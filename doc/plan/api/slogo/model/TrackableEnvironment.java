package slogo.model;

import java.util.ResourceBundle;
import slogo.records.UpdateCommands;
import slogo.records.UpdateTurtle;
import slogo.records.UpdateVariables;

/**
 * An environment that can be monitored for changes that occur and execute commands.
 * <p>
 * This environment holds the callbacks and updates listeners when certain events occur within the
 * environment.
 */
public interface TrackableEnvironment {

  /**
   * Sets the callback is called whenever there is an update to the turtle within the environment.
   *
   * @param callback - The callback that is called.
   */
  void setOnTurtleUpdate(UpdateTurtle callback);

  /**
   * Sets the callback is called whenever there is an update to the variables within the
   * environment.
   *
   * @param callback - The callback that is called.
   */
  void setOnVariableUpdate(UpdateVariables callback);

  /**
   * Sets the callback is called whenever there is an update to the userdefined commands.
   *
   * @param callback - The callback that is called.
   */
  void setOnCommandUpdate(UpdateCommands callback);

  /**
   * Parses and executes a passed in command.
   *
   * @param command - The command to parse and execute.
   */
  void runCommand(String command);

  /**
   * Sets the environmentInfo to use for translating commands
   * @param bundle - The environmentInfo to use.
   */
  void setBundle(ResourceBundle bundle);
}