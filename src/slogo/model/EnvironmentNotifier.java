package slogo.model;

import java.util.function.Consumer;
import slogo.events.CommandsRecord;
import slogo.events.DisplayVariable;
import slogo.events.EnvironmentRecord;
import slogo.events.TurtleRecord;
import slogo.events.VariablesRecord;

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

  void notifyEnvClear();

  void notifyEnvUpdate(EnvironmentRecord environmentRecord);

  void onRequestVarUpdate(Consumer<DisplayVariable> callback);

  void onRequestTurtleUpdate(Consumer<TurtleRecord> callback);

  void onRequestEnvUpdate(Consumer<EnvironmentRecord> callback);

  EnvironmentNotifier clone();
}
