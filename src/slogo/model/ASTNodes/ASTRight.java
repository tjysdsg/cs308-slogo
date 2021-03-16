package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;
import slogo.model.Turtle;

public class ASTRight extends ASTTurtleCommand {

  private static final int NUM_PARAMS = 1;
  private static final String NAME = "Right";

  public ASTRight() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double evaluateForTurtle(Turtle turtle, List<Double> parameters, InfoBundle info) {
    double deg = parameters.get(0);
    turtle.rotate(deg);
    return deg;
  }
}
