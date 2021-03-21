package slogo.model.parser.classifiers;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.exceptions.UnknownIdentifierException;

/**
* Simple parser based on regular expressions that matches input strings to kinds of program elements.
*
* @author Robert C. Duvall
*/
public class TokenClassifier implements SyntaxClassifier, CommandClassifier {
  // where to find resources specifically for this class
  private static final String RESOURCES_PACKAGE = "resources.";
  // "types" and the regular expression patterns that recognize those types
  // note, it is a list because order matters (some patterns may be more generic)
  private List<Entry<String, Pattern>> mySymbols;

  /**
   * Create an empty parser
   */
  public TokenClassifier() {
      mySymbols = new ArrayList<>();
  }

  /**
   * Adds the given resource file to this language's recognized types
   */
  public void addPatterns (String syntax) {
      ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PACKAGE + syntax);
      for (String key : Collections.list(resources.getKeys())) {
          String regex = resources.getString(key);
          mySymbols.add(new SimpleEntry<>(key,
              // THIS IS THE IMPORTANT LINE
              Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
      }
  }

  public void changePatterns (String newSyntax) {
    mySymbols.clear();
    addPatterns(newSyntax);
  }

  /**
   * Returns language's type associated with the given text if one exists
   */
  public String getSymbol (String text) throws UnknownIdentifierException {
      for (Entry<String, Pattern> e : mySymbols) {
          if (match(text, e.getValue())) {
              return e.getKey();
          }
      }
      // FIXME: perhaps throw an exception instead
      throw new UnknownIdentifierException(text);
  }

  // Returns true if the given text matches the given regular expression pattern
  private boolean match (String text, Pattern regex) {
      // THIS IS THE OTHER IMPORTANT LINE
      return regex.matcher(text).matches();
  }

//  public static void main(String[] args) {
//      TokenParser pp = new TokenParser();
//      pp.addPatterns("English");
//      pp.addPatterns("Syntax");
//
//      String toTest = "fd [ fd 50 ]";
//      List<String> tokensLeft = Arrays.asList(toTest.split(SPLITTER));
//
//      for (String line : tokensLeft) {
//          if (line.trim().length() > 0) {
//              System.out.println(pp.getSymbol(line));
//          }
//      }
//  }
}
