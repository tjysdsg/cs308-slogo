package slogo.model.ASTNodes;

import slogo.model.InfoBundle;
import slogo.model.Turtle;

public class ASTLeft extends ASTCommand {

  private static final int NUM_PARAMS = 1;
  private static final String NAME = "left";

  public ASTLeft() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    double deg = getChildAt(0).evaluate(info);
    Turtle turtle = info.getTurtle();
    turtle.rotate(-deg);
    return deg;
  }
}
