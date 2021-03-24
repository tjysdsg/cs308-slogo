package slogo.model.parser.handlers;

import java.util.Stack;
import slogo.exceptions.FloatingValueException;
import slogo.model.ASTNodes.ASTNode;
import slogo.records.ParserRecord;
import slogo.model.parser.ParsingScope;

public abstract class LiteralHandler implements Handler {
  private Stack<ParsingScope> scopeStack;
  private ParsingScope currScope;

  public LiteralHandler(ParserRecord parserInfo) {
    scopeStack = parserInfo.scopeStack();
    currScope = scopeStack.peek();
  }

  protected void assertScopeNeedsChild(String currentToken) {
    if (scopeStack.size() == 1 && !currScope.isNextAChild())
      throw new FloatingValueException(currentToken);
  }

  protected void addNode(ASTNode toAdd) {
    currScope.addNode(toAdd);
  }
}
