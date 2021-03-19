package slogo.model.parser;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import slogo.exceptions.UnknownIdentifierException;
import slogo.model.ASTNodes.ASTCommand;
import slogo.model.ASTNodes.ASTFunctionCall;
import slogo.model.ASTNodes.ASTFunctionReference;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.InfoBundle;

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

  public static final String packagePath = ASTCommand.class.getPackageName() + ".";
  public static final String classPrefix = "AST";
  private InfoBundle functionTable;

  public ASTCommandFactory(InfoBundle bundle) {
    functionTable = bundle;
  }


  /**
   * Get the ASTNode corresponding to the input string
   * @param command The command to find the node for
   * @return The command if found, null if not
   */
  public ASTNode getCommand(String command) throws UnknownIdentifierException {
    try {
      return (ASTNode) Class.forName(packagePath + classPrefix + command).getConstructor()
          .newInstance();
    } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      ASTFunctionCall foundFunc = functionTable.getCommand(command);
      if (foundFunc == null) {
        throw new UnknownIdentifierException(command);
      }
      return foundFunc.createReference();
    }

    throw new UnknownIdentifierException(command);
  }
}
