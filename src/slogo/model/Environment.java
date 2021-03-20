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
  private int mainTurtleIdx = 0;
  private List<Integer> currTurtles;
  private ExecutionEnvironment executionEnvironment;
  private Consumer<TurtleRecord> updateTurtleCallback;
  private Consumer<VariablesRecord> updateVariablesCallback;
  private Consumer<CommandsRecord> updateCommandsCallback;
  private Runnable clearEnvironmentCallback;
  private Parser myParser;
  private int penColorIdx = 0;
  private int backgroundColorIdx = 0;
  private int shapeIdx = 0;

  private static final String DEFAULT_LANG = "English";

  public Environment() {
    executionEnvironment = new ExecutionEnvironment();

    myParser = new ProgramParser(DEFAULT_LANG, executionEnvironment);
    turtles = new ArrayList<>();
    turtles.add(new Turtle(0, executionEnvironment));
    currTurtles = new ArrayList<>();
    currTurtles.add(0);
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

    @Override
    public ExecutionEnvironment clone() {
      HashMap<String, ASTNumberLiteral> varCopy = new HashMap<>();
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
    public void setCurrTurtle(List<Integer> newTurtles) {
      for (int currTurtle : newTurtles) {
        if (currTurtle >= turtles.size()) {
          for (int i = turtles.size(); i <= currTurtle; ++i) {
            Turtle turtle = new Turtle(i, executionEnvironment);
            turtles.add(turtle);
          }
        }
      }

      currTurtles.clear();
      currTurtles.addAll(newTurtles);

      notifyAllTurtleUpdates();
    }

    @Override
    public void setMainTurtle(int idx) {
      mainTurtleIdx = idx;
    }

    @Override
    public int getTotalNumTurtles() {
      return turtles.size();
    }

    @Override
    public List<Turtle> getActiveTurtles() {
      ArrayList<Turtle> ret = new ArrayList<>();
      for (int idx : currTurtles) {
        ret.add(turtles.get(idx));
      }
      return ret;
    }

    @Override
    public List<Turtle> getAllTurtles() {
      return turtles;
    }

    @Override
    public Turtle getMainTurtle() {
      return turtles.get(mainTurtleIdx);
    }

    /**
     * Notify all turtle information
     */
    private void notifyAllTurtleUpdates() {
      for (Turtle t : turtles) {
        notifyTurtleUpdate(
            new TurtleRecord(
                t.getId(), t.getX(), t.getY(), t.getRotation(), t.isVisible(), t.isPenDown()
            )
        );
      }
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

//    @Override
//    public Map<String, ASTFunctionCall> getCommandTable() {
//      return commandTable;
//    }

    @Override
    public ASTFunctionCall getCommand(String name) {
      return commandTable.get(name);
    }

    @Override
    public boolean setCommand(String name, ASTFunctionCall command) {
      boolean ret = !commandTable.containsKey(name);
      if (ret) {
        commandTable.put(name, command);
      }
      if (isOuterScope) {
        ArrayList<DisplayCommand> commands = new ArrayList<>();
        for (var entry : commandTable.entrySet()) {
          commands.add(new DisplayCommand(entry.getKey(), entry.getValue().toString()));
        }
        notifyCommandUpdate(new CommandsRecord(commands));
      }
      return ret;
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

    public int getPenColorIdx() {
      return penColorIdx;
    }

    public void setPenColorIdx(int _penColorIdx) {
      penColorIdx = _penColorIdx;
      // TODO: notify view about changes
    }

    public int getBackgroundColorIdx() {
      return backgroundColorIdx;
    }

    public void setBackgroundColorIdx(int _backgroundColorIdx) {
      backgroundColorIdx = _backgroundColorIdx;
      // TODO: notify view about changes
    }

    public int getShapeIdx() {
      return shapeIdx;
    }

    public void setShapeIdx(int _shapeIdx) {
      shapeIdx = _shapeIdx;
      // TODO: notify view about changes
    }
  }
}
