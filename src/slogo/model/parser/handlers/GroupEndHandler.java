package slogo.model.parser.handlers;

import slogo.model.ASTNodes.ASTNode;

public class GroupEndHandler extends ScopeEndHandler {

  public GroupEndHandler(ParserRecord parserInfo) {
    super(parserInfo);
  }

  @Override
  public void handle(String token) {
    ASTNode orphanage = popCurrentScope();
    getCurrentScope().addAllNodes(orphanage.getChildren());
  }
}
