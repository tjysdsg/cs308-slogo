package slogo.model.parser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import slogo.exceptions.*;
import slogo.model.ASTNodes.*;
import slogo.model.InfoBundle;
import slogo.model.parser.classifiers.SyntaxClassifier;
import slogo.model.parser.factories.ClassifierFactory;
import slogo.model.parser.factories.HandlerFactory;
import slogo.model.parser.handlers.Handler;
import slogo.records.ParserRecord;

/**
 * This class implements the Parser interface
 *
 * it has the same assumptions dependancies and examples as the parser interface
 * @author Oliver Rodas
 * @see slogo.model.parser.Parser
 */
public class ProgramParser implements Parser {

  private final SyntaxClassifier tokenClassifier = ClassifierFactory.buildSyntaxClassifier();
  private static final String NOTHING = "";
  private static final String WHITESPACE = "\\s+";
  private static final String COMMENT_MATCHER = "#.*";
  private static final String SPLITTER = "[ ]|(?<=\\[)|(?=\\[)|(?<=])|(?=])|(?<=\\()|(?=\\()|(?<=\\))|(?=\\))|\\n";
  private final InfoBundle bundle;

  private Stack<ParsingScope> scopeStack;
  private String currCommand;
  private List<String> tokensLeft;
  private HandlerFactory handlerFactory;
  private String language;

  /**
   * Infobundle to interface (lookup table) Factories
   *
   * @param language the language to begin with
   * @param bundle   the bundle to use as a lookup table
   */
  public ProgramParser(String language, InfoBundle bundle) {
    this.bundle = bundle;
    this.language = language;
  }


  public ASTNode parseCommand(String command)
      throws
      UnknownIdentifierException,
      InvalidSyntaxException,
      IncorrectParameterCountException,
      InvalidTokenTypeException,
      UnmatchedBracketException {

    // remove comments
    currCommand = command.replaceAll(COMMENT_MATCHER, NOTHING);

    tokensLeft = new LinkedList<>(Arrays.asList(currCommand.split(SPLITTER)));
    tokensLeft.removeIf(String::isBlank);

    scopeStack = new Stack<>();
    scopeStack.push(new ParsingScope());

    handlerFactory = new HandlerFactory(
        new ParserRecord(
        scopeStack, tokensLeft,
        language, bundle, currCommand));

    String token;

    while (!tokensLeft.isEmpty()) {
      token = tokensLeft.remove(0);
      token = token.replaceAll(WHITESPACE, NOTHING);

      if (token.length() > 0) {
        String type;

        try {
          type = tokenClassifier.getSymbol(token);
        } catch (UnknownIdentifierException e) {
          throw new InvalidSyntaxException(token, command);
        }

        Handler handler = handlerFactory.buildHandler(type);
        handler.handle(token);
      }
    }

    if (scopeStack.size() != 1) {
      throw new UnmatchedBracketException();
    }

    ASTNode out = scopeStack.pop().getCommands();
    if (out.getNumChildren() == 1) {
      return out.getChildAt(0);
    }

    return out;
  }

  public void changeLanguage(String newLanguage) {
    language = newLanguage;
  }
}
