package slogo.model.parser.handlers;


import slogo.model.parser.factories.HandlerFactory;
import slogo.records.ParserRecord;

/**
 * The type Group start handler. It is a type of lookahead handler.
 * It has the same notes as a handler, but is dependent on more classes
 *
 * This class depends on the HandlerFactory class, and ParserRecord class
 *
 * @author Oliver Rodas
 * @see Handler
 */
public class GroupStartHandler extends LookAheadHandler {

  private HandlerFactory handlerFactory;
  private Handler commandHandler;
  private Handler listStartHandler;

  private static final String EXPECTED_NEXT = "Command";
  private static final String ACTION = "ListStart";

  /**
   * Instantiates a new Group start handler.
   *
   * @param parserInfo the info from the parser
   */
  public GroupStartHandler(ParserRecord parserInfo) {
    super(parserInfo.tokensLeft());
    handlerFactory = new HandlerFactory(parserInfo);
    commandHandler = handlerFactory.buildHandler(EXPECTED_NEXT);
    listStartHandler = handlerFactory.buildHandler(ACTION);
  }

  @Override
  public void handle(String currentToken) {
    String nextToken = assertNextIs(currentToken, EXPECTED_NEXT);
    commandHandler.handle(nextToken);
    listStartHandler.handle(currentToken);
  }
}
