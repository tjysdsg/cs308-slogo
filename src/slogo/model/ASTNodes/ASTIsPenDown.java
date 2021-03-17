package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;
import slogo.model.Turtle;

public class ASTIsPenDown extends ASTTurtleCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "IsPenDown";

  public ASTIsPenDown() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double evaluateForTurtle(Turtle turtle, List<Double> parameters, InfoBundle info) {
    return turtle.isPenDown() ? 1.0 : 0.0;
  }
}
