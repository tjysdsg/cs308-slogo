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

  @Test
  void testMoveCommands() {
    Random rand = new Random();
    for (String cmd : new String[]{
        "FORWARD",
        "BACK",
        "RIGHT",
        "LEFT",
    }) {
      infoBundle = new TestBundle(); // reset turtle

      double a = rand.nextDouble() * 200.0 - 100.0;
      double res = parseAndEvaluateCommands(cmd, a);
      assertEquals(a, res, 1E-5);

      switch (cmd) {
        case "FORWARD" -> assertEquals(a, infoBundle.getTurtle().getY(), 1E-5);
        case "BACK" -> assertEquals(-a, infoBundle.getTurtle().getY(), 1E-5);
        case "RIGHT" -> assertEquals(a, infoBundle.getTurtle().getRotation(), 1E-5);
        case "LEFT" -> assertEquals(-a, infoBundle.getTurtle().getRotation(), 1E-5);
      }
    }
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

    assertEquals(20, infoBundle.getTurtle().getX(), 1E-5);
    assertEquals(10, infoBundle.getTurtle().getY(), 1E-5);
  }

  class TestBundle implements InfoBundle {

    private Map<String, ASTNode> lookupTable;
    private TurtleRecord info;
    private Turtle turtle;

    public TestBundle() {
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
