package slogo.model.parser.handlers;

import slogo.model.ASTNodes.ASTNumberLiteral;
import slogo.records.ParserRecord;

/**
 * The type constant handler. It is a type of literal handler.
 * It has the same notes as a handler, but is dependent on more classes
 *
 * This class depends on the ASTNumberLiteral class, and ParserRecord class
 *
 * @author Oliver Rodas
 * @see Handler
 */
public class ConstantHandler extends LiteralHandler {

  /**
   * Instantiates a new Constant handler.
   *
   * @param parserInfo the information from the parser
   */
  public ConstantHandler(ParserRecord parserInfo) {
    super(parserInfo);
  }

  @Override
  public void handle(String currentToken) {
    assertScopeNeedsChild(currentToken);
    addNode(new ASTNumberLiteral(Double.parseDouble(currentToken)));
  }
}
