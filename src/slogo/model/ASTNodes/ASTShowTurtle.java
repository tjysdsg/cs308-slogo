package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;
import slogo.model.Turtle;

public class ASTShowTurtle extends ASTTurtleCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "ShowTurtle";

  public ASTShowTurtle() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double evaluateForTurtle(Turtle turtle, List<Double> parameters,
      InfoBundle info) {
    turtle.setVisible(true);
    return 1;
  }
}
