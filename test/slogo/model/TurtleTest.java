package slogo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.events.TurtleRecord;


public class TurtleTest {

  private TestBundle infoBundle;
  private Turtle turtle;

  @BeforeEach
  void setUp() {
    infoBundle = new TestBundle();
    turtle = new Turtle(0, infoBundle);
  }

  @Test
  void testMove() {
    turtle.move(10.0);
    TurtleRecord record = infoBundle.getTurtleRecord();
    assertEquals(0.0, record.xCoord());
    assertEquals(10.0, record.yCoord());
  }

  @Test
  void testRotate() {
    turtle.rotate(20.0);
    TurtleRecord record = infoBundle.getTurtleRecord();
    assertEquals(20.0, record.rotation());
  }

  void testRotateAndMoveHelper(double rotation, double delta, double x, double y) {
    turtle = new Turtle(0, infoBundle);
    turtle.rotate(rotation);
    TurtleRecord record = infoBundle.getTurtleRecord();
    assertEquals(rotation, record.rotation());

    turtle.move(delta);
    record = infoBundle.getTurtleRecord();
    assertEquals(x, record.xCoord(), 1E-5);
    assertEquals(y, record.yCoord(), 1E-5);
  }

  @Test
  void testRotateAndMove() {
    testRotateAndMoveHelper(45.0, 100.0, 100.0 / Math.sqrt(2.0), 100.0 / Math.sqrt(2.0));
    testRotateAndMoveHelper(120.0, 100.0, 50 * Math.sqrt(3.0), -50.0);
    testRotateAndMoveHelper(-120.0, 100.0, -50 * Math.sqrt(3.0), -50.0);
    testRotateAndMoveHelper(-30.0, 100.0, -50.0, 50 * Math.sqrt(3.0));
  }

  @Test
  void testMoveAndMove() {
    turtle.move(10.0);
    turtle.move(-10.0);
    TurtleRecord record = infoBundle.getTurtleRecord();
    assertEquals(0.0, record.xCoord());
    assertEquals(0.0, record.yCoord());
  }
}
