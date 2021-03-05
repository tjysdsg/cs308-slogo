package slogo.view;

/**
 * A controller to manipulate
 * the current view.
 */
public interface ViewController {

  /**
   * Sets the background color of the view.
   * @param color - The color to set the background to.
   */
  void setBackground(String color);

  /**
   * Sets the language used within the environment.
   * @param language - The language locale to use.
   */
  void setLanguage(String language);


  /**
   * Set the turtle pen color for the view.
   * @param color - The color to set pen to.
   */
  void setPenColor(String color);

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
