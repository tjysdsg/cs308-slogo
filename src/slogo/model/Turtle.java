package slogo.model;

import slogo.events.TurtleRecord;

/**
 * Contains the information of the turtle, such as position and orientation.
 * <p>
 * NOTE: when the following functions are called, they should notifies the environment
 * <p>
 * NOTE: the x-coordinate used is pointing RIGHT, and the y-coordinate is pointing UP
 */
public class Turtle {

  private int id;
  private double x = 0;
  private double y = 0;
  private boolean visible = true;
  private double rotation = 0;
  private boolean penDown = true;
  private InfoBundle env;

  public int getId() {
    return id;
  }

  public Turtle(int id, InfoBundle infoBundle) {
    env = infoBundle;
    this.id = id;
  }

  private void sendUpdate() {
    TurtleRecord record = new TurtleRecord(id, x, y, rotation, visible, penDown);
    env.notifyTurtleUpdate(record);
  }

  /**
   * Rotate the turtle.
   *
   * @param rotation Rotation in degrees. Rotates clockwise if positive, otherwise
   *                 counter-clockwise
   */
  public void rotate(double rotation) {
    this.rotation = clampDegree(this.rotation + rotation);
    sendUpdate();
  }

  /**
   * Clamp the degree to [-180, 180], with negative value being on the left, positive value on the
   * right
   */
  private double clampDegree(double deg) {
    // first clamp to 0-360
    while (deg < 0) {
      deg += 360;
    }
    while (deg > 360) {
      deg -= 360;
    }

    // then clamp to [-180, 180]
    if (deg > 180) {
      deg -= 360;
    }

    return deg;
  }

  /**
   * Move the turtle towards its the current orientation.
   *
   * @param delta Number of pixels to move.
   */
  public void move(double delta) {
    double rad = Math.toRadians(rotation);
    double deltaX = delta * Math.sin(rad);
    double deltaY = delta * Math.cos(rad);

    this.x += deltaX;
    this.y += deltaY;
    sendUpdate();
  }

  /**
   * Set absolute rotation.
   */
  public void setRotation(double rotation) {
    this.rotation = clampDegree(rotation);
    sendUpdate();
  }

  /**
   * Set absolute position.
   */
  public void setPosition(double x, double y) {
    this.x = x;
    this.y = y;
    sendUpdate();
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
    sendUpdate();
  }

  public void setPenDown(boolean penDown) {
    this.penDown = penDown;
    sendUpdate();
  }

  public boolean isPenDown() {
    return this.penDown;
  }

  public void update(TurtleRecord record) {
    setPosition(record.xCoord(), record.yCoord());
    setRotation(record.rotation());
  }
}
