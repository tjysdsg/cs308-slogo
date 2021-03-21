package slogo.model;

import java.util.function.Consumer;
import slogo.events.CommandsRecord;
import slogo.events.DisplayVariable;
import slogo.events.EnvironmentRecord;
import slogo.events.TurtleRecord;
import slogo.events.VariablesRecord;

public interface TrackableScope {

  void setOnEnvironmentUpdate(Consumer<EnvironmentRecord> callback);

  void setOnClear(Runnable callback);

  void requestVariablesUpdate(DisplayVariable variable);

  void requestTurtleUpdate(TurtleRecord record);

  void requestEnvironmentUpdate(EnvironmentRecord record);

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
  void setOnVariableUpdate(Consumer<VariablesRecord> callback);

  /**
   * Sets the callback is called whenever there is an update to the userdefined commands.
   *
   * @param callback - The callback that is called.
   */
  void setOnCommandUpdate(Consumer<CommandsRecord> callback);
}
