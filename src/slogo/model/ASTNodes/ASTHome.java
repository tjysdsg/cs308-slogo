package slogo.model.ASTNodes;

import slogo.model.InfoBundle;
import slogo.model.Turtle;
import slogo.model.Vec2D;

public class ASTHome extends ASTCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "Home";

  public ASTHome() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    Turtle turtle = info.getTurtle();
    Vec2D src = new Vec2D(turtle.getX(), turtle.getY());
    turtle.setPosition(0, 0);
    return (new Vec2D(0, 0)).minus(src).magnitude();
  }
}
