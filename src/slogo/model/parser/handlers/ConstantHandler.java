package slogo.model.parser.handlers;

import slogo.model.ASTNodes.ASTNumberLiteral;
import slogo.model.parser.ParserRecord;

public class ConstantHandler extends LiteralHandler {

  public ConstantHandler(ParserRecord parserInfo) {
    super(parserInfo);
  }

  @Override
  public void handle(String token) {
    assertScopeNeedsChild(token);
    addNode(new ASTNumberLiteral(Double.parseDouble(token)));
  }
}
