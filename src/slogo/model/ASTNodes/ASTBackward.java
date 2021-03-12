package slogo.model.ASTNodes;

import slogo.model.InfoBundle;
import slogo.model.Turtle;

public class ASTBackward extends ASTCommand {

  private static final int NUM_PARAMS = 1;
  private static final String NAME = "Backward";

  public ASTBackward() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    double delta = getChildAt(0).evaluate(info);
    Turtle turtle = info.getTurtle();
//    turtle.rotate(180);
    turtle.move(-delta);
    return delta;
  }
}
