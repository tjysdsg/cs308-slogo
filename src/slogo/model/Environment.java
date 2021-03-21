package slogo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import slogo.events.CommandsRecord;
import slogo.events.DisplayVariable;
import slogo.events.EnvironmentRecord;
import slogo.events.TurtleRecord;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTMakeVariable;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.ASTNodes.ASTNumberLiteral;
import slogo.model.ASTNodes.ASTVariable;
import slogo.model.parser.Parser;
import slogo.model.parser.ProgramParser;

public class Environment implements TrackableEnvironment {

  private List<Turtle> turtles = new ArrayList<>();
  private List<Integer> currTurtles = new ArrayList<>();
  private ExecutionScope executionScope =
      new ExecutionScope(turtles, currTurtles);
  private Parser myParser =
      new ProgramParser(DEFAULT_LANG, executionScope);

  private static final String DEFAULT_LANG = "English";

  public Environment() {
    turtles.add(new Turtle(0, executionScope));
    currTurtles.add(0);
  }

  public void runCommand(String command) {
    ASTNode commandTree = myParser.parseCommand(command);
    commandTree.evaluate(executionScope);
  }

  @Override
  public void setOnEnvironmentUpdate(Consumer<EnvironmentRecord> callback) {
     executionScope.setOnEnvironmentUpdate(callback);
  }

  public void setOnClear(Runnable callback) {
    executionScope.setOnClear(callback);
  }

  public void setOnTurtleUpdate(Consumer<TurtleRecord> callback) {
    executionScope.setOnTurtleUpdate(callback);
  }

  public void setOnVariableUpdate(Consumer<VariablesRecord> callback) {
    executionScope.setOnVariableUpdate(callback);
  }

  public void setOnCommandUpdate(Consumer<CommandsRecord> callback) {
    executionScope.setOnCommandUpdate(callback);
  }

  @Override
  public void requestVariablesUpdate(DisplayVariable variable) {
    ASTNode variableSetter = new ASTMakeVariable();
    variableSetter.addChild(new ASTVariable(variable.name()));
    variableSetter.addChild(new ASTNumberLiteral(Double.parseDouble(variable.value())));
    variableSetter.evaluate(executionScope);
  }

  @Override
  public void requestTurtleUpdate(TurtleRecord record) {
    Turtle toUpdate = turtles.get(record.id());
    toUpdate.update(record);
  }

  @Override
  public void requestEnvironmentUpdate(EnvironmentRecord record) {
    executionScope.updateEnvironment(record);
  }

  public void setLanguage(String language) {
    myParser.changeLanguage(language);
  }

  public void addTurtle() {
    Turtle turtle = new Turtle(turtles.size(), executionScope);
    currTurtles.add(turtles.size());
    turtles.add(turtle);
  }

  public void setCurrTurtle(int currTurtle) {
    executionScope.setMainTurtle(currTurtle);
  }

  public void setCurrTurtle(List<Integer> currTurtles) {
    executionScope.setCurrTurtle(currTurtles);
  }
}
