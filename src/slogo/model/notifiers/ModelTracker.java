package slogo.model.notifiers;

import java.util.function.Consumer;
import slogo.records.CommandsRecord;
import slogo.records.DisplayVariable;
import slogo.records.EnvironmentRecord;
import slogo.records.TurtleRecord;
import slogo.records.VariablesRecord;

/**
 * The interface Model tracker is is used for the view to talk
 * to the model. The view can notify the model and respond to the listeners
 * set on the view.
 *
 * This class depends on the records
 *
 * This class is meant to be used in conjunction with the environment notifier
 * The class that gets this can notify the caller to trigger an action on the
 * user of the model tracker.
 *
 * @author Oliver Rodas
 */
public interface ModelTracker {

  /**
   * Set a listener to an update on the environment
   *
   * @param callback the callback to trigger
   */
  void setOnEnvUpdate(Consumer<EnvironmentRecord> callback);

  /**
   * Set a listener to the environment being cleared
   *
   * @param callback the callback to trigger
   */
  void setOnClear(Runnable callback);

  /**
   * Sets the callback is called whenever there is an update to the turtle within the environment.
   *
   * @param callback - The callback that is called.
   */
  void setOnTurtleUpdate(Consumer<TurtleRecord> callback);

  /**
   * Sets the callback is called whenever there is an update to the variables within the
   * environment.
   *
   * @param callback - The callback that is called.
   */
  void setOnVarUpdate(Consumer<VariablesRecord> callback);

  /**
   * Sets the callback is called whenever there is an update to the userdefined commands.
   *
   * @param callback - The callback that is called.
   */
  void setOnCommandUpdate(Consumer<CommandsRecord> callback);

  /**
   * Request a turtle to update.
   *
   * @param record the record to use to update a turtle
   */
  void requestTurtleUpdate(TurtleRecord record);

  /**
   * Request the environment to update.
   *
   * @param record the record to use to update
   */
  void requestEnvUpdate(EnvironmentRecord record);

  /**
   * Request a variable to update.
   *
   * @param variable the variable to update
   */
  void requestVarUpdate(DisplayVariable variable);
}
