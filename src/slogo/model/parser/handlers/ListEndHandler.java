package slogo.model.parser.handlers;

import slogo.model.ASTNodes.ASTNode;
import slogo.records.ParserRecord;

public class ListEndHandler extends ScopeEndHandler {

  public ListEndHandler(ParserRecord parserInfo) {
    super(parserInfo);
  }

  @Override
  public void handle(String currentToken) {
    ASTNode child = popCurrentScope();
    getCurrentScope().addNode(child);
  }
}
