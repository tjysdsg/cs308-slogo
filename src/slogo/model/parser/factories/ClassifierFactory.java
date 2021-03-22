package slogo.model.parser.factories;

import slogo.model.parser.classifiers.CommandClassifier;
import slogo.model.parser.classifiers.SyntaxClassifier;
import slogo.model.parser.classifiers.TokenClassifier;

public class ClassifierFactory {
  private static final String PACKAGE = "languages.";
  private static final String BUNDLE = "Syntax";

  public static SyntaxClassifier buildSyntaxClassifier() {
    TokenClassifier instance = new TokenClassifier();
    instance.addPatterns(BUNDLE);
    return instance;
  }

  /**
   * This creates a new instance of the CommandClassifier. It sets the language to use.
   * @param language to use for classification
   */
  public static CommandClassifier buildCommandClassifier(String language) {
    TokenClassifier instance = new TokenClassifier();
    instance.addPatterns(PACKAGE + language);
    return instance;
  }
}
