package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;
import slogo.model.Turtle;
import slogo.model.Vec2D;

public class ASTHome extends ASTTurtleCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "Home";

  public ASTHome() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double evaluateForTurtle(Turtle turtle, List<Double> parameters, InfoBundle info) {
    Vec2D src = new Vec2D(turtle.getX(), turtle.getY());
    turtle.setPosition(0, 0);
    return (new Vec2D(0, 0)).minus(src).magnitude();
  }
}
