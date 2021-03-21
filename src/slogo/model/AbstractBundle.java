package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.events.CommandsRecord;
import slogo.events.DisplayCommand;
import slogo.events.DisplayVariable;
import slogo.events.TurtleRecord;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTFunctionCall;
import slogo.model.ASTNodes.ASTNumberLiteral;

abstract class AbstractBundle implements InfoBundle {

  private List<Turtle> turtles;
  private int mainTurtleIdx = 0;
  private List<Integer> currTurtles;
  private Map<String, ASTNumberLiteral> variableTable;
  private Map<String, ASTFunctionCall> commandTable;
  private boolean isOuterScope = false;

  public AbstractBundle() {
    variableTable = new HashMap<>();
    commandTable = new HashMap<>();
  }

  public AbstractBundle(
      Map<String, ASTNumberLiteral> variableTable, Map<String, ASTFunctionCall> commandTable) {
    this.variableTable = variableTable;
    this.commandTable = commandTable;
  }

  @Override
  public abstract InfoBundle clone();

  protected Map<String, ASTNumberLiteral> getVars() {
    return this.variableTable;
  }

  protected Map<String, ASTFunctionCall> getCommands() {
    return this.commandTable;
  }

  public void setTurtles(List<Turtle> turtles, List<Integer> currTurtles) {
    this.turtles = turtles;
    this.currTurtles = currTurtles;
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
    for (Turtle t : turtles) {
      notifyTurtleUpdate(
          new TurtleRecord(
              t.getId(),
              t.getX(),
              t.getY(),
              t.getRotation(),
              t.isVisible(),
              t.isPenDown(),
              t.getPenThickness()));
    }

  }

  @Override
  public void setMainTurtle(int idx) {
    mainTurtleIdx = idx;
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
  public Turtle getMainTurtle() {
    return turtles.get(mainTurtleIdx);
  }


  @Override
  public abstract void notifyTurtleUpdate(TurtleRecord info);

  @Override
  public abstract void notifyEnvironmentClear();

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
    ArrayList<DisplayCommand> commands = new ArrayList<>();
    for (var entry : commandTable.entrySet()) {
      commands.add(new DisplayCommand(entry.getKey(), entry.getValue().toString()));
    }
    notifyCommandUpdate(new CommandsRecord(commands));
    return ret;
  }

  public abstract void notifyCommandUpdate(CommandsRecord info);

  public abstract void notifyVariableUpdate(VariablesRecord info);
}
