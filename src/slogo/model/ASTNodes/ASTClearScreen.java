package slogo.model.ASTNodes;

import slogo.model.InfoBundle;
import slogo.model.Turtle;
import slogo.model.Vec2D;

public class ASTClearScreen extends ASTCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "ClearScreen";

  public ASTClearScreen() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    // TODO: erase trails

    Turtle turtle = info.getTurtle();
    Vec2D src = new Vec2D(turtle.getX(), turtle.getY());
    turtle.setPosition(0, 0);
    info.notifyEnvironmentClear();
    return (new Vec2D(0, 0)).minus(src).magnitude();
  }
}
