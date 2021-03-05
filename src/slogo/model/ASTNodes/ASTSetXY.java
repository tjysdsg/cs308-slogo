package slogo.model.ASTNodes;

import slogo.model.InfoBundle;
import slogo.model.Turtle;
import slogo.model.Vec2D;

public class ASTSetXY extends ASTCommand {

  private static final int NUM_PARAMS = 2;
  private static final String NAME = "setposition";

  public ASTSetXY() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    double x = children.get(0).evaluate(info);
    double y = children.get(1).evaluate(info);

    Turtle turtle = info.getTurtle();
    turtle.setPosition(x, y);

    Vec2D src = new Vec2D(turtle.getX(), turtle.getY());
    return (new Vec2D(x, y)).minus(src).magnitude();
  }
}
