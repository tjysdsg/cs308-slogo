package slogo.model.parser;

public interface CommandClassifier {
  /**
   * Changes the language of the classifier.
   * @param language to use for classification
   */
  public void changePatterns(String language);
  public String getSymbol(String text);
}
