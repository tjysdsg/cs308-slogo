package slogo.model.ASTNodes;

import slogo.model.InfoBundle;
import slogo.model.Turtle;

public class ASTSetTowards extends ASTCommand {

  private static final int NUM_PARAMS = 2;
  private static final String NAME = "SetTowards";

  public ASTSetTowards() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    double x = getChildAt(0).evaluate(info);
    double y = getChildAt(1).evaluate(info);

    Turtle turtle = info.getTurtle();

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
