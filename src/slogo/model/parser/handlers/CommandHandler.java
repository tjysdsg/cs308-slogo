package slogo.model.parser.handlers;

import java.util.Stack;
import slogo.exceptions.UnknownIdentifierException;
import slogo.model.ASTNodes.ASTMakeUserInstruction;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.InfoBundle;
import slogo.model.parser.factories.ASTCommandFactory;
import slogo.model.parser.factories.ClassifierFactory;
import slogo.model.parser.classifiers.CommandClassifier;
import slogo.model.parser.ParserRecord;
import slogo.model.parser.ParsingScope;

public class CommandHandler extends LookAheadHandler {

  private Stack<ParsingScope> scopeStack;
  private CommandClassifier commandClassifier;
  private ASTCommandFactory commandFactory;
  private InfoBundle environmentInfo;

  private static final String EXPECTED_NEXT = "Command";
  private static final String MAKE_USER_INSTRUCTION = "MakeUserInstruction";

  public CommandHandler(ParserRecord parserInfo) {
    super(parserInfo.tokensLeft());
    scopeStack = parserInfo.scopeStack();
    environmentInfo = parserInfo.environmentInfo();
    commandClassifier = ClassifierFactory.buildCommandClassifier(parserInfo.language());
    commandFactory = new ASTCommandFactory(environmentInfo);
  }

  @Override
  public void handle(String currentToken) {
    String commandName;

    try {
      commandName = commandClassifier.getSymbol(currentToken);
    } catch (UnknownIdentifierException e) {
      commandName = currentToken;
    }

    ASTNode newCommand;

    if(commandName.equals(MAKE_USER_INSTRUCTION)) {
      String nextToken = assertNextIs(currentToken, EXPECTED_NEXT);
      newCommand = new ASTMakeUserInstruction(nextToken, environmentInfo);

    } else {
      newCommand = commandFactory.getCommand(commandName);
    }

    ParsingScope currScope = scopeStack.peek();
    currScope.addNode(newCommand);
  }
}
