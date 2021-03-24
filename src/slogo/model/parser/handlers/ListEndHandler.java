package slogo.model.parser.handlers;

import slogo.model.ASTNodes.ASTNode;
import slogo.records.ParserRecord;

/**
 * The type List end handler. It is a type of scope end handler. It has the same notes as a handler,
 * but is dependent on more classes
 * <p>
 * This class depends on the ASTNode class, and ParserRecord class
 *
 * @author Oliver Rodas
 * @see Handler
 */
public class ListEndHandler extends ScopeEndHandler {

  /**
   * Instantiates a new List end handler.
   *
   * @param parserInfo the parser information
   */
  public ListEndHandler(ParserRecord parserInfo) {
    super(parserInfo);
  }

  @Override
  public void handle(String currentToken) {
    ASTNode child = popCurrentScope();
    getCurrentScope().addNode(child);
  }
}
