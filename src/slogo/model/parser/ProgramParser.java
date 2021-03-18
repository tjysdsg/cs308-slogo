package slogo.model.parser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import slogo.exceptions.*;
import slogo.model.ASTNodes.*;
import slogo.model.InfoBundle;

public class ProgramParser implements Parser {

  private final SyntaxClassifier tc = ClassifierFactory.buildSyntaxClassifier();
  private final CommandClassifier cc;
  private static final String NOTHING = "";
  private static final String WHITESPACE = "\\s+";
  private static final String COMMENT_MATCHER = "#.*";
  private static final String SPLITTER = "[ ]|(?<=\\[)|(?=\\[)|(?<=])|(?=])|\\n";
  private Map<String, ASTFunctionCall> functionTable;
  private ASTCommandFactory commandFactory;
  private static final String LANGUAGES = "languages.";

  private Stack<Scope> scopeStack;
  private Scope currScope;
  private String currCommand;
  private List<String> lines;
  private int cursor;
  private boolean skipNext;

  public ProgramParser(String language, InfoBundle bundle) {
    cc = ClassifierFactory.buildCommandClassifier(language);

    functionTable = bundle.getCommandTable();
    commandFactory = new ASTCommandFactory(functionTable);
  }

  public ASTNode parseCommand(String command)
      throws
        UnknownIdentifierException,
        InvalidSyntaxException,
        IncorrectParameterCountException,
        InvalidCommandIdentifierException,
        UnmatchedSquareBracketException {

    // remove comments
    currCommand = command.replaceAll(COMMENT_MATCHER, NOTHING);

    lines = new LinkedList<>(Arrays.asList(currCommand.split(SPLITTER)));
    lines.removeIf(String::isBlank);

    scopeStack = new Stack<>();
    scopeStack.push(new Scope());

    skipNext = false;
    cursor  = -1;
    String token;

    while (!lines.isEmpty()) {
      token = lines.remove(0);
      token = token.replaceAll(WHITESPACE, NOTHING);

      if (token.length() > 0) {
        currScope = scopeStack.peek();
        String type;

        try {
          type = tc.getSymbol(token);
        } catch (UnknownIdentifierException e) {
          throw new InvalidSyntaxException(token, command);
        }

        try {
          Method handler = this.getClass().getDeclaredMethod("handle" + type, String.class);
          handler.setAccessible(true);
          handler.invoke(this, token);

        } catch (NoSuchMethodException | IllegalAccessException e) {
          System.out.printf("DEBUG: Unimplemented Method Type: %s\n", type);
        } catch (InvocationTargetException e) {
          throw (ModelException) e.getTargetException();
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

  private void handleVariable(String token) {
    assertCanTakeChild(token);
    currScope.push(new ASTVariable(token));
  }

  private void handleCommand(String token) {
    String commandName;

    try {
      commandName = cc.getSymbol(token);
    } catch (UnknownIdentifierException e) {
      commandName = token;
    }

    ASTNode newCommand;

    if(commandName.equals("MakeUserInstruction")) {
      String identifier = assertNextIsCommand(token);

      newCommand = new ASTMakeUserInstruction(identifier, functionTable);

    } else {
      newCommand = commandFactory.getCommand(commandName);
    }

    currScope.push(newCommand);
  }

  private void handleConstant(String token) {
    assertCanTakeChild(token);
    currScope.push(new ASTNumberLiteral(Double.parseDouble(token)));
  }

  private void handleListStart(String token) {
    scopeStack.push(new Scope());
  }

  private void handleListEnd(String token) {
    ASTNode child = popCurrentScope();
    currScope.push(child);
  }

  private void handleGroupStart(String token) {
    String identifier = assertNextIsCommand(token);
    handleCommand(identifier);
    handleListStart(token);
  }

  private void handleGroupEnd(String token) {
    ASTNode orphanage = popCurrentScope();
    currScope.pushAll(orphanage.getChildren());
  }

  private ASTNode popCurrentScope() {
    Scope prevScope = scopeStack.pop();
    currScope = scopeStack.peek();
    return prevScope.getCommands();
  }

  private void assertCanTakeChild(String token) {
    if (scopeStack.size() == 1 && !currScope.addNextAsChild())
      throw new InvalidSyntaxException(token, currCommand);
  }

  private String assertNextIsCommand(String token) {
    if (lines.isEmpty())
      throw new InvalidSyntaxException(token, currCommand);

    String identifier = lines.remove(0);
    if (!tc.getSymbol(identifier).equals("Command")) {
      throw new InvalidCommandIdentifierException(identifier);
    }

    return identifier;
  }

  public void changeLanguage(String language) {
    cc.changePatterns(LANGUAGES + language);
  }


}
