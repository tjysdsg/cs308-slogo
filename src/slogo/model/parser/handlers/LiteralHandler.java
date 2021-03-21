package slogo.model.parser.handlers;

import java.util.Stack;
import slogo.exceptions.FloatingValueException;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.parser.ParserRecord;
import slogo.model.parser.Scope;

public abstract class LiteralHandler implements Handler {
  private Stack<Scope> scopeStack;
  private Scope currScope;

  public LiteralHandler(ParserRecord parserInfo) {
    scopeStack = parserInfo.scopeStack();
    currScope = scopeStack.peek();
  }

  protected void assertScopeNeedsChild() {
    if (scopeStack.size() == 1 && !currScope.addNextAsChild())
      throw new FloatingValueException();
  }

  protected void addNode(ASTNode toAdd) {
    currScope.addNode(toAdd);
  }
}
