package slogo.model.parser;

public class CommandClassifier extends TokenParser {
  private static final String PACKAGE = "language.";

  public CommandClassifier(String language) {
    addPatterns(PACKAGE + language);
  }

  public void changeLanguage(String language) {
    changePatterns(PACKAGE + language);
  }
}
