package slogo.model.parser;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import slogo.model.ASTNodes.ASTCommand;

public class ASTCommandFactory {
  // FIXME: Find better way of referencing the root package of ASTNode
  public static final String packagePath = "slogo.model.ASTNodes.";
  public static final String dirPath = "src/" + packagePath.replace(".", "/") + "/";

  public ASTCommandFactory() {
    //TODO: Set languages to get the commands
  }

  public ASTCommand getCommand(String command) {
    //TODO: get the commands
    Class[] availNodes = getNodes();
    for (Class node : availNodes) {

      if (isNotCommand(node) | isAbstract(node))
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

    // TODO: Find the nodes
    return null;
  }

  private Class[] getNodes() {
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

  private boolean isNotCommand(Class node) {
    Class ancestor = node.getSuperclass();
    if (ancestor == null)
      return true;
    if (ancestor == ASTCommand.class)
      return false;
    return isNotCommand(ancestor);
  }

  private boolean isAbstract(Class node) {
    return Modifier.isAbstract(node.getModifiers());
  }
}
