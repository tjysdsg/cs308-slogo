package slogo.model.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ResourceBundle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ASTCommandFactoryTest {
  private static final String RESOURCES_PACKAGE = "resources.commands.";
  private static final String LANGUAGE = "languages.English";
  public static final String packagePath = "slogo.";
  public static final String classPrefix = "model.ASTNodes.AST";
  public static final String none = "meh";

  @Test
  void testCommands() {
    ResourceBundle bundle = ResourceBundle.getBundle(RESOURCES_PACKAGE + LANGUAGE);
    for (String command : bundle.keySet()) {
      Class clazz = null;
      try {
        clazz = Class.forName(packagePath + classPrefix + command);
      } catch (ClassNotFoundException e) {
        //e.printStackTrace();
        continue;
      }

      if (!command.equals("MakeUserInstruction"))
        assertEquals(clazz , ASTCommandFactory.getCommand(command).getClass());
    }

    assertNull(ASTCommandFactory.getCommand(none));
  }
}
