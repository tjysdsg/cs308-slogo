package slogo.model.notifiers;

import slogo.records.TurtleRecord;

/**
 * The Turtle notifier interface is used for a turtle to notify the environment
 * that a turtle has changed a value.
 *
 * @author Oliver Rodas
 */
public interface TurtleNotifier {

  /**
   * Notifies the listener of an update to the turtle.
   *
   * @param info - The information to pass to listeners
   */
  void notifyTurtleUpdate(TurtleRecord info);
}
