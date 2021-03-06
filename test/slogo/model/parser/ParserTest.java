package slogo.model.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.ASTNodes.ASTCommand;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.ASTNodes.ASTNumberLiteral;


/**
 * Tests only for parsing, not evaluation
 */
public class ParserTest {

  private Parser parser;
  private static final double FLOATING_POINT_EPSILON = 1E-5;

  @BeforeEach
  void setUp() {
    parser = new ProgramParser("English");
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

  }
}
