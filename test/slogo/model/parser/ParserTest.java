package slogo.model.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.ASTNodes.ASTCommand;
import slogo.model.ASTNodes.ASTCompoundStatement;
import slogo.model.ASTNodes.ASTForward;
import slogo.model.ASTNodes.ASTFunctionCall;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.ASTNodes.ASTNumberLiteral;
import slogo.model.ASTNodes.ASTRemainder;
import slogo.model.ASTNodes.ASTRepeat;
import slogo.model.ASTNodes.ASTSum;
import slogo.model.ASTNodes.ASTUnaryOperator;
import slogo.model.ASTNodes.ASTVariable;


/**
 * Tests only for parsing, not evaluation
 */
public class ParserTest {

  private Parser parser;
  private static final double FLOATING_POINT_EPSILON = 1E-5;
  private CommandClassifier commandClassifier;
  private Map<String, ASTFunctionCall> functionTable;

  @BeforeEach
  void setUp() {
    functionTable =  new HashMap<>();
    parser = new ProgramParser("English", functionTable);
    commandClassifier = new CommandClassifier("English");
  }

  void printTestCommand(String command) {
    System.out.println("Testing command: ");
    System.out.println("=================");
    System.out.println(command);
    System.out.println("=================");
  }

  void assertDoubleEquals(double expected, double actual) {
    assertEquals(expected, actual, FLOATING_POINT_EPSILON);
  }

  void checkCommandType(ASTNode node, String command) {
    command = commandClassifier.getSymbol(command);
    assertTrue(node instanceof ASTCommand);
    assertEquals(command.toLowerCase(), ((ASTCommand) node).getName().toLowerCase());
  }

  void unaryOperationTestHelper(String str, String cmdName, double value) {
    ASTNode node = parser.parseCommand(str);
    checkCommandType(node, cmdName);

    assertEquals(1, node.getNumChildren());

    var child1 = (ASTNumberLiteral) node.getChildAt(0);
    assertDoubleEquals(value, child1.getValue());
  }

  void binaryOperationTestHelper(String str, String cmdName, double value1, double value2) {
    ASTNode node = parser.parseCommand(str);
    checkCommandType(node, cmdName);

    assertEquals(2, node.getNumChildren());

    var child1 = (ASTNumberLiteral) node.getChildAt(0);
    var child2 = (ASTNumberLiteral) node.getChildAt(1);
    assertDoubleEquals(value1, child1.getValue());
    assertDoubleEquals(value2, child2.getValue());
  }

  @Test
  void testParseBinaryMathOperations() {
    Random rand = new Random();
    for (String command : new String[]{
        "SUM",
        "DIFFERENCE",
        "PRODUCT",
        "QUOTIENT",
        "REMAINDER",
    }) {
      // generate two numbers in [-100.0, 100.0]
      double a = rand.nextDouble() * 200.0 - 100.0;
      double b = rand.nextDouble() * 200.0 - 100.0;
      String str = String.format("\t%s %f \t\n %f\n", command, a, b);
      printTestCommand(str);
      binaryOperationTestHelper(str, command, a, b);
    }
  }

  @Test
  void testParseUnaryMathOperations() {
    Random rand = new Random();
    for (String command : new String[]{
        "MINUS",
        "RANDOM",
        "SIN",
        "COS",
        "TAN",
    }) {
      // generate a number in [-100.0, 100.0]
      double a = rand.nextDouble() * 200.0 - 100.0;
      String str = String.format("\t%s %f \t\n", command, a);
      printTestCommand(str);
      unaryOperationTestHelper(str, command, a);
    }
  }

  @Test
  void testBasicContruction() {
    ASTNode expected = new ASTForward();
    expected.addChild(new ASTNumberLiteral(50));

    ASTNode actual = parser.parseCommand("FD 50");

    assertNodeStructure(expected, actual);
  }

  @Test
  void testCompoundStatements() {
    int numCommands = 5;
    String toTest = "fd";
    Random rand = new Random();
    String TEST_STRING = "";
    List<ASTNode> commands = new ArrayList<>();

    for (int i = 0; i < numCommands; i++) {
      double val = rand.nextDouble();
      TEST_STRING = TEST_STRING.concat(String.format("%s %f ", toTest, val));
      ASTNode forward = new ASTForward();
      forward.addChild(new ASTNumberLiteral(val));
      commands.add(forward);
    }
    printTestCommand(TEST_STRING);

    ASTNode expected = new ASTCompoundStatement(commands);
    ASTNode actual = parser.parseCommand(TEST_STRING);

    assertNodeStructure(expected, actual);
  }

  @Test
  void testLoopStatementStructure() {
    String TEST_COMMAND = "repeat 4 \n[ \nfd :repcount \nfd + 3 5\n]";
    printTestCommand(TEST_COMMAND);

    List<ASTNode> commands = new ArrayList<>();

    ASTNode forward = new ASTForward();
    forward.addChild(new ASTVariable("repcount"));
    commands.add(forward);

    forward = new ASTForward();
    ASTNode sum = new ASTSum();
    sum.addChild(new ASTNumberLiteral(3));
    sum.addChild(new ASTNumberLiteral(5));
    forward.addChild(sum);
    commands.add(forward);

    ASTNode expected = new ASTRepeat();
    expected.addChild(new ASTNumberLiteral(4));
    expected.addChild(new ASTCompoundStatement(commands));

    ASTNode actual = parser.parseCommand(TEST_COMMAND);

    assertNodeStructure(expected, actual);
  }

  @Test
  void testUserDefinedCommand() {
    List<String> params = new ArrayList<>(
        Arrays.asList("item1", "item2"));

    List<ASTNode> commands = new ArrayList<>();
    ASTNode next = new ASTForward();
    next.addChild(new ASTVariable("itemA"));
    commands.add(next);
    next = new ASTSum();
    next.addChild(new ASTNumberLiteral(1));
    next.addChild(new ASTNumberLiteral(1));
    commands.add(next);

    ASTNode body = new ASTCompoundStatement(commands);
    ASTNode expected = new ASTFunctionCall("test", params, body);

    String TEST_STRING = """
        To test\s
        [ :itemA :itemB ]\s
        [fd :itemA sum 1 1]""";

    parser.parseCommand(TEST_STRING);

    ASTNode actual = functionTable.get("test");
    assertNodeStructure(expected, actual);

    TEST_STRING = "test 2 3";

    expected.addChild(new ASTNumberLiteral(2));
    expected.addChild(new ASTNumberLiteral(3));
    actual = parser.parseCommand(TEST_STRING);
    assertNodeStructure(expected, actual);
  }

  void assertNodeStructure(ASTNode expected, ASTNode actual) {
    assertEquals(expected.getToken(), actual.getToken());
    assertEquals(expected.getNumChildren(), actual.getNumChildren());

    for (int i  = 0; i < expected.getNumChildren(); i++) {
      assertNodeStructure(expected.getChildAt(i), actual.getChildAt(i));
    }
  }
}
