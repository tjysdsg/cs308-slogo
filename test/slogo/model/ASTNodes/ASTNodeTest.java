package slogo.model.ASTNodes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.TestBundle;
import slogo.model.parser.Parser;
import slogo.model.parser.ProgramParser;

/**
 * Test evaluation of ASTNodes
 * <p>
 * For convenience, this test uses parser to build command node tree, so parser tests must pass
 * first
 */
public class ASTNodeTest {

  private Parser parser;
  private TestBundle infoBundle;

  @BeforeEach
  void setUp() {
    infoBundle = new TestBundle();
    parser = new ProgramParser("English", infoBundle);
  }

  double parseAndEvaluateCommands(String cmd) {
    ASTNode node = parser.parseCommand(cmd);
    return node.evaluate(infoBundle);
  }

  double parseAndEvaluateCommands(String cmd, double param) {
    String str = String.format("%s %f", cmd, param);
    ASTNode node = parser.parseCommand(str);
    return node.evaluate(infoBundle);
  }

  double parseAndEvaluateCommands(String cmd, double param1, double param2) {
    String str = String.format("%s %f %f", cmd, param1, param2);
    ASTNode node = parser.parseCommand(str);
    return node.evaluate(infoBundle);
  }

  void assertTurtleXY(double x, double y) {
    assertEquals(x, infoBundle.getActiveTurtles().get(0).getX(), 1E-5);
    assertEquals(y, infoBundle.getActiveTurtles().get(0).getY(), 1E-5);
  }

  void assertTurtleRotation(double rotation) {
    assertEquals(rotation, infoBundle.getActiveTurtles().get(0).getRotation(), 1E-5);
  }

  void assertVariableLookUp(String name, double val) {
    ASTNumberLiteral literal = infoBundle.getVariable(name);
    assertNotNull(literal);
    assertEquals(val, literal.getValue(), 1E-5);
  }

  @Test
  void testForwardBackwardCommands() {
    Random rand = new Random();

    { // FORWARD
      infoBundle.reset(); // reset turtle
      double a = rand.nextDouble() * 200.0 - 100.0;
      double res = parseAndEvaluateCommands("FORWARD", a);
      assertEquals(a, res, 1E-5);
      assertTurtleXY(0, a);
    }

    { // BACKWARD
      infoBundle.reset(); // reset turtle
      double a = rand.nextDouble() * 200.0 - 100.0;
      double res = parseAndEvaluateCommands("BACK", a);
      assertEquals(a, res, 1E-5);
      assertTurtleXY(0, -a);
    }
  }

  @Test
  void testLeftRightCommands() {
    { // LEFT 20
      infoBundle.reset(); // reset turtle
      double res = parseAndEvaluateCommands("LEFT", 20);
      assertEquals(-20, res, 1E-5);
      assertTurtleRotation(-20);
    }

    { // LEFT -20
      infoBundle.reset(); // reset turtle
      double res = parseAndEvaluateCommands("LEFT", -20);
      assertEquals(20, res, 1E-5);
      assertTurtleRotation(20);
    }

    { // RIGHT 20
      infoBundle.reset(); // reset turtle
      double res = parseAndEvaluateCommands("RIGHT", 20);
      assertEquals(20, res, 1E-5);
      assertTurtleRotation(20);
    }

    { // RIGHT -20
      infoBundle.reset(); // reset turtle
      double res = parseAndEvaluateCommands("RIGHT", -20);
      assertEquals(-20, res, 1E-5);
      assertTurtleRotation(-20);
    }

    { // RIGHT 200
      infoBundle.reset(); // reset turtle
      double res = parseAndEvaluateCommands("RIGHT", 200);
      assertEquals(200, res, 1E-5);
      assertTurtleRotation(-160);
    }
  }

  @Test
  void testSetHeading() {
    double a = 123.21;
    double res = parseAndEvaluateCommands("SETHEADING", a);
    assertEquals(a, res);
    assertTurtleRotation(a);
  }

  @Test
  void testTowards() {
    { // 45 degrees
      double x = 10;
      double y = 10;
      double res = parseAndEvaluateCommands("TOWARDS", x, y);
      assertEquals(45, res, 1E-5);
      assertTurtleRotation(45);
    }

    { // upwards
      infoBundle.reset(); // reset
      double x = 0;
      double y = 10;
      double res = parseAndEvaluateCommands("TOWARDS", x, y);
      assertEquals(0, res, 1E-5);
      assertTurtleRotation(0);
    }

    { // -30 degrees
      infoBundle.reset(); // reset
      double x = -10;
      double y = 10 * Math.sqrt(3);
      double res = parseAndEvaluateCommands("TOWARDS", x, y);
      assertEquals(-30, res, 1E-5);
      assertTurtleRotation(-30);
    }

    { // from -30 degrees to -150
      // NO RESET
      double x = -10;
      double y = -10 * Math.sqrt(3);
      double res = parseAndEvaluateCommands("TOWARDS", x, y);
      assertEquals(-120, res, 1E-5);
      assertTurtleRotation(-150);
    }

    { // from -150 degrees to 30
      double x = 10;
      double y = 10 * Math.sqrt(3);
      double res = parseAndEvaluateCommands("TOWARDS", x, y);
      assertEquals(180, res, 1E-5);
      assertTurtleRotation(30);
    }
  }

  @Test
  void testSetXY() {
    double res = parseAndEvaluateCommands("SETXY", 30, 40);
    assertEquals(50, res);
  }

  @Test
  void testClearScreen() {
    parseAndEvaluateCommands("SETXY", 3, 4);
    assertEquals(5, parseAndEvaluateCommands("CS"), 1E-5);
    assertTrue(infoBundle.getEnvironmentCleared());
  }

  @Test
  void testQueryOperators() {
    double x = 69;
    double y = 96;
    double angle = -30;
    parseAndEvaluateCommands("SETXY", x, y);
    parseAndEvaluateCommands("SETHEADING", angle);

    assertEquals(x, parseAndEvaluateCommands("XCOR"), 1E-5);
    assertEquals(y, parseAndEvaluateCommands("YCOR"), 1E-5);
    assertEquals(angle, parseAndEvaluateCommands("HEADING"), 1E-5);

    parseAndEvaluateCommands("PENDOWN");
    assertEquals(1.0, parseAndEvaluateCommands("PENDOWN?"), 1E-5);
    parseAndEvaluateCommands("PENUP");
    assertEquals(0, parseAndEvaluateCommands("PENDOWN?"), 1E-5);

    parseAndEvaluateCommands("SHOWTURTLE");
    assertEquals(1.0, parseAndEvaluateCommands("SHOWING?"), 1E-5);
    parseAndEvaluateCommands("HIDETURTLE");
    assertEquals(0, parseAndEvaluateCommands("SHOWING?"), 1E-5);
  }

  @Test
  void testFunctions() {
    String definition = "TO translate [:dForward :dRight] [FORWARD :dForward RIGHT 90 FORWARD :dRight]";
    String call = "translate 10 20";

    ASTNode node = parser.parseCommand(definition);
    double res = node.evaluate(infoBundle);
    assertEquals(1, res, 1E-5);

    node = parser.parseCommand(call);
    res = node.evaluate(infoBundle);
    assertEquals(20.0, res, 1E-5);

    assertTurtleXY(20, 10);
  }

  @Test
  void testVariableDefaultValue() {
    String definition = "FD :aaa";

    ASTNode node = parser.parseCommand(definition);
    double res = node.evaluate(infoBundle);
    assertEquals(0, res, 1E-5);

    assertTurtleXY(0, 0);
    assertVariableLookUp(":aaa", 0);
  }

  @Test
  void testMakeVariable() {
    assertEquals(308.0, parseAndEvaluateCommands("MAKE :a", 308), 1E-5);
    ASTNumberLiteral literal = (ASTNumberLiteral) infoBundle.getVariable(":a");
    assertNotNull(literal);
    assertEquals(308, literal.getValue(), 1E-5);
    assertVariableLookUp(":a", 308);
  }

  @Test
  void testMathOperators() {
    assertEquals(69 + 96, parseAndEvaluateCommands("SUM", 69, 96), 1E-5);
    assertEquals(69 - 96, parseAndEvaluateCommands("DIFFERENCE", 69, 96), 1E-5);
    assertEquals(69 * 96, parseAndEvaluateCommands("PRODUCT", 69, 96), 1E-5);
    assertEquals(69.0 / 96.0, parseAndEvaluateCommands("QUOTIENT", 69, 96), 1E-5);
    assertEquals(69 % 96, parseAndEvaluateCommands("REMAINDER", 69, 96), 1E-5);
    assertEquals(-69, parseAndEvaluateCommands("MINUS", 69), 1E-5);
    assertEquals(0.5, parseAndEvaluateCommands("SIN", 30), 1E-5);
    assertEquals(0.5, parseAndEvaluateCommands("COS", 60), 1E-5);
    assertEquals(1, parseAndEvaluateCommands("TAN", 45), 1E-5);
    assertEquals(45, parseAndEvaluateCommands("ATAN", 1), 1E-5);

    assertEquals(0, parseAndEvaluateCommands("LOG", 1), 1E-5);
    assertEquals(1, parseAndEvaluateCommands("LOG", Math.E), 1E-5);
    assertEquals(1, parseAndEvaluateCommands("POW", 999, 0), 1E-5);
    assertEquals(1024, parseAndEvaluateCommands("POW", 2, 10), 1E-5);

    assertEquals(Math.PI, parseAndEvaluateCommands("PI"), 1E-5);
  }

  @Test
  void testBoolOperators() {
    assertEquals(0, parseAndEvaluateCommands("LESS?", 2, 1), 1E-5);
    assertEquals(0, parseAndEvaluateCommands("LESS?", 1, 1), 1E-5);
    assertEquals(1, parseAndEvaluateCommands("LESS?", 1, 2), 1E-5);

    assertEquals(1, parseAndEvaluateCommands("GREATER?", 2, 1), 1E-5);
    assertEquals(0, parseAndEvaluateCommands("GREATER?", 1, 1), 1E-5);
    assertEquals(0, parseAndEvaluateCommands("GREATER?", 1, 2), 1E-5);

    assertEquals(0, parseAndEvaluateCommands("EQUAL?", 2, 1), 1E-5);
    assertEquals(1, parseAndEvaluateCommands("EQUAL?", 1, 1), 1E-5);
    assertEquals(0, parseAndEvaluateCommands("EQUAL?", 1, 2), 1E-5);

    assertEquals(1, parseAndEvaluateCommands("NOTEQUAL?", 2, 1), 1E-5);
    assertEquals(0, parseAndEvaluateCommands("NOTEQUAL?", 1, 1), 1E-5);
    assertEquals(1, parseAndEvaluateCommands("NOTEQUAL?", 1, 2), 1E-5);

    assertEquals(0, parseAndEvaluateCommands("AND", 0, 0), 1E-5);
    assertEquals(0, parseAndEvaluateCommands("AND", 0, 1), 1E-5);
    assertEquals(0, parseAndEvaluateCommands("AND", 1, 0), 1E-5);
    assertEquals(1, parseAndEvaluateCommands("AND", 1, 1), 1E-5);

    assertEquals(0, parseAndEvaluateCommands("OR", 0, 0), 1E-5);
    assertEquals(1, parseAndEvaluateCommands("OR", 0, 1), 1E-5);
    assertEquals(1, parseAndEvaluateCommands("OR", 1, 0), 1E-5);
    assertEquals(1, parseAndEvaluateCommands("OR", 1, 1), 1E-5);

    assertEquals(1, parseAndEvaluateCommands("NOT", 0), 1E-5);
    assertEquals(0, parseAndEvaluateCommands("NOT", 1), 1E-5);
  }

  @Test
  void testForLoop() {
    double res = parseAndEvaluateCommands("""
        FOR [:a 1 10 2] [fd :a]
        """);

    assertEquals(9, res, 1E-5);
    assertTurtleXY(0, 25);

    assertVariableLookUp(":a", 9);
  }

  @Test
  void testDotTimes() {
    double res = parseAndEvaluateCommands("""
        DOTIMES [:a 10] [fd :a]
        """);

    assertEquals(10, res, 1E-5);
    assertTurtleXY(0, 55);

    assertVariableLookUp(":a", 10);
  }

  @Test
  void testRepeat() {
    double res = parseAndEvaluateCommands("""
        REPEAT 10 [fd :repcount]
        """);

    assertEquals(10, res, 1E-5);
    assertTurtleXY(0, 55);

    assertVariableLookUp(":repcount", 10);
  }

  @Test
  void testIf() {
    double res = parseAndEvaluateCommands("""
        IF SUM 1 1 [fd 1]
        """);
    assertEquals(1, res, 1E-5);
    assertTurtleXY(0, 1);

    infoBundle.reset();
    res = parseAndEvaluateCommands("""
        IF 0 [fd 1]
        """);
    assertEquals(0, res, 1E-5);
    assertTurtleXY(0, 0);
  }

  @Test
  void testIfElse() {
    double res = parseAndEvaluateCommands("""
        IFELSE SUM 1 1 [fd 1] [back 1]
        """);
    assertEquals(1, res, 1E-5);
    assertTurtleXY(0, 1);

    infoBundle.reset();
    res = parseAndEvaluateCommands("""
        IFELSE - 1 1 [fd 1] [back 1]
        """);
    assertEquals(1, res, 1E-5);
    assertTurtleXY(0, -1);
  }
}
