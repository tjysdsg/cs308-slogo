package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.events.CommandsRecord;
import slogo.events.TurtleRecord;
import slogo.events.UpdateCommands;
import slogo.events.UpdateTurtle;
import slogo.events.UpdateVariables;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTNode;

public class Environment implements TrackableEnvironment {

  private List<Turtle> turtles;
  private int currTurtle;
  private ExecutionEnvironment executionEnvironment;
  private Map<String, ASTNode> lookupTable;
  private UpdateTurtle updateTurtleCallback;
  private UpdateVariables updateVariablesCallback;
  private UpdateCommands updateCommandsCallback;

  public Environment() {
    executionEnvironment = new ExecutionEnvironment();
    lookupTable = new HashMap<>();

    turtles = new ArrayList<>();
    turtles.add(new Turtle(executionEnvironment));
    currTurtle = 0;
  }

  public void setOnTurtleUpdate(UpdateTurtle callback) {
    updateTurtleCallback = callback;
  }

  public void setOnVariableUpdate(UpdateVariables callback) {
    updateVariablesCallback = callback;
  }

  public void setOnCommandUpdate(UpdateCommands callback) {
    updateCommandsCallback = callback;
  }

  public void runCommand(String command) {
    // TODO
  }

  @Override
  public void setBundle(ResourceBundle bundle) {
    // TODO
  }

  /**
   * Add a new turtle and switch to it
   */
  public void addTurtle() {
    Turtle turtle = new Turtle(executionEnvironment);
    turtles.add(turtle);
    currTurtle = turtles.size() - 1;
  }

  public void setCurrTurtle(int currTurtle) {
    if (currTurtle >= turtles.size()) {
      // FIXME: exception class
      throw new RuntimeException(
          String.format("Unable to set current turtle index to %d", currTurtle));
    }
    this.currTurtle = currTurtle;
  }

  private class ExecutionEnvironment implements InfoBundle {

    @Override
    public Turtle getTurtle() {
      return turtles.get(currTurtle);
    }

    @Override
    public Map<String, ASTNode> getLookupTable() {
      return lookupTable;
    }

    public void notifyTurtleUpdate(TurtleRecord info) {
      if (updateTurtleCallback != null) {
        updateTurtleCallback.execute(info);
      }
    }

    public void notifyCommandUpdate(CommandsRecord info) {
      if (updateCommandsCallback != null) {
        updateCommandsCallback.execute(info);
      }
    }

    public void notifyVariableUpdate(VariablesRecord info) {
      if (updateVariablesCallback != null) {
        updateVariablesCallback.execute(info);
      }
    }
  }
}