package slogo.model.ASTNodes;

import slogo.model.InfoBundle;
import slogo.model.Turtle;
import slogo.model.Vec2D;

public class ASTSetPosition extends ASTCommand {

  private static final int NUM_PARAMS = 2;
  private static final String NAME = "SetPosition";

  public ASTSetPosition() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    double x = getChildAt(0).evaluate(info);
    double y = getChildAt(1).evaluate(info);

    Turtle turtle = info.getTurtle();
    Vec2D src = new Vec2D(turtle.getX(), turtle.getY());

    turtle.setPosition(x, y);

    return (new Vec2D(x, y)).minus(src).magnitude();
  }
}
