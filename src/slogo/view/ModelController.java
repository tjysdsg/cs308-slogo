package slogo.view;

import java.util.ResourceBundle;
import slogo.model.TrackableEnvironment;
import slogo.exceptions.ModelException;
/**
 * @author Joshua Pettima
 * @author Martha Aboagye
 * This class contains methods needed for
 * the view to talk to the  our model.
 * The class assumes/depends on  a trackable environment object.
 * Trackable environment is the backend external interface.
 * In order for the view
 * to send user request actions to the backened to execute,
 * it calls the methods available from the trackable environment
 * object.  An example of how to use this class is below:
 * ModelController model = new ModelController();
 * model.sendCommand("Forward 50");
 *
 */

public class ModelController {
  private static final String RESOURCES_PACKAGE = "resources.commands.";
  private ViewController vc;
  private TrackableEnvironment env;
  private ResourceBundle exceptions;
  private ResourceBundle translation;

  /**
   * This is the constryctor for the model controller class
   * As of now, it doesn't meed to be initialized with any
   * specific obkects. It is an empty method
   */
  public ModelController() {
    exceptions = ResourceBundle.getBundle(RESOURCES_PACKAGE + "exceptions.English");
  }

  public void setController(ViewController vc) {
    this.vc = vc;
  }

  /**
   * This method initializes a trackable environment object which
   * allows the view to talk to the model/pass user actions
   * to the model to interpret.
   * @param env is a trackable environment object.
   */
  public void setModel(TrackableEnvironment env) {
    this.env = env;
  }

  /**
   * This method sends the text the user
   * enters through the view to the model
   * to parse and execute. If this method is called
   * before a trackable environment is initialized,
   * it throws an error.
   *
   * @param command
   * @throws Exception
   * @return Whether the command was successfully executed.
   */
  public boolean sendCommand(String command) {
    if (this.env != null) {
      try {
        env.runCommand(command);
        return true;
      } catch(ModelException e) {
        System.out.println("Error: " + e.toString());
        String message = e.buildException(exceptions.getString(e.getMessage()));
        vc.sendAlert("Error", message);
        return false;
      }
    } else {
      return false;
    }
  }

  /**
   * Sets the language to translate exceptions
   * and commands to and from.
   * @param language - The language to use
   */
  public void setLanguage(String language) {
    env.setLanguage(language);
  }

  public void addTurtle() {
    env.addTurtle();
  }

  public void setCurrTurtle(int turtle) {
    env.setCurrTurtle(turtle);
  }
}
