package slogo.model.parser.handlers;

import java.util.Stack;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.parser.Scope;

public abstract class ScopeEndHandler implements Handler {
  private Stack<Scope> scopeStack;

  public ScopeEndHandler(ParserRecord parserInfo) {
    scopeStack = parserInfo.scopeStack();
  }

  protected ASTNode popCurrentScope() {
    Scope prevScope = scopeStack.pop();
    return prevScope.getCommands();
  }

  protected Scope getCurrentScope() {
    return scopeStack.peek();
  }
}
