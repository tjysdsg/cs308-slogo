package slogo.model;

import java.util.List;
import java.util.Map;
import slogo.events.TurtleRecord;
import slogo.model.ASTNodes.ASTFunctionCall;
import slogo.model.ASTNodes.ASTNumberLiteral;
import slogo.model.notifiers.Delegate;

public class TestBundle extends ExecutionScope {

  private TurtleRecord turtleRecord;
  private boolean environmentCleared = false;

  public TestBundle(
      List<Turtle> turtles, List<Integer> currTurtles,
      Map<String, ASTNumberLiteral> variableTable,
      Map<String, ASTFunctionCall> commandTable,
     Delegate delegate) {

    super(turtles, currTurtles,
        variableTable, commandTable,
        delegate, info -> {});

    delegate.setOnTurtleUpdate(record -> turtleRecord = record);

    delegate.setOnClear(() -> environmentCleared = true);
  }

  public TestBundle() {
    super(null, null, new Delegate(), new Delegate());
  }

  @Override
  public int getTotalNumTurtles() {
    return getAllTurtles().size();
  }

  public TurtleRecord getTurtleRecord() {
    return turtleRecord;
  }

  public boolean getEnvironmentCleared() {
    return environmentCleared;
  }
}
