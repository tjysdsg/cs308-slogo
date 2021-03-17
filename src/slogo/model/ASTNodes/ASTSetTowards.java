package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;
import slogo.model.Turtle;

public class ASTSetTowards extends ASTTurtleCommand {

  private static final int NUM_PARAMS = 2;
  private static final String NAME = "SetTowards";

  public ASTSetTowards() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double evaluateForTurtle(Turtle turtle, List<Double> parameters, InfoBundle info) {
    double x = parameters.get(0);
    double y = parameters.get(1);

    double tan = x / y;
    double deg = Math.toDegrees(Math.atan(tan));

    if (tan > 0 && x < 0) {
      deg -= 180;
    }

    if (tan < 0 && x > 0) {
      deg += 180;
    }

    double delta = deg - turtle.getRotation();
    turtle.rotate(delta);
    return delta;
  }
}
