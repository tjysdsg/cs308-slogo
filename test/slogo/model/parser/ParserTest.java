package slogo.model.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.ASTNodes.ASTNumberLiteral;


/**
 * Tests only for parsing, not evaluation
 */
public class ParserTest {

  private Parser parser;

  @BeforeEach
  void setUp() {
    parser = new ProgramParser("English");
  }

  void printTestHeading(String command) {
    System.out.println("Testing command: ");
    System.out.println("=================");
    System.out.println(command);
    System.out.println("=================");
  }

  void binaryOperationTestHelper(String command, double value1, double value2) {
    ASTNode tree = parser.parseCommand(command);
    assertEquals(2, tree.getNumChildren());

    var child1 = (ASTNumberLiteral) tree.getChildAt(0);
    var child2 = (ASTNumberLiteral) tree.getChildAt(1);
    assertEquals(value1, child1.getValue(), 1E-5);
    assertEquals(value2, child2.getValue(), 1E-5);
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
      String cmd = String.format("\t%s %f \t\n %f\n", command, a, b);
      printTestHeading(cmd);
      binaryOperationTestHelper(cmd, a, b);
    }
  }

}
