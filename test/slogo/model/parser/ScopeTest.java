package slogo.model.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.ASTNodes.ASTCompoundStatement;
import slogo.model.ASTNodes.ASTForward;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.ASTNodes.ASTNumberLiteral;

public class ScopeTest {
  private Scope myScope;

  @BeforeEach
  void setUp() {
    myScope = new Scope();
  }

  @Test
  void testEmptyScope() {
    assertNextNotChild();
    ParserTest.assertNodeStructure(
        new ASTCompoundStatement(new ArrayList<>()), myScope.getCommands());
  }

  @Test
  void testCollapse() {
    myScope.addNode(new ASTForward());
    assertNextIsChild();

    myScope.addNode(new ASTNumberLiteral(50));
    assertNextNotChild();

    ASTNode child = new ASTForward();
    child.addChild(new ASTNumberLiteral(50));
    ASTNode expected = new ASTCompoundStatement(List.of(child));
    ParserTest.assertNodeStructure(expected, myScope.getCommands());
  }

  @Test
  void testCompoundStatement() {
    testCollapse();
    ASTNode child = new ASTForward();
    child.addChild(new ASTNumberLiteral(50));
    ASTNode expected = new ASTCompoundStatement(List.of(child, new ASTNumberLiteral(50)));
    myScope.addNode(new ASTNumberLiteral(50));
    ParserTest.assertNodeStructure(expected, myScope.getCommands());
  }

  void assertNextIsChild() {
    //assertTrue(myScope.isIncomplete());
    assertTrue(myScope.addNextAsChild());
  }

  void assertNextNotChild() {
    //assertFalse(myScope.isIncomplete());
    assertFalse(myScope.addNextAsChild());
  }
}
