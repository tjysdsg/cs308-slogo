package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.events.ClearEnvironment;
import slogo.events.CommandsRecord;
import slogo.events.TurtleRecord;
import slogo.events.UpdateCommands;
import slogo.events.UpdateTurtle;
import slogo.events.UpdateVariables;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTFunctionCall;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.parser.Parser;
import slogo.model.parser.ProgramParser;

public class Environment implements TrackableEnvironment {

  private List<Turtle> turtles;
  private int currTurtle;
  private ExecutionEnvironment executionEnvironment;
  private Map<String, ASTNode> lookupTable;
  private UpdateTurtle updateTurtleCallback;
  private UpdateVariables updateVariablesCallback;
  private UpdateCommands updateCommandsCallback;
  private Parser myParser;
  private ClearEnvironment clearEnvironmentCallback;

  private static final String DEFAULT_LANG = "English";

  public Environment() {
    executionEnvironment = new ExecutionEnvironment();
    lookupTable = new HashMap<>();
    myParser = new ProgramParser(DEFAULT_LANG, new HashMap<>());
    turtles = new ArrayList<>();
    turtles.add(new Turtle(currTurtle, executionEnvironment));
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
    ASTNode commandTree = myParser.parseCommand(command);
    commandTree.evaluate(executionEnvironment);
  }

  public void setOnClear(ClearEnvironment callback) {
    this.clearEnvironmentCallback = callback;
  }

  @Override
  public void setBundle(ResourceBundle bundle) {
    // TODO
  }

  /**
   * Add a new turtle and switch to it
   */
  public void addTurtle() {
    int size = turtles.size();
    Turtle turtle = new Turtle(size, executionEnvironment);
    turtles.add(turtle);
    currTurtle = size;
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

    public void notifyEnvironmentClear() {
      if (clearEnvironmentCallback != null) {
        clearEnvironmentCallback.execute();
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
