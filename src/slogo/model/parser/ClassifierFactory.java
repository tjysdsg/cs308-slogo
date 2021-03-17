package slogo.model.parser;

public class ClassifierFactory {
  private static final String PACKAGE = "languages.";
  private static final String BUNDLE = "Syntax";

  public static SyntaxClassifier buildSyntaxClassifier() {
    TokenParser instance = new TokenParser();
    instance.addPatterns(BUNDLE);
    return instance;
  }

  /**
   * This creates a new instance of the CommandClassifier. It sets the language to use.
   * @param language to use for classification
   */
  public static CommandClassifier buildCommandClassifier(String language) {
    TokenParser instance = new TokenParser();
    instance.addPatterns(PACKAGE + language);
    return instance;
  }
}
