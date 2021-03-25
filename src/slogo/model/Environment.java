package slogo.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

/**
 * This class is the container for the model. It implements an interface that allows the view to
 * communicate with the model and the model to respond to the view.
 * <p>
 * This class assumes that parsers and InfoBundles have been defined.
 * <p>
 * This class depends on the parser package, notifiers package, ASTNode package and Java's util
 * package.
 * <p>
 * This class would be created using its constructor, then a tracker can be created to set listeners
 * and calling listeners from the model. The parent class can then call runCommand to run a command
 * string.
 *
 * @author Oliver Rodas, Jiyang Tang, Joshua Petitma
 */
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

  /**
   * Instantiates a new Environment.
   */
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
  public void save(File saveLocation) {
    try {
      saveLocation.createNewFile();
      FileOutputStream fileOut = new FileOutputStream(saveLocation);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(this);
      out.close();
      fileOut.close();
    } catch (FileNotFoundException e) {
      System.out.printf("DEBUG: Saving Failed: The %s file was not found\n", saveLocation.getName());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void load(File loadLocation) {
    try {
      FileInputStream fileIn = new FileInputStream(loadLocation);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      Environment newEnv = (Environment) in.readObject();
      in.close();
      fileIn.close();
      mergeWith(newEnv);
    } catch (FileNotFoundException e) {
      System.out.printf("DEBUG: Loading Failed: The %s file was not found\n", loadLocation.getName());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.printf("DEBUG: Loading Failed: The %s file was not read correctly.\n", loadLocation.getName());
    }
  }

  private void mergeWith(Environment newEnv) {
    for (var entry : newEnv.executionScope.getCommands()) {
      executionScope.setCommand(entry.getKey(), entry.getValue());
    }

    for (var entry : newEnv.executionScope.getVariables()) {
      executionScope.setVariable(entry.getKey(), entry.getValue());
    }

    for (Turtle currTurtle : newEnv.turtles) {
      turtles.add(currTurtle.clone(turtles.size(), turtleNotifier));
    }
  }

  public void setLanguage(String language) {
    myParser.changeLanguage(language);
  }

  public void addTurtle() {
    Turtle turtle = new Turtle(turtles.size(), delegate);
    currTurtles.add(turtles.size());
    turtles.add(turtle);
    executionScope.notifyEnvironment();
  }

  public void setCurrTurtle(int currTurtle) {
    executionScope.setMainTurtle(currTurtle);
  }

  public void setCurrTurtle(List<Integer> currTurtles) {
    // don't notify view since it was view who called this
    executionScope.setCurrTurtleNoNotify(currTurtles);
  }

}
