package slogo.model.parser.handlers;

import slogo.model.ASTNodes.ASTVariable;
import slogo.model.parser.ParserRecord;

public class VariableHandler extends LiteralHandler {

  public VariableHandler(ParserRecord parserInfo) {
    super(parserInfo);
  }

  @Override
  public void handle(String token) {
    assertScopeNeedsChild(token);
    addNode(new ASTVariable(token));
  }
}
