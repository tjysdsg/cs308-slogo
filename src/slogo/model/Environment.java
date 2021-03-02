package slogo.model;

import slogo.events.CommandsInfo;
import slogo.events.TurtleInfo;
import slogo.events.UpdateCommands;
import slogo.events.UpdateTurtle;
import slogo.events.UpdateVariables;
import slogo.events.VariablesInfo;

public class Environment implements TrackableEnvironment {

  /**
   * Notifies the listener of an update to the turtle.
   *
   * @param info - The information to pass to listeners
   */
  public void notifyTurtleUpdate(TurtleInfo info) {
  }

  /**
   * Notifies the listener of an update to the user-defined commands.
   *
   * @param info - The information to pass to listeners
   */
  public void notifyCommandUpdate(CommandsInfo info) {
  }

  /**
   * Notifies the listener of an update to the variables within the environment.
   *
   * @param info - The information to pass to listeners
   */
  public void notifyVariableUpdate(VariablesInfo info) {
  }

  public void setOnTurtleUpdate(UpdateTurtle callback) {
  }

  public void setOnVariableUpdate(UpdateVariables callback) {
  }

  public void setOnCommandUpdate(UpdateCommands callback) {
  }

  public void runCommand(String command) {
  }
}