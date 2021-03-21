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
import slogo.model.parser.Scope;

public class CommandHandler extends LookAheadHandler {

  private Stack<Scope> scopeStack;
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
  public void handle(String token) {
    String commandName;

    try {
      commandName = commandClassifier.getSymbol(token);
    } catch (UnknownIdentifierException e) {
      commandName = token;
    }

    ASTNode newCommand;

    if(commandName.equals(MAKE_USER_INSTRUCTION)) {
      String identifier = assertNextIs(EXPECTED_NEXT);
      newCommand = new ASTMakeUserInstruction(identifier, environmentInfo);

    } else {
      newCommand = commandFactory.getCommand(commandName);
    }

    Scope currScope = scopeStack.peek();
    currScope.addNode(newCommand);
  }
}
