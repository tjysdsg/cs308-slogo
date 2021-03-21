package slogo.model.notifiers;

import java.util.function.Consumer;
import slogo.events.CommandsRecord;
import slogo.events.DisplayVariable;
import slogo.events.EnvironmentRecord;
import slogo.events.TurtleRecord;
import slogo.events.VariablesRecord;

public class Delegate implements ModelTracker, EnvironmentNotifier, TurtleNotifier {

  private Consumer<TurtleRecord> updateTurtleCallback;
  private Consumer<CommandsRecord> updateCommandsCallback;
  private Consumer<VariablesRecord> updateVariablesCallback;
  private Consumer<EnvironmentRecord> updateEnvironmentCallback;
  private Runnable clearEnvironmentCallback;

  private Consumer<TurtleRecord> requestTurtleCallback;
  private Consumer<DisplayVariable> requestVarCallback;
  private Consumer<EnvironmentRecord> requestEnvCallback;

  public Delegate() {};

  public Delegate(
      Consumer<TurtleRecord> updateTurtleCallback,
      Consumer<CommandsRecord> updateCommandsCallback,
      Consumer<EnvironmentRecord> updateEnvironmentCallback,
      Runnable clearEnvironmentCallback) {

    this.updateTurtleCallback = updateTurtleCallback;
    this.updateCommandsCallback = updateCommandsCallback;
    this.updateEnvironmentCallback = updateEnvironmentCallback;
    this.clearEnvironmentCallback = clearEnvironmentCallback;
  }

  @Override
  public EnvironmentNotifier clone() {
    return new Delegate(
        updateTurtleCallback,
        updateCommandsCallback,
        updateEnvironmentCallback,
        clearEnvironmentCallback);
  }

  @Override
  public void notifyCommandUpdate(CommandsRecord info) {
    if (updateCommandsCallback != null) {
      updateCommandsCallback.accept(info);
    }
  }

  @Override
  public void notifyVariableUpdate(VariablesRecord info) {
    if (updateVariablesCallback != null) {
      updateVariablesCallback.accept(info);
    }
  }

  @Override
  public void notifyTurtleUpdate(TurtleRecord info) {
    if (updateTurtleCallback != null) {
      updateTurtleCallback.accept(info);
    }
  }

  @Override
  public void notifyEnvClear() {
    if (clearEnvironmentCallback != null) {
      clearEnvironmentCallback.run();
    }
  }

  @Override
  public void notifyEnvUpdate(EnvironmentRecord record) {
    if (updateEnvironmentCallback != null) {
      updateEnvironmentCallback.accept(record);
    }
  }

  public void setOnEnvUpdate(Consumer<EnvironmentRecord> callback) {
    updateEnvironmentCallback = callback;
  }

  public void setOnClear(Runnable callback) {
    this.clearEnvironmentCallback = callback;
  }

  public void setOnTurtleUpdate(Consumer<TurtleRecord> callback) {
    updateTurtleCallback = callback;
  }

  public void setOnVarUpdate(Consumer<VariablesRecord> callback) {
    updateVariablesCallback = callback;
  }

  public void setOnCommandUpdate(Consumer<CommandsRecord> callback) {
    updateCommandsCallback = callback;
  }

  public void requestVarUpdate(DisplayVariable variable) {
    if (requestVarCallback != null) {
      requestVarCallback.accept(variable);
    }
  }

  @Override
  public void requestTurtleUpdate(TurtleRecord record) {
    if (requestTurtleCallback != null) {
      requestTurtleCallback.accept(record);
    }
  }

  public void requestEnvUpdate(EnvironmentRecord record) {
    if (requestEnvCallback != null) {
      requestEnvCallback.accept(record);
    }
  }

  public void onRequestVarUpdate(Consumer<DisplayVariable> callback) {
    requestVarCallback = callback;
  }

  public void onRequestTurtleUpdate(Consumer<TurtleRecord> callback) {
    requestTurtleCallback = callback;
  }

  public void onRequestEnvUpdate(Consumer<EnvironmentRecord> callback) {
    requestEnvCallback = callback;
  }
}
