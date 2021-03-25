package slogo.model.parser.factories;

import slogo.model.parser.classifiers.CommandClassifier;
import slogo.model.parser.classifiers.SyntaxClassifier;
import slogo.model.parser.classifiers.TokenClassifier;

/**
 * The classifier factory, is meant to encapsulate the creation of a token classifier. There are different
 * token classifiers that require different setups, but do the same thing.
 * <p>
 * This class assumes that each resource bundles for the classifiers are defined
 * </p>
 * <p>
 * This class depends on the classifiers package
 * </p>
 * <p>
 * This class is used by simply calling the build classifier methods.
 * </p>
 *
 * @author Oliver Rodas
 */
public class ClassifierFactory {
  private static final String PACKAGE = "languages.";
  private static final String BUNDLE = "Syntax";

  /**
   * Build a syntax classifier
   *
   * @return the syntax classifier
   */
  public static SyntaxClassifier buildSyntaxClassifier() {
    TokenClassifier instance = new TokenClassifier();
    instance.addPatterns(BUNDLE);
    return instance;
  }

  /**
   * This creates a new instance of the CommandClassifier. It sets the language to use.
   *
   * @param language to use for classification
   * @return the command classifier
   */
  public static CommandClassifier buildCommandClassifier(String language) {
    TokenClassifier instance = new TokenClassifier();
    instance.addPatterns(PACKAGE + language);
    return instance;
  }
}
