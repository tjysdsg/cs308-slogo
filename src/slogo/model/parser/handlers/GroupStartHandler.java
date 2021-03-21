package slogo.model.parser.handlers;


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
  public void handle(String token) {
    String identifier = assertNextIs(EXPECTED_NEXT);
    commandHandler.handle(identifier);
    listStartHandler.handle(token);
  }
}
