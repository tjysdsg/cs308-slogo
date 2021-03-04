package slogo.model.ASTNodes;

import slogo.model.InfoBundle;
import slogo.model.Turtle;
import slogo.model.Vec2D;

public class ASTTowards extends ASTCommand {

  private static final int NUM_PARAMS = 2;
  private static final String NAME = "towards";

  public ASTTowards() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    double x = children.get(0).evaluate(info);
    double y = children.get(1).evaluate(info);

    Turtle turtle = info.getTurtle();

    Vec2D src = new Vec2D(turtle.getX(), turtle.getY());
    Vec2D dest = new Vec2D(x, y);
    double deg = Math.acos(dest.cosAngle(src)); // acos returns degree, not radian
    if (deg < 0) {
      deg = 180.0 - deg;
    }
    int clockwiseness = dest.clockwiseness(src);

    deg *= clockwiseness;
    turtle.rotate(deg);
    return deg;
  }
}
