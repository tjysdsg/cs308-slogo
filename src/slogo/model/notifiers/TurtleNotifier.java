package slogo.model.notifiers;

import slogo.records.TurtleRecord;

public interface TurtleNotifier {

  /**
   * Notifies the listener of an update to the turtle.
   *
   * @param info - The information to pass to listeners
   */
  void notifyTurtleUpdate(TurtleRecord info);
}
