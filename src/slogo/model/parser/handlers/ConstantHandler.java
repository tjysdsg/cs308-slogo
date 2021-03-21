package slogo.model.parser.handlers;

import slogo.model.ASTNodes.ASTNumberLiteral;

public class ConstantHandler extends LiteralHandler {

  public ConstantHandler(ParserRecord parserInfo) {
    super(parserInfo);
  }

  @Override
  public void handle(String token) {
    assertScopeNeedsChild();
    addNode(new ASTNumberLiteral(Double.parseDouble(token)));
  }
}
