package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.events.CommandsRecord;
import slogo.events.DisplayCommand;
import slogo.events.TurtleRecord;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTFunctionCall;
import slogo.model.ASTNodes.ASTMakeUserInstruction;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.ASTNodes.ASTNumberLiteral;

public class TestBundle implements InfoBundle {

  private Map<String, ASTNumberLiteral> variableTable;
  private Map<String, ASTFunctionCall> commandTable;
  private TurtleRecord info;
  private Turtle turtle;
  private List<Turtle> turtles;
  private List<Integer> currTurtles;
  private int mainTurtleIdx = 0;
  private boolean environmentCleared = false;
  boolean isOuterScope = false;

  public TestBundle() {
    reset();
  }

  public TestBundle(HashMap<String, ASTNumberLiteral> variableTable, HashMap<String, ASTFunctionCall> commandTable) {
    this.variableTable = variableTable;
    this.commandTable = commandTable;
  }

  public void reset() {
    variableTable = new HashMap<>();
    commandTable = new HashMap<>();
    info = new TurtleRecord(0, 0, 0, 0, true, true);
    turtle = new Turtle(0, this);
  }

  @Override
  public List<Turtle> getActiveTurtles() {
    ArrayList<Turtle> ret = new ArrayList<>();
    ret.add(turtle);
    return ret;
  }

  @Override
  public Turtle getMainTurtle() {
    return turtles.get(mainTurtleIdx);
  }

  public void setCurrTurtle(int currTurtle) {
    setMainTurtle(currTurtle);
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
  }

  @Override
  public void setMainTurtle(int idx) {
    mainTurtleIdx = idx;
  }

  @Override
  public void notifyTurtleUpdate(TurtleRecord info) {
    this.info = info;
  }

  @Override
  public void notifyCommandUpdate(CommandsRecord info) {
  }

  @Override
  public void notifyVariableUpdate(VariablesRecord info) {
  }

  @Override
  public void notifyEnvironmentClear() {
    environmentCleared = true;
  }

  @Override
  public ASTNumberLiteral getVariable(String name) {
    return variableTable.get(name);
  }

  @Override
  public boolean setVariable(String name, ASTNumberLiteral value) {
    boolean ret = !variableTable.containsKey(name);
    variableTable.put(name, value);
    return ret;
  }
//
//  public Map<String, ASTNumberLiteral> getVariableTable() {
//    return variableTable;
//  }

//  @Override
//  public Map<String, ASTFunctionCall> getCommandTable() {
//    return commandTable;
//  }

  @Override
  public ASTFunctionCall getCommand(String name) {
    return commandTable.get(name);
  }

  @Override
  public boolean setCommand(String name, ASTFunctionCall command) {
    boolean ret = !commandTable.containsKey(name);
    commandTable.put(name, command);
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
  public InfoBundle clone() {
    TestBundle instance = new TestBundle(
        new HashMap<>(variableTable),
        new HashMap<>(commandTable));
    instance.isOuterScope = false;
    return instance;
  }

  public TurtleRecord getInfo() {
    return info;
  }

  public boolean getEnvironmentCleared() {
    return environmentCleared;
  }
}
