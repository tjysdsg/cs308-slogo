package slogo.model.parser;

/**
 * The command classifier interface is meant to be used with the  of the token parser made specifically for commands. It sets up the Token Parser for commands.
 */
public interface CommandClassifier {
  /**
   * Changes the language of the classifier.
   * @param language to use for classification
   */
  public void changePatterns(String language);

  public String getSymbol(String text);
}
