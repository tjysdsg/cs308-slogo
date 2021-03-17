package slogo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.events.CommandsRecord;
import slogo.events.TurtleRecord;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTFunctionCall;
import slogo.model.ASTNodes.ASTNode;


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
    TurtleRecord record = infoBundle.getInfo();
    assertEquals(0.0, record.xCoord());
    assertEquals(10.0, record.yCoord());
  }

  @Test
  void testRotate() {
    turtle.rotate(20.0);
    TurtleRecord record = infoBundle.getInfo();
    assertEquals(20.0, record.rotation());
  }

  void testRotateAndMoveHelper(double rotation, double delta, double x, double y) {
    turtle = new Turtle(0, infoBundle);
    turtle.rotate(rotation);
    TurtleRecord record = infoBundle.getInfo();
    assertEquals(rotation, record.rotation());

    turtle.move(delta);
    record = infoBundle.getInfo();
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
    TurtleRecord record = infoBundle.getInfo();
    assertEquals(0.0, record.xCoord());
    assertEquals(0.0, record.yCoord());
  }

  class TestBundle implements InfoBundle {

    TurtleRecord info;

    @Override
    public Turtle getTurtle() {
      return null;
    }

    @Override
    public void notifyTurtleUpdate(TurtleRecord info) {
      this.info = info;
    }

    @Override
    public void notifyCommandUpdate(CommandsRecord info) {
    }

    @Override
    public void notifyVariableUpdate(VariablesRecord info) {
    }

    @Override
    public void notifyEnvironmentClear() {
    }

    @Override
    public Map<String, ASTNode> getVariableTable() {
      return null;
    }

    @Override
    public Map<String, ASTFunctionCall> getCommandTable() {
      return null;
    }

    @Override
    public InfoBundle clone() {
      return null;
    }

    public TurtleRecord getInfo() {
      return info;
    }
  }

  ;
}
