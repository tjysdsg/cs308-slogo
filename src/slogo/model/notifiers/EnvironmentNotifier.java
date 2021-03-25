package slogo.model.notifiers;

import java.util.function.Consumer;
import slogo.records.CommandsRecord;
import slogo.records.DisplayVariable;
import slogo.records.EnvironmentRecord;
import slogo.records.TurtleRecord;
import slogo.records.VariablesRecord;

/**
 * The Environment notifier interface is used for the model to talk
 * to the view. The model can notify the environment and respond to the listeners
 * set on the model.
 *
 * This class depends on the records
 *
 * This class is meant to be used in conjunction with the model tracker
 * The class that gets this can notify the caller to trigger an action on the
 * user of the model tracker.
 *
 * @author Oliver Rodas
 */
public interface EnvironmentNotifier {

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

  /**
   * Notify the environment was cleared.
   */
  void notifyEnvClear();

  /**
   * Notify the environment updated.
   *
   * @param environmentRecord the environment's record
   */
  void notifyEnvUpdate(EnvironmentRecord environmentRecord);

  /**
   * Set a listener to a request of a variable update.
   *
   * @param callback the callback to trigger
   */
  void onRequestVarUpdate(Consumer<DisplayVariable> callback);

  /**
   * Set a listener to a request to update the turtle.
   *
   * @param callback the callback to trigger
   */
  void onRequestTurtleUpdate(Consumer<TurtleRecord> callback);

  /**
   * Set a listener to a request to update the environment.
   *
   * @param callback the callback to trigger
   */
  void onRequestEnvUpdate(Consumer<EnvironmentRecord> callback);

  /**
   * Clone the notifier
   * @return a new environment that is a clone
   */
  EnvironmentNotifier clone();
}
