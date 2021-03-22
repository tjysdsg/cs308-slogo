package slogo.model.parser.handlers;

import slogo.model.ASTNodes.ASTNode;
import slogo.records.ParserRecord;

public class GroupEndHandler extends ScopeEndHandler {

  public GroupEndHandler(ParserRecord parserInfo) {
    super(parserInfo);
  }

  @Override
  public void handle(String currentToken) {
    ASTNode orphanage = popCurrentScope();
    getCurrentScope().addAllNodes(orphanage.getChildren());
  }
}
