package slogo.model.notifiers;

import java.util.function.Consumer;
import slogo.records.CommandsRecord;
import slogo.records.DisplayVariable;
import slogo.records.EnvironmentRecord;
import slogo.records.TurtleRecord;
import slogo.records.VariablesRecord;

public interface ModelTracker {
  void setOnEnvUpdate(Consumer<EnvironmentRecord> callback);

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

  void requestTurtleUpdate(TurtleRecord record);

  void requestEnvUpdate(EnvironmentRecord record);

  void requestVarUpdate(DisplayVariable variable);
}
