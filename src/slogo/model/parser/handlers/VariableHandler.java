package slogo.model.parser.handlers;

import slogo.model.ASTNodes.ASTVariable;
import slogo.records.ParserRecord;

/**
 * The type variable handler. It is a type of literal handler. It has the same notes as a handler,
 * but is dependent on more classes
 * <p>
 * This class depends on the ASTVariable class, and ParserRecord class
 *
 * @author Oliver Rodas
 * @see Handler
 */
public class VariableHandler extends LiteralHandler {

  /**
   * Instantiates a new Variable handler.
   *
   * @param parserInfo the parser information
   */
  public VariableHandler(ParserRecord parserInfo) {
    super(parserInfo);
  }

  @Override
  public void handle(String currentToken) {
    assertScopeNeedsChild(currentToken);
    addNode(new ASTVariable(currentToken));
  }
}
