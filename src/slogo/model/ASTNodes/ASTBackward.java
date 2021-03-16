package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;
import slogo.model.Turtle;

public class ASTBackward extends ASTTurtleCommand {

  private static final int NUM_PARAMS = 1;
  private static final String NAME = "Backward";

  public ASTBackward() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double evaluateForTurtle(Turtle turtle, List<Double> parameters,
      InfoBundle info) {
    double delta = -parameters.get(0);
    turtle.move(delta);
    return delta;
  }
}
