package slogo.model.parser;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import slogo.exceptions.*;
import slogo.model.ASTNodes.*;

public class ProgramParser implements Parser {

  private final ASTCommandFactory factory = new ASTCommandFactory();
  private final TokenClassifier tc = new TokenClassifier();
  private final CommandClassifier cc;
  private static final String SPLITTER = "[ ]|(?<=\\[)|(?=\\[)|(?<=\\])|(?=\\])";

  public ProgramParser(String language) {
    cc = new CommandClassifier(language);
  }

  //TODO: Add Expression Handling and Logic for Errors

  public ASTNode parseCommand(String command)
      throws UnknownIdentifierException, InvalidSyntaxException, IncorrectParameterCountException {
    List<String> lines = Arrays.asList(command.split(SPLITTER));
    String type;
    Stack<Stack<ASTCommand>> scopeStack = new Stack<>();
    scopeStack.push(new Stack<>());
    Stack<ASTCommand> nodeStack;
    int expLevel = 0;
    boolean skipNext = false;

    for (String token : lines) {
      // trim all whitespaces
      // token.trim() doesn't work for symbols such as \t
      // https://stackoverflow.com/a/15633284/7730917
      if (skipNext) {
        skipNext = false;
        continue;
      }

      nodeStack = scopeStack.peek();

      token = token.replaceAll("\\s+", "");
      if (token.length() > 0) {
        type = tc.getSymbol(token);
        System.out.printf("Token:%s, Type:%s\n", token, type);

        //TODO: Must refactor to become better designed and avoid duplication
        switch (type) {
          case "Constant" -> {
            nodeStack.peek().addChild(new ASTNumberLiteral(Double.parseDouble(token)));
          }

          case "Command" -> {
            String commandName = cc.getSymbol(token);
            if (commandName.equals("To")) {
              String identifier = lines.get(lines.indexOf(token) + 1);
              //ASTCommand newCommand = new ASTFunction(identifier);
              skipNext = true;
            } else {
              ASTCommand newCommand = factory.getCommand(commandName);

              if (newCommand == null) {
//                foundFunc = lookUpTable.get(commandName);
//                newCommand = foundFunc.clone();
              }

              if (!nodeStack.isEmpty()) {
                nodeStack.peek().addChild(newCommand);
              }
              nodeStack.push(newCommand);
            }
          }

          case "Variable" -> {
            nodeStack.peek().addChild(new ASTVariable(token));
          }

          case "ListStart" -> {
            expLevel++;
            scopeStack.push(new Stack<>());
          }

          case "ListEnd" -> {
            expLevel--;

            if (stackHasError(nodeStack)) {
              throw new IncorrectParameterCountException(nodeStack.peek());
            }

            scopeStack.pop();
            nodeStack = scopeStack.peek();
            nodeStack.peek().addChild(nodeStack.peek());
          }

          default -> throw new InvalidSyntaxException(token, command);
        }

        collapse(nodeStack);
        printStack(nodeStack);
      }
    }

    nodeStack = scopeStack.peek();
    if (stackHasError(nodeStack)) {
      ASTNode problem = nodeStack.peek();
      throw new IncorrectParameterCountException(problem);
    }

    return nodeStack.pop();
  }

  private void collapse(Stack<ASTCommand> myStack) {
    while (myStack.size() > 1 && myStack.peek().isDone()) {
      myStack.pop();
    }
  }

  public void changeLanguage(String language) {
    cc.changeLanguage(language);
  }

  public static void main(String[] args) {
    Parser myParser = new ProgramParser("English");
    ASTNode res = myParser.parseCommand("MINUS 1");
  }

  public void printStack(Stack<ASTCommand> myStack) {
    for (ASTCommand item : myStack) {
      System.out.println(item.getClass().getName());
    }
  }

  private boolean stackHasError(Stack<ASTCommand> nodeStack) {
    return nodeStack.size() != 1 | !nodeStack.peek().isDone();
  }
}
