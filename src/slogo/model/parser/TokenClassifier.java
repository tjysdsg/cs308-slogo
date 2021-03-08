package slogo.model.parser;

public class TokenClassifier extends TokenParser {
  private static final String BUNDLE = "Syntax";
  public TokenClassifier() {
    addPatterns(BUNDLE);
  }
}
