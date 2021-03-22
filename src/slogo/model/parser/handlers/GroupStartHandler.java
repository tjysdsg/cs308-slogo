package slogo.model.parser.handlers;


import slogo.model.parser.factories.HandlerFactory;
import slogo.records.ParserRecord;

public class GroupStartHandler extends LookAheadHandler {

  private HandlerFactory handlerFactory;
  private Handler commandHandler;
  private Handler listStartHandler;

  private static final String EXPECTED_NEXT = "Command";
  private static final String ACTION = "ListStart";

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
