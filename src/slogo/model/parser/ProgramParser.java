package slogo.model.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import slogo.exceptions.*;
import slogo.model.ASTNodes.*;

public class ProgramParser implements Parser {

  private final TokenClassifier tc = new TokenClassifier();
  private final CommandClassifier cc;
  private static final String SPLITTER = "[ \\n]|(?<=\\[)|(?=\\[)|(?<=\\])|(?=\\])";
  private static final String COMMENT_MATCHER = "#.*";
  private Map<String, ASTFunctionCall> lookUpTable;

  public ProgramParser(String language, Map<String, ASTFunctionCall> table) {
    cc = new CommandClassifier(language);
    lookUpTable = table;
  }

  public ASTNode parseCommand(String command)
      throws UnknownIdentifierException, InvalidSyntaxException, IncorrectParameterCountException {

    // remove comments
    command = command.replaceAll(COMMENT_MATCHER, "");

    List<String> lines = new LinkedList<>(Arrays.asList(command.split(SPLITTER)));
    lines.removeIf(String::isBlank);
    Stack<Scope> scopeStack = new Stack<>();
    scopeStack.push(new Scope());
    boolean skipNext = false;

    for (String token : lines) {
      // trim all whitespaces
      // token.trim() doesn't work for symbols such as \t
      // https://stackoverflow.com/a/15633284/7730917
      if (skipNext) {
        skipNext = false;
        continue;
      }

      Scope currScope = scopeStack.peek();

      token = token.replaceAll("\\s+", "");
      if (token.length() > 0) {
        String type = tc.getSymbol(token);

        //TODO: Try to avoid using a switch statement
        switch (type) {
          case "Constant" -> {
            currScope.push(new ASTNumberLiteral(Double.parseDouble(token)));
          }

          case "Command" -> {
            String commandName = cc.getSymbol(token);
            ASTNode newCommand;
            switch (commandName) {
              case "MakeUserInstruction" -> {
                String identifier = lines.get(lines.indexOf(token) + 1);
                newCommand = new ASTFunctionDefinition(identifier, lookUpTable);
                skipNext = true;
              }

              case "NO MATCH" -> {
                ASTFunctionCall foundFunc = lookUpTable.get(token);
                if (foundFunc == null) {
                  throw new UnknownIdentifierException(token);
                }
                newCommand = foundFunc.clone();
              }

              default -> newCommand = ASTCommandFactory.getCommand(commandName);
            }

            currScope.push(newCommand);
          }

          case "Variable" -> currScope.push(new ASTVariable(token));

          case "ListStart" -> scopeStack.push(new Scope());

          case "ListEnd" -> {
            if (currScope.isIncomplete()) {
              throw new IncorrectParameterCountException(currScope.peek());
            }

            Scope prevScope = scopeStack.pop();
            currScope = scopeStack.peek();
            currScope.peek().addChild(prevScope.getCommands());
          }

          default -> throw new InvalidSyntaxException(token, command);
        }

        currScope.collapse();
      }
    }

    Scope currScope = scopeStack.peek();
    if (currScope.isIncomplete() || scopeStack.size() != 1) {
      ASTNode problem = currScope.peek();
      throw new IncorrectParameterCountException(problem);
    }

    return currScope.getCommands();
  }

  public void changeLanguage(String language) {
    cc.changeLanguage(language);
  }

  public static void main(String[] args) {
    Parser parser = new ProgramParser("English", new HashMap<>());
    parser.parseCommand("""
        to dash [ :count ]
        [
            repeat :count
                [
                pu fd 4 pd fd 4
          ]      
        ]

            cs
                home
            dash 10""");
  }
}
