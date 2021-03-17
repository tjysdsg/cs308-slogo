package slogo.model.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ResourceBundle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandClassifierTest {
  private static final String RESOURCES_PACKAGE = "resources.commands.";
  private static final String LANGUAGES = "languages.";

  private String[] languages = new String[] {
      "English", "Spanish", "French"
  };
  private CommandClassifier cc;

  @BeforeEach
  void setUp() {
    cc = ClassifierFactory.buildCommandClassifier("English");
  }

  @Test
  void testChangeLanguage() {
    for (String lang : languages) {
      assertDoesNotThrow(() -> cc.changeLanguage(lang));
      assertDoesNotThrow(() -> checkBundle(lang));
    }
  }

  void checkBundle(String language) {
    String NOMATCH = "NO MATCH";
    String none = "meh";

    ResourceBundle bundle = ResourceBundle.getBundle(RESOURCES_PACKAGE + LANGUAGES + language);
    for (String command : bundle.keySet()) {
      String regex = bundle.getString(command).split("\\|")[0];
      regex = regex.replace("\\", "");
      assertEquals(command, cc.getSymbol(regex));
    }

    assertEquals(NOMATCH, cc.getSymbol(none));
  }
}
