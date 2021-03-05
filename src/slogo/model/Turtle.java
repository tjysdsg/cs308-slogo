package slogo.model;

/**
 * Contains the information of the turtle, such as position and orientation.
 * <p>
 * NOTE: when the following functions are called, they should notifies the environment
 * <p>
 * NOTE: the x-coordinate used is pointing RIGHT, and the y-coordinate is pointing UP
 */
public class Turtle {

  private double x = 0;
  private double y = 0;
  private boolean visible = true;
  private double rotation = 0;
  private InfoBundle env;

  public Turtle(InfoBundle infoBundle) {
    env = infoBundle;
  }

  /**
   * Set coordinate of the turtle.
   */
  public void setXY(double x, double y) {
  }

  /**
   * Rotate the turtle.
   *
   * @param rotation Rotation in degrees. Rotates clockwise if positive, otherwise
   *                 counter-clockwise
   */
  public void rotate(double rotation) {
  }

  /**
   * Move the turtle towards its the current orientation.
   *
   * @param x Number of pixels to move.
   */
  public void move(double x) {
  }

  /**
   * Set absolute rotation.
   */
  public void setRotation(double rotation) {
  }

  /**
   * Set absolute position.
   */
  public void setPosition(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Get current absolute rotation.
   */
  public double getRotation() {
    return this.rotation;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }
}
