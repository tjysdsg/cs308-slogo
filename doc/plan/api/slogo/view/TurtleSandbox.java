package slogo.view;
import javafx.scene.layout.Pane;
import slogo.records.TurtleRecord;

/**
 * @author marthaaboagye
 * @author Joshua Pettima
 *
 * This class creates the  window
 * where the turtle moves. It extends the Pane class
 * so that the turtle box and the status bar
 * are both displayed when the simulation starts.
 */

public class TurtleSandbox extends Pane {

  /**
   * Constructor for TurtleSandbox. Intializes the pan class.
   */
  public TurtleSandbox() {
    super();
  }

  /**
   * This method updates turtles position after the user
   * command is executed on the backend.
   * @param info
   */

  public void updateTurtle(TurtleRecord info) {
  }
}
