package slogo.view;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;

/**
 * A controller to manipulate
 * the current view.
 */
public interface ViewController {

  /**
   * Sets the background color of the view.
   * @param colorPicker - The color to set the background to.
   */
  void setBackground(ColorPicker colorPicker);

  /**
   * Sets the language used within the environment.
   * @param choiceBox - The language locale to use.
   */
  void setLanguage(ChoiceBox choiceBox);


  /**
   * Set the turtle pen color for the view.
   * @param colorPicker - The color to set pen to.
   */
  void setPenColor(ColorPicker colorPicker);

  /**
   * Set the turtle logo color for the view.
   * @param turtleLogo- The turtlelogo displayed in the view.
   */
  void setTurtleLogo(String turtleLogo);

  void sendAlert(String title, String message);

  /**
   * send user command to model to parse.
   *
   */
   void sendUserText();

}
