package slogo.model.parser.handlers;

import java.util.Stack;
import slogo.exceptions.FloatingValueException;
import slogo.model.ASTNodes.ASTNode;
import slogo.records.ParserRecord;
import slogo.model.parser.ParsingScope;

/**
 * The Literal handler. This class serves as a super class of the types of literal handlers. It has
 * the same notes as a handler, but is dependent on more classes
 * <p>
 * This class depends on the exceptions, Java's Util, ASTNodes, records, and parser packages
 *
 * @author Oliver Rodas
 * @see Handler
 */
public abstract class LiteralHandler implements Handler {
  private Stack<ParsingScope> scopeStack;
  private ParsingScope currScope;

  /**
   * Instantiates a new Literal handler.
   *
   * @param parserInfo the parser information
   */
  public LiteralHandler(ParserRecord parserInfo) {
    scopeStack = parserInfo.scopeStack();
    currScope = scopeStack.peek();
  }

  /**
   * Assert the current scope needs a child.
   *
   * @param currentToken the current token
   */
  protected void assertScopeNeedsChild(String currentToken) {
    if (scopeStack.size() == 1 && !currScope.isNextAChild())
      throw new FloatingValueException(currentToken);
  }

  /**
   * Add a node to the current scope.
   *
   * @param toAdd the node to add
   */
  protected void addNode(ASTNode toAdd) {
    currScope.addNode(toAdd);
  }
}
