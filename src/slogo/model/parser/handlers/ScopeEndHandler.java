package slogo.model.parser.handlers;

import java.util.Stack;
import slogo.exceptions.IncorrectParameterCountException;
import slogo.model.ASTNodes.ASTNode;
import slogo.records.ParserRecord;
import slogo.model.parser.ParsingScope;

/**
 * The type scope end handler. It is a type of handler that ends a scope. It has the same notes as a
 * handler, but is dependent on more classes
 * <p>
 * This class depends on the Stack class, ASTNode class, ParsingScope class, and ParserRecord class
 *
 * @author Oliver Rodas
 * @see Handler
 */
public abstract class ScopeEndHandler implements Handler {
  private Stack<ParsingScope> scopeStack;

  /**
   * Instantiates a new Scope end handler.
   *
   * @param parserInfo the parser information
   */
  public ScopeEndHandler(ParserRecord parserInfo) {
    scopeStack = parserInfo.scopeStack();
  }

  /**
   * Pops the current scope as a single ast node.
   *
   * @return the ast node containing all the commands of a scope
   * @throws IncorrectParameterCountException if the scope is not finished and a command
   *    *   does not have all of its children
   */
  protected ASTNode popCurrentScope() throws IncorrectParameterCountException {
    ParsingScope prevScope = scopeStack.pop();
    return prevScope.getCommands();
  }

  /**
   * Gets current scope.
   *
   * @return the current scope
   */
  protected ParsingScope getCurrentScope() {
    return scopeStack.peek();
  }
}
