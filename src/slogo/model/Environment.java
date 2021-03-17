package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.events.ClearEnvironment;
import slogo.events.CommandsRecord;
import slogo.events.DisplayVariable;
import slogo.events.TurtleRecord;
import slogo.events.UpdateCommands;
import slogo.events.UpdateTurtle;
import slogo.events.UpdateVariables;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTFunctionCall;
import slogo.model.ASTNodes.ASTMakeUserInstruction;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.ASTNodes.ASTNumberLiteral;
import slogo.model.ASTNodes.ASTVariable;
import slogo.model.parser.Parser;
import slogo.model.parser.ProgramParser;

public class Environment implements TrackableEnvironment {

  private List<Turtle> turtles;
  private List<Integer> currTurtles;
  private ExecutionEnvironment executionEnvironment;
  private UpdateTurtle updateTurtleCallback;
  private UpdateVariables updateVariablesCallback;
  private UpdateCommands updateCommandsCallback;
  private ClearEnvironment clearEnvironmentCallback;
  private Parser myParser;

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

    private Map<String, ASTNumberLiteral> variableTable;
    private Map<String, ASTFunctionCall> commandTable;
    private boolean isOuterScope = false;

    public ExecutionEnvironment() {
      variableTable = new HashMap<>();
      commandTable = new HashMap<>();
      isOuterScope = true;
    }

    public ExecutionEnvironment(Map<String, ASTNumberLiteral> variableTable,
        Map<String, ASTFunctionCall> commandTable) {
      this.variableTable = variableTable;
      this.commandTable = commandTable;
    }

    public ExecutionEnvironment clone() {
      ExecutionEnvironment instance = new ExecutionEnvironment(
          new HashMap<>(variableTable),
          new HashMap<>(commandTable));
      instance.isOuterScope = false;
      return instance;
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

    public void setCurrTurtle() {
      // TODO: implement this
    }

    public void notifyEnvironmentClear() {
      if (clearEnvironmentCallback != null) {
        clearEnvironmentCallback.execute();
      }
    }

    @Override
    public ASTNumberLiteral getVariable(String name) {
      return variableTable.get(name);
    }

    @Override
    public boolean setVariable(String name, ASTNumberLiteral value) {
      boolean ret = !variableTable.containsKey(name);
      variableTable.put(name, value);
      if (isOuterScope) {
        ArrayList<DisplayVariable> vars = new ArrayList<>();
        for (var entry : variableTable.entrySet()) {
          double val = entry.getValue().evaluate(this);
          vars.add(new DisplayVariable(entry.getKey(), Double.toString(val)));
        }
        notifyVariableUpdate(new VariablesRecord(vars));
      }
      return ret;
    }

    @Override
    public Map<String, ASTFunctionCall> getCommandTable() {
      return commandTable;
    }

    @Override
    public ASTMakeUserInstruction getCommand(String name) {
      return null;
    }

    public void notifyCommandUpdate(CommandsRecord info) {
      if (updateCommandsCallback != null) {
        updateCommandsCallback.execute(info);
      }
    }

    public void notifyVariableUpdate(VariablesRecord info) {
      if (isOuterScope && updateVariablesCallback != null) {
        updateVariablesCallback.execute(info);
      }
    }
  }
}
