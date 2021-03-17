package slogo.model.parser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import slogo.exceptions.*;
import slogo.model.ASTNodes.*;

public class ProgramParser implements Parser {

  private final SyntaxClassifier tc = ClassifierFactory.buildSyntaxClassifier();
  private final CommandClassifier cc;
  private static final String COMMENT_MATCHER = "#.*";
  private static final String SPLITTER = "[ ]|(?<=\\[)|(?=\\[)|(?<=\\])|(?=\\])|\\n";
  private Map<String, ASTFunctionCall> lookUpTable;

  public ProgramParser(String language, Map<String, ASTFunctionCall> table) {
    cc = ClassifierFactory.buildCommandClassifier(language);
    lookUpTable = table;
  }

  public ASTNode parseCommand(String command)
      throws
        UnknownIdentifierException,
        InvalidSyntaxException,
        IncorrectParameterCountException,
        InvalidCommandIdentifierException,
        UnmatchedSquareBracketException {

    // remove comments
    command = command.replaceAll(COMMENT_MATCHER, "");

    List<String> lines = new LinkedList<>(Arrays.asList(command.split(SPLITTER)));
    lines.removeIf(String::isBlank);
    Stack<Scope> scopeStack = new Stack<>();
    scopeStack.push(new Scope());
    boolean skipNext = false;
    int cursor  = -1;
    for (String token : lines) {
      cursor++;
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
            assertNeedsChild(scopeStack.size(), currScope, command, token);
            currScope.push(new ASTNumberLiteral(Double.parseDouble(token)));
          }

          case "Command" -> {
            String commandName = cc.getSymbol(token);
            ASTNode newCommand;

            switch (commandName) {
              case "MakeUserInstruction" -> {
                String identifier = lines.get(cursor + 1);
                if (!tc.getSymbol(identifier).equals("Command")) {
                  throw new InvalidCommandIdentifierException(identifier);
                }

                newCommand = new ASTMakeUserInstruction(identifier, lookUpTable);
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

          case "Variable" -> {
            assertNeedsChild(scopeStack.size(), currScope, command, token);
            currScope.push(new ASTVariable(token));
          }

          case "ListStart" -> scopeStack.push(new Scope());

          case "ListEnd" -> {
//            if (currScope.isIncomplete()) {
//              throw new IncorrectParameterCountException(currScope.peek());
//            }

            Scope prevScope = scopeStack.pop();
            currScope = scopeStack.peek();
            currScope.push(prevScope.getCommands());
          }

//          case "GroupStart" -> {
//            beginGroup = true;
//          }
//
//          case "GroupEnd" -> {
//            beginGroup = false;
//          }

          default -> throw new InvalidSyntaxException(token, command);
        }
      }
    }

    if (scopeStack.size() != 1) {
      throw new UnmatchedSquareBracketException();
    }

    ASTNode out = scopeStack.pop().getCommands();
    if (out.getNumChildren() == 1)
      return out.getChildAt(0);
    return out;
  }

  private void assertNeedsChild(int scopeDepth, Scope currScope, String command, String token) {
    if (scopeDepth == 1 && !currScope.addNextAsChild())
      throw new InvalidSyntaxException(token, command);
  }

  public void changeLanguage(String language) {
    cc.changeLanguage(language);
  }
}
