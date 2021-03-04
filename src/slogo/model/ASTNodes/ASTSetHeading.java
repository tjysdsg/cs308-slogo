package slogo.model.ASTNodes;

import slogo.model.InfoBundle;
import slogo.model.Turtle;

public class ASTSetHeading extends ASTCommand {

  private static final int NUM_PARAMS = 1;
  private static final String NAME = "setheading";

  public ASTSetHeading() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    double deg = children.get(0).evaluate(info);
    Turtle turtle = info.getTurtle();
    double rotation = turtle.getRotation();
    turtle.setRotation(deg);
    return deg - rotation;
  }
}
