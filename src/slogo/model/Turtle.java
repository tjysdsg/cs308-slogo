package slogo.model;

/**
 * Contains the information of the turtle, such as position and orientation.
 * <p>
 * NOTE: when the following functions are called, they should notifies the environment
 */
public class Turtle {

  private double x;
  private double y;
  private double rotation;
  private Environment env;

  /**
   * Set coordinate of the turtle.
   */
  public void setXY(double x, double y) {}

  /**
   * Rotate the turtle.
   *
   * @param rotation Rotation in degrees. Rotates clockwise if positive, otherwise
   *                 counter-clockwise
   */
  public void rotate(double rotation) {}

  /**
   * Move the turtle towards its the current orientation.
   *
   * @param x Number of pixels to move.
   */
  public void move(double x) {}

}
