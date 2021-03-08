package slogo.model.parser;

/**
 * The command classifier class is an extension of the token parser made specifically for commands. It sets up the Token Parser for commands.
 * <p>This class assumes that every command's regex is in the resource bundle.</p>
 * <p>This class depends on the token parser class only.</p>
 * <p>This class is used by creating it with the language to begin with. Then it can be called with a command to find the global label for</p>
 */
public class CommandClassifier extends TokenParser {
  private static final String PACKAGE = "languages.";

  /**
   * This creates a new instance of the CommandClassifier. It sets the language to use.
   * @param language to use for classification
   */
  public CommandClassifier(String language) {
    addPatterns(PACKAGE + language);
  }

  /**
   * Changes the language of the classifier.
   * @param language to use for classification
   */
  public void changeLanguage(String language) {
    changePatterns(PACKAGE + language);
  }
}
