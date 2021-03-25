package slogo.model.parser.classifiers;

/**
 * The command classifier interface is meant to be used with the  of the token parser made
 * specifically for commands. It sets up the Token Parser for commands.
 */
public interface CommandClassifier {

  /**
   * Changes the language of the classifier.
   *
   * @param language to use for classification
   */
  public void changePatterns(String language);

  /**
   * Gets symbol.
   *
   * @param text the text
   * @return the symbol
   */
  public String getSymbol(String text);
}
