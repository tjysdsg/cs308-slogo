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
  private List<Integer> currTurtles;
  private ExecutionEnvironment executionEnvironment;
  private Map<String, ASTNode> variableTable;
  private Map<String, ASTNode> commandTable;
  private UpdateTurtle updateTurtleCallback;
  private UpdateVariables updateVariablesCallback;
  private UpdateCommands updateCommandsCallback;
  private Parser myParser;
  private ClearEnvironment clearEnvironmentCallback;

  private static final String DEFAULT_LANG = "English";

  public Environment() {
    executionEnvironment = new ExecutionEnvironment();
    myParser = new ProgramParser(DEFAULT_LANG, new HashMap<>());
    turtles = new ArrayList<>();
    turtles.add(new Turtle(0, executionEnvironment));
    currTurtles = new ArrayList<>();
    currTurtles.add(0);
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

  public void setLanguage(String language) {
    myParser.changeLanguage(language);
  }

  public void addTurtle() {
    Turtle turtle = new Turtle(turtles.size(), executionEnvironment);
    currTurtles.add(turtles.size());
    turtles.add(turtle);
  }

  public void setCurrTurtle(int currTurtle) {
    if (currTurtle >= turtles.size()) {
      for (int i = turtles.size(); i <= currTurtle; ++i) {
        Turtle turtle = new Turtle(i, executionEnvironment);
        this.turtles.add(turtle);
      }
    }

    this.currTurtles.clear();
    this.currTurtles.add(currTurtle);
  }

  public void setCurrTurtle(List<Integer> currTurtles) {
    for (int currTurtle : currTurtles) {
      if (currTurtle >= turtles.size()) {
        for (int i = turtles.size(); i <= currTurtle; ++i) {
          Turtle turtle = new Turtle(i, executionEnvironment);
          this.turtles.add(turtle);
        }
      }
    }

    this.currTurtles.clear();
    this.currTurtles.addAll(currTurtles);
  }

  private class ExecutionEnvironment implements InfoBundle {

    public ExecutionEnvironment() {
      variableTable = new VariableTable(this);
      commandTable = new CommandTable(this);
    }

    @Override
    public List<Turtle> getActiveTurtles() {
      ArrayList<Turtle> ret = new ArrayList<>();
      for (int idx : currTurtles) {
        ret.add(turtles.get(idx));
      }
      return ret;
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

    @Override
    public Map<String, ASTNode> getVariableTable() {
      return variableTable;
    }

    @Override
    public Map<String, ASTNode> getCommandTable() {
      return commandTable;
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
