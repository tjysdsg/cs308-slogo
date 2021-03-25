package slogo.model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import slogo.model.notifiers.ModelTracker;

/**
 * An environment that can be monitored for changes that occur and execute commands.
 * <p>
 * This environment holds the callbacks and updates listeners when certain events occur within the
 * environment.
 */
public interface TrackableEnvironment {

  /**
   * Parses and executes a passed in command.
   *
   * @param command - The command to parse and execute.
   */
  void runCommand(String command);

  /**
   * Sets the language to use for translating commands
   *
   * @param language - The language to use.
   */
  void setLanguage(String language);

  /**
   * Add turtle.
   */
  void addTurtle();

  /**
   * Sets curr turtle.
   *
   * @param turtle the turtle
   */
  void setCurrTurtle(int turtle);

  /**
   * Sets curr turtle.
   *
   * @param turtle the turtle
   */
  void setCurrTurtle(List<Integer> turtle);

  /**
   * Gets tracker.
   *
   * @return the tracker
   */
  ModelTracker getTracker();

  /**
   * Save.
   *
   * @param saveLocation the save location
   */
  void save(File saveLocation);

  /**
   * Load.
   *
   * @param loadLocation the load location
   */
  void load(File loadLocation);
}
