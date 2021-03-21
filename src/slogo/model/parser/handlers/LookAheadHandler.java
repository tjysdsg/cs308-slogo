package slogo.model.parser.handlers;

import java.util.List;
import slogo.exceptions.InvalidTokenTypeException;
import slogo.exceptions.NotEnoughTokensException;
import slogo.model.parser.factories.ClassifierFactory;
import slogo.model.parser.classifiers.SyntaxClassifier;

public abstract class LookAheadHandler implements Handler {

  private List<String> tokensLeft;
  private SyntaxClassifier syntaxClassifier =
      ClassifierFactory.buildSyntaxClassifier();

  private static final int LAST = 0;

  public LookAheadHandler(List<String> tokensLeft) {
    this.tokensLeft = tokensLeft;
  }

  protected String assertNextIs(String currentToken, String expectedType) {
    if (tokensLeft.isEmpty())
      throw new NotEnoughTokensException(currentToken, expectedType);

    String nextToken = tokensLeft.remove(LAST);
    String actualType = syntaxClassifier.getSymbol(nextToken);
    if (!actualType.equals(expectedType)) {
      throw new InvalidTokenTypeException
          (nextToken, expectedType, currentToken, actualType);
    }

    return nextToken;
  }
}
