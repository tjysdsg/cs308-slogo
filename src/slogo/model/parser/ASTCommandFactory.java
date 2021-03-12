package slogo.model.parser;

import java.lang.reflect.InvocationTargetException;
import slogo.model.ASTNodes.ASTNode;

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
  public static final String classPrefix = "AST";

  /**
   * Get the ASTNode corresponding to the input string
   * @param command The command to find the node for
   * @return The command if found, null if not
   */
  public static ASTNode getCommand(String command) {
    try {
      return (ASTNode) Class.forName(packagePath + classPrefix + command).getConstructor()
          .newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.printf("Class %s not found\n", command);
    }
    return null;
  }
}
