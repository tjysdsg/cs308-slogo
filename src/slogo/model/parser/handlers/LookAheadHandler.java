package slogo.model.parser.handlers;

import java.util.List;
import slogo.exceptions.InvalidTokenTypeException;
import slogo.exceptions.NotEnoughTokensException;
import slogo.model.parser.factories.ClassifierFactory;
import slogo.model.parser.classifiers.SyntaxClassifier;

/**
 * The type lookahead handler. It is a type of handler that expects a specific token type next. It
 * has the same notes as a handler, but is dependent on more classes
 * <p>
 * This class depends on the exceptions, factories, and classifier packages
 *
 * @author Oliver Rodas
 * @see Handler
 */
public abstract class LookAheadHandler implements Handler {

  private List<String> tokensLeft;
  private SyntaxClassifier syntaxClassifier =
      ClassifierFactory.buildSyntaxClassifier();

  private static final int LAST = 0;

  /**
   * Instantiates a new Look ahead handler.
   *
   * @param tokensLeft the tokens left
   */
  public LookAheadHandler(List<String> tokensLeft) {
    this.tokensLeft = tokensLeft;
  }

  /**
   * Assert next is of a type.
   *
   * @param currentToken the current token
   * @param expectedType the expected type
   * @return the next token
   */
  protected String assertNextIs(String currentToken, String expectedType) {
    if (tokensLeft.isEmpty())
      throw new NotEnoughTokensException(currentToken, expectedType);

    String nextToken = tokensLeft.remove(LAST);
    String actualType = syntaxClassifier.getSymbol(nextToken);
    if (!actualType.equals(expectedType)) {
      throw new InvalidTokenTypeException
          (currentToken, expectedType, nextToken, actualType);
    }

    return nextToken;
  }
}
