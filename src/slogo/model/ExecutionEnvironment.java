package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import slogo.events.CommandsRecord;
import slogo.events.DisplayCommand;
import slogo.events.DisplayVariable;
import slogo.events.EnvironmentRecord;
import slogo.events.TurtleRecord;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTFunctionCall;
import slogo.model.ASTNodes.ASTNumberLiteral;

public class ExecutionEnvironment implements InfoBundle {

  private Map<String, ASTNumberLiteral> variableTable = new HashMap<>();
  private Map<String, ASTFunctionCall> commandTable = new HashMap<>();
  private Consumer<TurtleRecord> updateTurtleCallback;
  private Consumer<CommandsRecord> updateCommandsCallback;
  private Consumer<VariablesRecord> updateVariablesCallback;
  private Consumer<EnvironmentRecord> updateEnvironmentCallback;
  private Runnable clearEnvironmentCallback;
  private int penColorIdx = 0;
  private int backgroundColorIdx = 0;
  private int shapeIdx = 0;
  private double penSize = 5;
  private Palette palette = new Palette();
  private int mainTurtleIdx = 0;
  private List<Turtle> turtles;
  private List<Integer> currTurtles;

  public ExecutionEnvironment(List<Turtle> turtles, List<Integer> currTurtles) {
    this.turtles = turtles;
    this.currTurtles = currTurtles;
  }

  public ExecutionEnvironment(List<Turtle> turtles, List<Integer> currTurtles, Map<String, ASTNumberLiteral> variableTable,
      Map<String, ASTFunctionCall> commandTable) {
    this(turtles, currTurtles);
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
        turtles, currTurtles,
        varCopy, new HashMap<>(commandTable));

    instance.setOnCommandUpdate(updateCommandsCallback);
    instance.setOnTurtleUpdate(updateTurtleCallback);
    instance.setOnEnvironmentUpdate(updateEnvironmentCallback);
    instance.setOnClear(clearEnvironmentCallback);

    return instance;
  }

  @Override
  public void setCurrTurtle(List<Integer> newTurtles) {
    for (int currTurtle : newTurtles) {
      if (currTurtle >= turtles.size()) {
        for (int i = turtles.size(); i <= currTurtle; ++i) {
          Turtle turtle = new Turtle(i, this);
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
              t.getId(), t.getX(), t.getY(), t.getRotation(), t.isVisible(), t.isPenDown(),
              t.getPenThickness()
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
  public void notifyEnvironmentUpdate() {
    if (updateEnvironmentCallback != null) {
      updateEnvironmentCallback.accept(
          new EnvironmentRecord(
              palette, penColorIdx,
              shapeIdx, backgroundColorIdx,
              mainTurtleIdx, penSize));
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
    ArrayList<DisplayVariable> vars = new ArrayList<>();
    for (var entry : variableTable.entrySet()) {
      double val = entry.getValue().evaluate(this);
      vars.add(new DisplayVariable(entry.getKey(), Double.toString(val)));
    }
    notifyVariableUpdate(new VariablesRecord(vars));

    return ret;
  }

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

    ArrayList<DisplayCommand> commands = new ArrayList<>();
    for (var entry : commandTable.entrySet()) {
      commands.add(new DisplayCommand(entry.getKey(), entry.getValue().toString()));
    }
    notifyCommandUpdate(new CommandsRecord(commands));
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
    if (updateVariablesCallback != null) {
      updateVariablesCallback.accept(info);
    }
  }

  public int getPenColorIdx() {
    return penColorIdx;
  }

  public void setPenColorIdx(int _penColorIdx) {
    penColorIdx = _penColorIdx;
    notifyEnvironmentUpdate();
  }

  public int getBackgroundColorIdx() {
    return backgroundColorIdx;
  }

  public void setBackgroundColorIdx(int _backgroundColorIdx) {
    backgroundColorIdx = _backgroundColorIdx;
    notifyEnvironmentUpdate();
  }

  public int getShapeIdx() {
    return shapeIdx;
  }

  public void setShapeIdx(int _shapeIdx) {
    shapeIdx = _shapeIdx;
    notifyEnvironmentUpdate();
  }

  public void setPalette(int idx, double r, double g, double b) {
    palette.setColor(idx, new Color(r, g, b));
    notifyEnvironmentUpdate();
  }

  public Color getPalette(int idx) {
    return palette.getColor(idx);
  }

  @Override
  public void setPenSize(double newSize) {
    penSize = newSize;
    notifyEnvironmentUpdate();
  }

  public void setOnEnvironmentUpdate(Consumer<EnvironmentRecord> callback) {
    updateEnvironmentCallback = callback;
  }

  public void setOnClear(Runnable callback) {
    this.clearEnvironmentCallback = callback;
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

  public void updateEnvironment(EnvironmentRecord record) {
    backgroundColorIdx = record.currBGColor();
    penColorIdx = record.currPenColor();
    mainTurtleIdx = record.mainTurtle();
    penSize = record.currPenSize();
    palette = record.colors();
  }
}