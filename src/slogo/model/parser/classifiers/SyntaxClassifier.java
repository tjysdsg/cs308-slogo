package slogo.model.parser.classifiers;

/**
 * The interface Syntax classifier, specifically used to classify a type of symbol.
 */
public interface SyntaxClassifier {

  /**
   * Gets the symbol for the type of token input.
   *
   * @param text the text to classify
   * @return the classification of the text
   */
  String getSymbol(String text);
}
