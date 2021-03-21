package slogo.model.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ResourceBundle;
import org.junit.jupiter.api.Test;
import slogo.exceptions.UnknownIdentifierException;
import slogo.model.TestBundle;
import slogo.model.parser.factories.ASTCommandFactory;

public class ASTCommandFactoryTest {
  private static final String RESOURCES_PACKAGE = "resources.";
  private static final String LANGUAGE = "languages.English";
  public static final String packagePath = "slogo.";
  public static final String classPrefix = "model.ASTNodes.AST";
  public static final String none = "meh";

  @Test
  void testCommands() {
    ASTCommandFactory commandFactory = new ASTCommandFactory(new TestBundle());
    ResourceBundle bundle = ResourceBundle.getBundle(RESOURCES_PACKAGE + LANGUAGE);
    for (String command : bundle.keySet()) {
      Class clazz;
      try {
        clazz = Class.forName(packagePath + classPrefix + command);
      } catch (ClassNotFoundException e) {
        //e.printStackTrace();
        continue;
      }

      if (!command.equals("MakeUserInstruction"))
        assertEquals(clazz , commandFactory.getCommand(command).getClass());
    }

    assertThrows(UnknownIdentifierException.class, () -> commandFactory.getCommand(none));
  }
}
