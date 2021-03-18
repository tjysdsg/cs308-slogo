package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private UpdateTurtle updateTurtleCallback;
  private UpdateVariables updateVariablesCallback;
  private UpdateCommands updateCommandsCallback;
  private ClearEnvironment clearEnvironmentCallback;
  private Parser myParser;

  private static final String DEFAULT_LANG = "English";

  public Environment() {
    executionEnvironment = new ExecutionEnvironment();
    myParser = new ProgramParser(DEFAULT_LANG, executionEnvironment);
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
    private Map<String, ASTNode> variableTable;
    private Map<String, ASTFunctionCall> commandTable;
    private boolean isOuterScope = false;

    public ExecutionEnvironment() {
      variableTable = new HashMap<String, ASTNode>();
      commandTable = new HashMap<String, ASTFunctionCall>();
    }

    public ExecutionEnvironment(Map<String, ASTNode> variableTable, Map<String, ASTFunctionCall> commandTable) {
      this.variableTable = variableTable;
      this.commandTable = commandTable;
    }

//    public void addVariable(String idenfier, ASTNode newThing) {
//      variableTable.put(idenfier, newThing);
//      notifyVariableUpdate(null);
//    }

    public ExecutionEnvironment clone() {
     ExecutionEnvironment instance = new ExecutionEnvironment(new HashMap<String, ASTNode> (variableTable), new HashMap<String, ASTFunctionCall>(commandTable));
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
    public Map<String, ASTFunctionCall> getCommandTable() {
      return commandTable;
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
