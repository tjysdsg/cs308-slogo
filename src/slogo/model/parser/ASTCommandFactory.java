package slogo.model.parser;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import slogo.model.ASTNodes.ASTCommand;

/**
 * The ASTCommand factory class is used to convert a string into an ASTNode using reflection.
 *<p>
 * This class assumes that every ASTNode class is inside of the ASTNodes package. Every subclass of ASTNode must contain the NAME static field in order to be found.
 *</p>
 * <p>
 * This class depends on the ASTCommand class, but only for finding its children. It depends on the java reflection package and the file class.
 * </p>
 * <p>
 * This class is used by simply calling getCommand on the class itself to get the ASTNode corresponding to the input string.
 *</p>
 * @author Oliver Rodas
 */
public class ASTCommandFactory {
  public static final String packagePath = "slogo.model.ASTNodes.";
  public static final String dirPath = "src/" + packagePath.replace(".", "/") + "/";

  /**
   * Get the ASTNode corresponding to the input string
   * @param command The command to find the node for
   * @return The command if found, null if not
   */
  public static ASTCommand getCommand(String command) {
    Class[] availNodes = getASTNodes();
    for (Class node : availNodes) {

      if (!isNodeDescendantOfClass(node, ASTCommand.class) | isNodeAbstract(node))
        continue;

      String name = "";
      try {
        Field nameField = node.getDeclaredField("NAME");
        nameField.setAccessible(true);
        name = (String) nameField.get(nameField);
      } catch (NoSuchFieldException | IllegalAccessException e) {
        System.out.printf("%s needs the private field NAME\n", node.getName());
        continue;
      }

      if (name.equalsIgnoreCase(command)) {
        try {
          return (ASTCommand) node.getConstructor().newInstance();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  private static Class[] getASTNodes() {
    File myPackage = new File(dirPath);
    assert(myPackage.isDirectory());

    File[] files = myPackage.listFiles();
    Class[] nodes = new Class[files.length];

    int index = 0;
    for (File currClass : files) {
      try {
        nodes[index] = Class.forName(packagePath + currClass.getName().replace(".java", ""));
        index++;
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    return nodes;
  }

  private static boolean isNodeDescendantOfClass(Class node, Class ancestor) {
    Class parent = node.getSuperclass();
    if (parent == null)
      return false;
    if (parent == ancestor)
      return true;
    return isNodeDescendantOfClass(parent, ancestor);
  }

  private static boolean isNodeAbstract(Class node) {
    return Modifier.isAbstract(node.getModifiers());
  }
}
