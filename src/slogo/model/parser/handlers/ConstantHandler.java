package slogo.model.parser.handlers;

import slogo.model.ASTNodes.ASTNumberLiteral;
import slogo.records.ParserRecord;

public class ConstantHandler extends LiteralHandler {

  public ConstantHandler(ParserRecord parserInfo) {
    super(parserInfo);
  }

  @Override
  public void handle(String currentToken) {
    assertScopeNeedsChild(currentToken);
    addNode(new ASTNumberLiteral(Double.parseDouble(currentToken)));
  }
}
