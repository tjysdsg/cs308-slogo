package slogo.model.parser.handlers;

import slogo.model.ASTNodes.ASTNode;
import slogo.model.parser.ParserRecord;

public class ListEndHandler extends ScopeEndHandler {

  public ListEndHandler(ParserRecord parserInfo) {
    super(parserInfo);
  }

  @Override
  public void handle(String token) {
    ASTNode child = popCurrentScope();
    getCurrentScope().addNode(child);
  }
}
