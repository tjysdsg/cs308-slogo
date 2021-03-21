package slogo.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.notifiers.EnvironmentNotifier;
import slogo.model.notifiers.ModelTracker;
import slogo.model.notifiers.Delegate;
import slogo.model.notifiers.TurtleNotifier;
import slogo.model.parser.Parser;
import slogo.model.parser.ProgramParser;

public class Environment implements TrackableEnvironment, Serializable {

  private List<Turtle> turtles = new ArrayList<>();
  private List<Integer> currTurtles = new ArrayList<>();

  private transient Delegate delegate = new Delegate();
  private transient TurtleNotifier turtleNotifier = delegate;
  private transient EnvironmentNotifier envNotifier = delegate;

  private ExecutionScope executionScope =
      new ExecutionScope(turtles, currTurtles, envNotifier, turtleNotifier);

  private transient Parser myParser =
      new ProgramParser(DEFAULT_LANG, executionScope);

  private static final String DEFAULT_LANG = "English";

  public Environment() {
    turtles.add(new Turtle(0, delegate));
    currTurtles.add(0);
  }

  public void runCommand(String command) {
    ASTNode commandTree = myParser.parseCommand(command);
    commandTree.evaluate(executionScope);
  }

  public ModelTracker getTracker() {
    return delegate;
  }

  @Override
  public void saveEnv(File saveLocation) {
    try {
      FileOutputStream fileOut = new FileOutputStream(saveLocation);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(this);
      out.close();
      fileOut.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void loadEnv(File loadLocation) {

  }

//  private void writeObject(ObjectOutputStream out) throws IOException {
//    out.defaultWriteObject();
//  }
//
//  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
//
//  }

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
