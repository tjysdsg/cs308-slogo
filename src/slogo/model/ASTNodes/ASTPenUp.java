package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;
import slogo.model.Turtle;

public class ASTPenUp extends ASTTurtleCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "PenUp";

  public ASTPenUp() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double evaluateForTurtle(Turtle turtle, List<Double> parameters, InfoBundle info) {
    turtle.setPenDown(false);
    return 0;
  }
}
