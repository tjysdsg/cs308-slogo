package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;
import slogo.model.Turtle;
import slogo.model.Vec2D;

public class ASTSetPosition extends ASTTurtleCommand {

  private static final int NUM_PARAMS = 2;
  private static final String NAME = "SetPosition";

  public ASTSetPosition() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double evaluateForTurtle(Turtle turtle, List<Double> parameters,
      InfoBundle info) {
    double x = parameters.get(0);
    double y = parameters.get(1);
    Vec2D src = new Vec2D(turtle.getX(), turtle.getY());

    turtle.setPosition(x, y);
    return (new Vec2D(x, y)).minus(src).magnitude();
  }
}
