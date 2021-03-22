package slogo.model.parser.handlers;

import java.util.Stack;
import slogo.model.ASTNodes.ASTNode;
import slogo.records.ParserRecord;
import slogo.model.parser.ParsingScope;

public abstract class ScopeEndHandler implements Handler {
  private Stack<ParsingScope> scopeStack;

  public ScopeEndHandler(ParserRecord parserInfo) {
    scopeStack = parserInfo.scopeStack();
  }

  protected ASTNode popCurrentScope() {
    ParsingScope prevScope = scopeStack.pop();
    return prevScope.getCommands();
  }

  protected ParsingScope getCurrentScope() {
    return scopeStack.peek();
  }
}
