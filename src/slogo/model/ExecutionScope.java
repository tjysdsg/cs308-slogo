package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.events.CommandsRecord;
import slogo.events.DisplayCommand;
import slogo.events.DisplayVariable;
import slogo.events.EnvironmentRecord;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTFunctionCall;
import slogo.model.ASTNodes.ASTMakeVariable;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.ASTNodes.ASTNumberLiteral;
import slogo.model.ASTNodes.ASTVariable;
import slogo.model.notifiers.EnvironmentNotifier;
import slogo.model.notifiers.TurtleNotifier;

public class ExecutionScope implements InfoBundle {

  private Map<String, ASTNumberLiteral> variableTable = new HashMap<>();
  private Map<String, ASTFunctionCall> commandTable = new HashMap<>();
  private int penColorIdx = 0;
  private int backgroundColorIdx = 0;
  private int shapeIdx = 0;
  private double penSize = 5;
  private Palette palette = new Palette();
  private int mainTurtleIdx = 0;
  private List<Turtle> turtles;
  private List<Integer> currTurtles;
  private EnvironmentNotifier envNotifier;
  private TurtleNotifier turtleNotifier;

  public ExecutionScope(List<Turtle> turtles, List<Integer> currTurtles,
      EnvironmentNotifier envNotifier,
      TurtleNotifier turtleNotifier) {
    this.turtles = turtles;
    this.currTurtles = currTurtles;
    this.envNotifier = envNotifier;
    this.turtleNotifier = turtleNotifier;

    envNotifier.onRequestEnvUpdate(record -> {
      backgroundColorIdx = record.currBGColor();
      penColorIdx = record.currPenColor();
      mainTurtleIdx = record.mainTurtle();
      penSize = record.currPenSize();
      palette = (Palette) record.colors();
    });

    envNotifier.onRequestTurtleUpdate(record -> {
      Turtle toUpdate = turtles.get(record.id());
      toUpdate.update(record);
    });

    envNotifier.onRequestVarUpdate(variable -> {
      ASTNode variableSetter = new ASTMakeVariable();
      variableSetter.addChild(new ASTVariable(variable.name()));
      variableSetter.addChild(new ASTNumberLiteral(Double.parseDouble(variable.value())));
      variableSetter.evaluate(this);
    });
  }

  public ExecutionScope(
      List<Turtle> turtles, List<Integer> currTurtles,
      Map<String, ASTNumberLiteral> variableTable,
      Map<String, ASTFunctionCall> commandTable,
      EnvironmentNotifier envNotifier, TurtleNotifier turtleNotifier) {

    this(turtles, currTurtles, envNotifier, turtleNotifier);
    this.variableTable = variableTable;
    this.commandTable = commandTable;
  }

  @Override
  public ExecutionScope clone() {

    HashMap<String, ASTNumberLiteral> varCopy = new HashMap<>();
    for (var entry : variableTable.entrySet()) {
      varCopy.put(entry.getKey(), new ASTNumberLiteral(entry.getValue().getValue()));
    }

    ExecutionScope instance = new ExecutionScope(
        turtles, currTurtles,
        varCopy, new HashMap<>(commandTable),
        envNotifier.clone(), turtleNotifier);

    return instance;
  }

  @Override
  public void setCurrTurtle(List<Integer> newTurtles) {
    for (int currTurtle : newTurtles) {
      if (currTurtle >= turtles.size()) {
        for (int i = turtles.size(); i <= currTurtle; ++i) {
          Turtle turtle = new Turtle(i, turtleNotifier);
          turtles.add(turtle);
        }
      }
    }

    currTurtles.clear();
    currTurtles.addAll(newTurtles);

    notifyAllTurtleUpdates();
  }

  /**
   * Notify all turtle information
   */
  private void notifyAllTurtleUpdates() {
    for (Turtle t : turtles) {
      t.sendUpdate();
    }
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
    envNotifier.notifyVariableUpdate(new VariablesRecord(vars));

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
    envNotifier.notifyCommandUpdate(new CommandsRecord(commands));
    return ret;
  }

  public int getPenColorIdx() {
    return penColorIdx;
  }

  public void setPenColorIdx(int _penColorIdx) {
    penColorIdx = _penColorIdx;
    notifyEnvironment();
  }

  public int getBackgroundColorIdx() {
    return backgroundColorIdx;
  }

  public void setBackgroundColorIdx(int _backgroundColorIdx) {
    backgroundColorIdx = _backgroundColorIdx;
    notifyEnvironment();
  }

   public int getShapeIdx() {
    return shapeIdx;
  }

  public void setShapeIdx(int _shapeIdx) {
    shapeIdx = _shapeIdx;
    notifyEnvironment();
  }

  public void setPalette(int idx, double r, double g, double b) {
    palette.setColor(idx, new Color(r, g, b));
    notifyEnvironment();
  }

  public Color getPalette(int idx) {
    return palette.getColor(idx);
  }

  @Override
  public void setPenSize(double newSize) {
    penSize = newSize;
    notifyEnvironment();
  }

  public void clear() {
    envNotifier.notifyEnvClear();
  }

  private void notifyEnvironment() {
    envNotifier.notifyEnvUpdate(new EnvironmentRecord(
        palette, penColorIdx,
        shapeIdx, backgroundColorIdx,
        mainTurtleIdx, penSize));
  }
}
