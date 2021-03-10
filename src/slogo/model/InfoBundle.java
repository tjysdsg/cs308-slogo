package slogo.model;

import java.util.Map;
import slogo.events.CommandsRecord;
import slogo.events.TurtleRecord;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTNode;

/**
 * Information that ASTNodes required in order to evaluate.
 */
public interface InfoBundle {

  /**
   * @return The turtle within the environment
   */
  Turtle getTurtle();

  /**
   * Notifies the listener of an update to the turtle.
   *
   * @param info - The information to pass to listeners
   */
  void notifyTurtleUpdate(TurtleRecord info);

  /**
   * Notifies the listener of an update to the user-defined commands.
   *
   * @param info - The information to pass to listeners
   */
  void notifyCommandUpdate(CommandsRecord info);

  /**
   * Notifies the listener of an update to the variables within the environment.
   *
   * @param info - The information to pass to listeners
   */
  void notifyVariableUpdate(VariablesRecord info);

  void notifyEnvironmentClear();

  /**
   * Returns the mapping of names to AST trees.
   *
   * @return The table of command names to AST tree.
   */
  Map<String, ASTNode> getLookupTable();
}