package slogo.model.ASTNodes;

import slogo.model.InfoBundle;
import slogo.model.Turtle;
import slogo.model.Vec2D;

public class ASTTowards extends ASTCommand {

  private static final int NUM_PARAMS = 2;
  private static final String NAME = "settowards";

  public ASTTowards() {
    super(NAME, NUM_PARAMS);
  }

  // FIXME: clockwiseness
  @Override
  protected double doEvaluate(InfoBundle info) {
    double x = getChildAt(0).evaluate(info);
    double y = getChildAt(1).evaluate(info);

    Turtle turtle = info.getTurtle();

    double rotation = turtle.getRotation();
    double deg = Math.toDegrees(Math.atan(y / x));

    double delta = deg - rotation;
    turtle.rotate(delta);
    return delta;
  }
}
