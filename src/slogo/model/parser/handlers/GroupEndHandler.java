package slogo.model.parser.handlers;

import slogo.model.ASTNodes.ASTNode;
import slogo.records.ParserRecord;

/**
 * The type Group end handler. It is a type of scope end handler.
 * It has the same notes as a handler, but is dependent on more classes
 *
 * This class depends on the ASTNode class, and ParserRecord class
 *
 * @author Oliver Rodas
 * @see Handler
 */
public class GroupEndHandler extends ScopeEndHandler {

  /**
   * Instantiates a new Group end handler.
   *
   * @param parserInfo the information from the parser
   */
  public GroupEndHandler(ParserRecord parserInfo) {
    super(parserInfo);
  }

  @Override
  public void handle(String currentToken) {
    ASTNode orphanage = popCurrentScope();
    getCurrentScope().addAllNodes(orphanage.getChildren());
  }
}
