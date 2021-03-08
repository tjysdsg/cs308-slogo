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

  class TestBundle implements InfoBundle {

    private Map<String, ASTNode> lookupTable;
    private TurtleRecord info;
    private Turtle turtle;

    public TestBundle() {
      reset();
    }

    public void reset() {
      lookupTable = new HashMap<>();
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
    public Map<String, ASTNode> getLookupTable() {
      return lookupTable;
    }

    public TurtleRecord getInfo() {
      return info;
    }
  }
}
