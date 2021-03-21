package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import slogo.events.CommandsRecord;
import slogo.events.DisplayCommand;
import slogo.events.TurtleRecord;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTFunctionCall;
import slogo.model.ASTNodes.ASTNumberLiteral;

public class TestBundle extends ExecutionScope {

  private TurtleRecord turtleRecord;
  private boolean environmentCleared = false;

  public TestBundle(
      List<Turtle> turtles, List<Integer> currTurtles,
      Map<String, ASTNumberLiteral> variableTable,
      Map<String, ASTFunctionCall> commandTable,
     TransmissionLine transmissionLine) {

    super(turtles, currTurtles,
        variableTable, commandTable,
        transmissionLine, info -> {});

    transmissionLine.setOnTurtleUpdate(record -> turtleRecord = record);

    transmissionLine.setOnClear(() -> environmentCleared = true);
  }

  public TestBundle() {
    super(null, null, new TransmissionLine(), new TransmissionLine());
  }

  //reset();
//    this.variableTable = variableTable;
//    this.commandTable = commandTable;
//  }

//  public void reset() {
//
//    variableTable = new HashMap<>();
//    commandTable = new HashMap<>();
//    turtleRecord = new TurtleRecord(0, 0, 0, 0, true, true, 5);
//    turtle = new Turtle(0, null);
//  }

//  @Override
//  public List<Turtle> getActiveTurtles() {
//    ArrayList<Turtle> ret = new ArrayList<>();
//    ret.add(turtle);
//    return ret;
//  }
//
//  @Override
//  public List<Turtle> getAllTurtles() {
//    ArrayList<Turtle> ret = new ArrayList<>();
//    ret.add(turtle);
//    return ret;
//  }

//  @Override
//  public Turtle getMainTurtle() {
//    return turtle;
//  }
//
//  public void setCurrTurtle(int currTurtle) {
//    // TODO: implement this when testing TELL and ASK
//  }
//
//  @Override
//  public void setCurrTurtle(List<Integer> newTurtles) {
//    // TODO: implement this when testing TELL and ASK
//  }
//
//  @Override
//  public void setMainTurtle(int idx) {
//    // TODO: implement this when testing TELL and ASK
//  }

  @Override
  public int getTotalNumTurtles() {
    return getAllTurtles().size();
  }

//  @Override
//  public ASTNumberLiteral getVariable(String name) {
//    return variableTable.get(name);
//  }
//
//  @Override
//  public boolean setVariable(String name, ASTNumberLiteral value) {
//    boolean ret = !variableTable.containsKey(name);
//    variableTable.put(name, value);
//    return ret;
//  }

//  public Map<String, ASTNumberLiteral> getVariableTable() {
//    return variableTable;
//  }

//  @Override
//  public Map<String, ASTFunctionCall> getCommandTable() {
//    return commandTable;
//  }

//  @Override
//  public ASTFunctionCall getCommand(String name) {
//    return commandTable.get(name);
//  }
//
//  @Override
//  public boolean setCommand(String name, ASTFunctionCall command) {
//    boolean ret = !commandTable.containsKey(name);
//    commandTable.put(name, command);
//    if (isOuterScope) {
//      ArrayList<DisplayCommand> commands = new ArrayList<>();
//      for (var entry : commandTable.entrySet()) {
//        commands.add(new DisplayCommand(entry.getKey(), entry.getValue().toString()));
//      }
//      // TODO: Comeback to this
////      notifier.notifyCommandUpdate(new CommandsRecord(commands));
//    }
//    return ret;
//  }

//  @Override
//  public InfoBundle clone() {
//    TestBundle instance = new TestBundle(
//        new HashMap<>(variableTable),
//        new HashMap<>(commandTable));
//    instance.isOuterScope = false;
//    instance.turtle = this.turtle;
//    instance.turtleRecord = this.turtleRecord;
//    return instance;
//  }

  public TurtleRecord getTurtleRecord() {
    return turtleRecord;
  }

  public boolean getEnvironmentCleared() {
    return environmentCleared;
  }
}
