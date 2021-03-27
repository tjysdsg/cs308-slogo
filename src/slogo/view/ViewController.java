package slogo.view;

import java.util.ResourceBundle;
import slogo.model.Color;

/** A controller to manipulate the current view. */
public interface ViewController {

  /**
   * Sets the background color of the view.
   *
   * @param color - The color to set the background to.
   */
  void setBackground(String color);

  void setBackground(Color colorRecord);

  /**
   * Sets the language used within the environment.
   *
   * @param language - The language locale to use.
   */
  void setLanguage(String language);

  /**
   * Set the turtle pen color for the view.
   *
   * @param color - The color to set pen to.
   */
  void setPenColor(String color);

  /**
   * Set turtle pen color but with a color parameter
   * @param penColor
   */
  void setPenColor(Color penColor);

  /**
   * Set the turtle logo color for the view.
   *
   * @param turtleLogo- The turtlelogo displayed in the view.
   */
  void setTurtleLogo(int turtleLogo);

  /**
   * Overloaded method to set the turtle logo for the view.
   * @param turtleLogo
   */

  void setTurtleLogo(String turtleLogo);

  /**
   * Set an alert for an exception message.
   * @param title
   * @param message
   */

  void sendAlert(String title, String message);

  /** send user command to model to parse. */
  void sendCommand(String command);

  /**
   * Add a turtle to the turtle sandbox
   */
  void addTurtle();

  /**
   * Sets clicked on turtle as the active turtle
   * @param id
   */

  void setCurrTurtle(int id);

  /**
   * Allows users to change the value of
   * a variable dynamically.
   * @param name
   * @param newValue
   */

  void changeVariable(String name, double newValue);

  /**
   * Fill command area with a given test.
   * This method is useful for reading in
   * a textfile
   * @param text
   */
  void fillCommandArea(String text);

  /**
   * Set the font Size for the UI
   * @param size
   */
  void setFontSize(int size);


  /**
   * Set the font for the UI
   * @param font
   */
  void setFont(String font);

  /**
   * Save the turtle environment including
   * preferences at users request.
   */
  void saveEnvironment();

  /**
   * Load a previously saved environment.
   */
  void loadEnvironment();

  /**
   * set the resources for the main view class.
   * @return
   */
  ResourceBundle getResources();
}
