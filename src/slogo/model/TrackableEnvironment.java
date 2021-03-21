package slogo.model;

import java.util.List;

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

  void addTurtle();

  void setCurrTurtle(int turtle);

  void setCurrTurtle(List<Integer> turtle);

  Tracker getTracker();
}
