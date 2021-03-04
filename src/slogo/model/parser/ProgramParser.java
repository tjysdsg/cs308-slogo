package slogo.model.parser;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import slogo.exceptions.*;
import slogo.model.ASTNodes.*;

public class ProgramParser implements Parser {
  private final ASTCommandFactory factory;
  private final TokenClassifier tc = new TokenClassifier();
  private final CommandClassifier cc;
  private static final String SPLITTER = "[ ]|(?<=\\[)|(?=\\[)|(?<=\\])|(?=\\])";

  public ProgramParser(String language) {
    cc = new CommandClassifier(language);
    factory = new ASTCommandFactory(language);
  }

  //TODO: Add Expression Handling and Logic for Errors

  public ASTNode parseCommand(String command)
      throws UnknownCommandException, InvalidSyntaxException, IncorrectParameterCountException {
    List<String> lines = Arrays.asList(command.split(SPLITTER));
    String type;
    Stack<ASTCommand> nodeStack = new Stack<>();
    int expLevel = 0;

    for (String line : lines) {
      if (line.trim().length() > 0) {
        type = tc.getSymbol(line);

        //TODO: Must refactor to become better designed and avoid duplication
        switch(type) {
          case "Constant" -> {
            nodeStack.peek().addChild(new ASTNumberLiteral(Double.parseDouble(line)));
          }

          case "Command" -> {
            nodeStack.push(factory.getCommand(line));
          }

          case "Variable" -> {
            nodeStack.peek().addChild(new ASTVariable(line));
          }

          case "ListStart" -> { expLevel++; }
          case"ListEnd" -> { expLevel--; }

          default -> throw new InvalidSyntaxException(line, command);
        }

        collapse(nodeStack);
      }
    }

    if (nodeStack.capacity() != 1) {
      throw new IncorrectParameterCountException(4, 3, "blorp");
    }

    return nodeStack.pop();
  }

  private void collapse(Stack<ASTCommand> myStack) {

  }

  public void changeLanguage(String language) {
    cc.changeLanguage(language);
  }
}
