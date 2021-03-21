package slogo.model.parser.handlers;

import slogo.model.ASTNodes.ASTVariable;

public class VariableHandler extends LiteralHandler {

  public VariableHandler(ParserRecord parserInfo) {
    super(parserInfo);
  }

  @Override
  public void handle(String token) {
    assertScopeNeedsChild();
    addNode(new ASTVariable(token));
  }
}
