package slogo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.events.TurtleRecord;
import slogo.model.ASTNodes.ASTFunctionCall;
import slogo.model.ASTNodes.ASTNumberLiteral;


public class TurtleTest {
  private Map<String, ASTNumberLiteral> variableTable;
  private Map<String, ASTFunctionCall> commandTable;
  private TestBundle infoBundle;
  private Turtle turtle;
  private List<Turtle> turtles;
  private List<Integer> currTurtles;
  private TransmissionLine transmissionLine = new TransmissionLine();

  @BeforeEach
  void setUp() {
    turtle = new Turtle(0, transmissionLine);
    turtles = new ArrayList<>(List.of(turtle));
    currTurtles = new ArrayList<>(List.of(0));

    variableTable = new HashMap<>();
    commandTable = new HashMap<>();

    infoBundle = new TestBundle(
        turtles,
        currTurtles, variableTable,
        commandTable, transmissionLine);
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
    turtle = new Turtle(0, transmissionLine);
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
