package slogo.model;

import java.util.List;
import slogo.events.CommandsRecord;
import slogo.events.EnvironmentRecord;
import slogo.events.TurtleRecord;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTFunctionCall;
import slogo.model.ASTNodes.ASTNumberLiteral;

/**
 * Information that ASTNodes required in order to evaluate.
 */
public interface InfoBundle {

  /**
   * @return The active turtles
   */
  List<Turtle> getActiveTurtles();

  List<Turtle> getAllTurtles();

  Turtle getMainTurtle();

  void setCurrTurtle(List<Integer> currTurtles);

  void setMainTurtle(int idx);

  int getTotalNumTurtles();

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

  void notifyEnvironmentUpdate(EnvironmentRecord info);

  ASTNumberLiteral getVariable(String name);

  /**
   * @return Returns true if a new entry is added to the variable table, otherwise false
   */
  boolean setVariable(String name, ASTNumberLiteral value);

  /**
   * Returns the mapping of command names to command node.
   *
   * @return
   */
//  Map<String, ASTFunctionCall> getCommandTable();

  ASTFunctionCall getCommand(String name);

  boolean setCommand(String name, ASTFunctionCall command);

  InfoBundle clone();

  int getPenColorIdx();

  void setPenColorIdx(int penColorIdx);

  int getBackgroundColorIdx();

  void setBackgroundColorIdx(int backgroundColorIdx);

  int getShapeIdx();

  void setShapeIdx(int shapeIdx);

  void setPalette(int idx, double r, double g, double b);

  Color getPalette(int idx);
}