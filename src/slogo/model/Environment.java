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
  private ExecutionEnvironment executionEnvironment =
      new ExecutionEnvironment(turtles, currTurtles);
  private Parser myParser =
      new ProgramParser(DEFAULT_LANG, executionEnvironment);

  private static final String DEFAULT_LANG = "English";

  public Environment() {
    turtles.add(new Turtle(0, executionEnvironment));
    currTurtles.add(0);
  }

  public void runCommand(String command) {
    ASTNode commandTree = myParser.parseCommand(command);
    commandTree.evaluate(executionEnvironment);
  }

  @Override
  public void setOnEnvironmentUpdate(Consumer<EnvironmentRecord> callback) {
     executionEnvironment.setOnEnvironmentUpdate(callback);
  }

  public void setOnClear(Runnable callback) {
    executionEnvironment.setOnClear(callback);
  }

  public void setOnTurtleUpdate(Consumer<TurtleRecord> callback) {
    executionEnvironment.setOnTurtleUpdate(callback);
  }

  public void setOnVariableUpdate(Consumer<VariablesRecord> callback) {
    executionEnvironment.setOnVariableUpdate(callback);
  }

  public void setOnCommandUpdate(Consumer<CommandsRecord> callback) {
    executionEnvironment.setOnCommandUpdate(callback);
  }

  @Override
  public void requestVariablesUpdate(DisplayVariable variable) {
    ASTNode variableSetter = new ASTMakeVariable();
    variableSetter.addChild(new ASTVariable(variable.name()));
    variableSetter.addChild(new ASTNumberLiteral(Double.parseDouble(variable.value())));
    variableSetter.evaluate(executionEnvironment);
  }

  @Override
  public void requestTurtleUpdate(TurtleRecord record) {
    Turtle toUpdate = turtles.get(record.id());
    toUpdate.update(record);
  }

  @Override
  public void requestEnvironmentUpdate(EnvironmentRecord record) {

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
}
