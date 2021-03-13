package slogo.model.ASTNodes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.events.CommandsRecord;
import slogo.events.TurtleRecord;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.InfoBundle;
import slogo.model.Turtle;
import slogo.model.parser.CommandClassifier;
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
    parser = new ProgramParser("English", new HashMap<>());
    infoBundle = new TestBundle();
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
    assertEquals(x, infoBundle.getTurtle().getX(), 1E-5);
    assertEquals(y, infoBundle.getTurtle().getY(), 1E-5);
  }

  void assertTurtleRotation(double rotation) {
    assertEquals(rotation, infoBundle.getTurtle().getRotation(), 1E-5);
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
  void testHeadingSetHeading() {
    double a = 123.21;
    double res = parseAndEvaluateCommands("SETHEADING", a);
    assertEquals(a, res);
    assertTurtleRotation(a);

    assertEquals(a, parseAndEvaluateCommands("HEADING"), 1E-5);
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
  }

  @Test
  void testMakeVariable() {
    assertEquals(308.0, parseAndEvaluateCommands("MAKE :a", 308), 1E-5);
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

  class TestBundle implements InfoBundle {

    private Map<String, ASTNode> variableTable;
    private Map<String, ASTNode> commandTable;
    private TurtleRecord info;
    private Turtle turtle;
    private boolean environmentCleared = false;

    public TestBundle() {
      reset();
    }

    public void reset() {
      variableTable = new HashMap<>();
      commandTable = new HashMap<>();
      info = new TurtleRecord(0, 0, 0, 0, true, true);
      turtle = new Turtle(0, this);
    }

    @Override
    public Turtle getTurtle() {
      return turtle;
    }

    @Override
    public void notifyTurtleUpdate(TurtleRecord info) {
      this.info = info;
    }

    @Override
    public void notifyCommandUpdate(CommandsRecord info) {
    }

    @Override
    public void notifyVariableUpdate(VariablesRecord info) {
    }

    @Override
    public void notifyEnvironmentClear() {
      environmentCleared = true;
    }

    @Override
    public Map<String, ASTNode> getVariableTable() {
      return variableTable;
    }

    @Override
    public Map<String, ASTNode> getCommandTable() {
      return commandTable;
    }

    public TurtleRecord getInfo() {
      return info;
    }

    public boolean getEnvironmentCleared() {
      return environmentCleared;
    }
  }
}
