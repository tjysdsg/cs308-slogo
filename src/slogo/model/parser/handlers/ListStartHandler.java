package slogo.model.parser.handlers;

import java.util.Stack;
import slogo.records.ParserRecord;
import slogo.model.parser.ParsingScope;

/**
 * The type Group start handler. It is a type of lookahead handler. It has the same notes as a
 * handler, but is dependent on more classes
 * <p>
 * This class depends on the ParsingScope, Stack, and ParserRecord classes
 *
 * @author Oliver Rodas
 * @see Handler
 */
public class ListStartHandler implements Handler  {
  private Stack<ParsingScope> scopeStack;

  /**
   * Instantiates a new List start handler.
   *
   * @param parserInfo the parser information
   */
  public ListStartHandler(ParserRecord parserInfo) {
    scopeStack = parserInfo.scopeStack();
  }

  @Override
  public void handle(String currentToken) {
    scopeStack.push(new ParsingScope());
  }
}
