package slogo.model.parser.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.InfoBundle;
import slogo.model.TestBundle;
import slogo.model.parser.ParsingScope;
import slogo.model.parser.factories.HandlerFactory;
import slogo.records.ParserRecord;

public class HandlerTest {
  private HandlerFactory handlerFactory;
  private ParserRecord parserRecord;
  private Stack<ParsingScope> scopeStack;
  private List<String> tokensLeft;
  private static final String language = "English";
  private String currCommand;
  private InfoBundle environmentInfo;

  void setUp() {
    tokensLeft = new ArrayList<>();
    scopeStack = new Stack<>();
    ParsingScope currScope = new ParsingScope();
    scopeStack.push(currScope);
    environmentInfo = new TestBundle();

    parserRecord = new ParserRecord(
        scopeStack, tokensLeft, language, environmentInfo, currCommand);
    handlerFactory = new HandlerFactory(parserRecord);
  }

  @Test
  void testGroupEndHandler() {
    setUp();
    handlerFactory.buildHandler("GroupEnd");
  }
}
