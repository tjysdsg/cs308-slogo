package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import slogo.events.CommandsRecord;
import slogo.events.DisplayCommand;
import slogo.events.DisplayVariable;
import slogo.events.TurtleRecord;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTFunctionCall;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.ASTNodes.ASTNumberLiteral;
import slogo.model.parser.Parser;
import slogo.model.parser.ProgramParser;

public class Environment implements TrackableEnvironment {

  private List<Turtle> turtles;
  private List<Integer> currTurtles;
  private AbstractBundle executionEnvironment;
  private Consumer<TurtleRecord> updateTurtleCallback;
  private Consumer<VariablesRecord> updateVariablesCallback;
  private Consumer<CommandsRecord> updateCommandsCallback;
  private Runnable clearEnvironmentCallback;
  private Parser myParser;

  private static final String DEFAULT_LANG = "English";

  public Environment() {
    turtles = new ArrayList<>();
    currTurtles = new ArrayList<>();

    executionEnvironment = new ExecutionEnvironment();
    myParser = new ProgramParser(DEFAULT_LANG, executionEnvironment);
    addTurtle();
  }

  public void setOnTurtleUpdate(Consumer<TurtleRecord> callback) {
    updateTurtleCallback = callback;
  }

  public void setOnVariableUpdate(Consumer<VariablesRecord> callback) {
    updateVariablesCallback = callback;
  }

  public void setOnCommandUpdate(Consumer<CommandsRecord> callback) {
    updateCommandsCallback = callback;
  }

  public void runCommand(String command) {
    ASTNode commandTree = myParser.parseCommand(command);
    commandTree.evaluate(executionEnvironment);
  }

  public void setOnClear(Runnable callback) {
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
    executionEnvironment.setMainTurtle(currTurtle);
  }

  public void setCurrTurtle(List<Integer> currTurtles) {
    executionEnvironment.setCurrTurtle(currTurtles);
  }

  private class ExecutionEnvironment extends AbstractBundle {

    private Map<String, ASTNumberLiteral> variableTable;
    private Map<String, ASTFunctionCall> commandTable;
    private boolean isOuterScope = false;

    public ExecutionEnvironment() {
      isOuterScope = true;
      setTurtles(turtles, currTurtles);
    }

    public ExecutionEnvironment(Map<String, ASTNumberLiteral> variableTable,
        Map<String, ASTFunctionCall> commandTable) {
      this.variableTable = variableTable;
      this.commandTable = commandTable;
    }

    @Override
    public ExecutionEnvironment clone() {
      Map<String, ASTNumberLiteral> varCopy = getVars();
      for (var entry : variableTable.entrySet()) {
        varCopy.put(entry.getKey(), new ASTNumberLiteral(entry.getValue().getValue()));
      }

      ExecutionEnvironment instance = new ExecutionEnvironment(
          varCopy,
          new HashMap<>(commandTable));
      instance.isOuterScope = false;
      return instance;
    }


    @Override
    public void notifyTurtleUpdate(TurtleRecord info) {
      if (updateTurtleCallback != null) {
        updateTurtleCallback.accept(info);
      }
    }

    @Override
    public void notifyEnvironmentClear() {
      if (clearEnvironmentCallback != null) {
        clearEnvironmentCallback.run();
      }
    }

    @Override
    public ASTNumberLiteral getVariable(String name) {
      return variableTable.get(name);
    }

    @Override
    public void notifyCommandUpdate(CommandsRecord info) {
      if (updateCommandsCallback != null) {
        updateCommandsCallback.accept(info);
      }
    }

    @Override
    public void notifyVariableUpdate(VariablesRecord info) {
      if (isOuterScope && updateVariablesCallback != null) {
        updateVariablesCallback.accept(info);
      }
    }
  }
}
