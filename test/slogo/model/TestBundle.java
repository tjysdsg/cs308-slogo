package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.events.CommandsRecord;
import slogo.events.TurtleRecord;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTFunctionCall;
import slogo.model.ASTNodes.ASTNode;

public class TestBundle implements InfoBundle {

  private Map<String, ASTNode> variableTable;
  private Map<String, ASTFunctionCall> commandTable;
  private TurtleRecord info;
  private Turtle turtle;
  private boolean environmentCleared = false;

  public TestBundle() {
    reset();
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
  public Map<String, ASTNode> getVariableTable() {
    return variableTable;
  }

  @Override
  public Map<String, ASTFunctionCall> getCommandTable() {
    return commandTable;
  }

  @Override
  public InfoBundle clone() {
    return null;
  }

  public TurtleRecord getInfo() {
    return info;
  }

  public boolean getEnvironmentCleared() {
    return environmentCleared;
  }
}
