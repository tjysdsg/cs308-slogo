package slogo.model.parser.handlers;

import java.util.Stack;
import slogo.model.parser.ParserRecord;
import slogo.model.parser.Scope;

public class ListStartHandler implements Handler  {
  private Stack<Scope> scopeStack;

  public ListStartHandler(ParserRecord parserInfo) {
    scopeStack = parserInfo.scopeStack();
  }

  @Override
  public void handle(String currentToken) {
    scopeStack.push(new Scope());
  }
}
