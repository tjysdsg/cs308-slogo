package slogo.model;

import java.util.ArrayList;
import java.util.List;
import slogo.model.ASTNodes.ASTMakeVariable;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.ASTNodes.ASTNumberLiteral;
import slogo.model.ASTNodes.ASTVariable;
import slogo.model.notifiers.EnvironmentNotifier;
import slogo.model.notifiers.ModelTracker;
import slogo.model.notifiers.Delegate;
import slogo.model.notifiers.TurtleNotifier;
import slogo.model.parser.Parser;
import slogo.model.parser.ProgramParser;

public class Environment implements TrackableEnvironment {

  private List<Turtle> turtles = new ArrayList<>();
  private List<Integer> currTurtles = new ArrayList<>();
  private Delegate delegate = new Delegate();
  private EnvironmentNotifier envNotifier = delegate;
  private TurtleNotifier turtleNotifier = delegate;
  private ExecutionScope executionScope =
      new ExecutionScope(turtles, currTurtles, envNotifier, turtleNotifier);
  private Parser myParser =
      new ProgramParser(DEFAULT_LANG, executionScope);

  private static final String DEFAULT_LANG = "English";

  public Environment() {
    turtles.add(new Turtle(0, delegate));
    currTurtles.add(0);

    delegate.onRequestVarUpdate(variable -> {
      ASTNode variableSetter = new ASTMakeVariable();
      variableSetter.addChild(new ASTVariable(variable.name()));
      variableSetter.addChild(new ASTNumberLiteral(Double.parseDouble(variable.value())));
      variableSetter.evaluate(executionScope);
    });
  }

  public void runCommand(String command) {
    ASTNode commandTree = myParser.parseCommand(command);
    commandTree.evaluate(executionScope);
  }

  public ModelTracker getTracker() {
    return delegate;
  }

  public void setLanguage(String language) {
    myParser.changeLanguage(language);
  }

  public void addTurtle() {
    Turtle turtle = new Turtle(turtles.size(), delegate);
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
