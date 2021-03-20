package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTSetPenColor extends ASTCommand {

  private static final int NUM_PARAMS = 1;
  private static final String NAME = "SetPenColor";

  public ASTSetPenColor() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    double arg = getChildAt(0).evaluate(info);
    info.setPenColorIdx((int) arg);
    return arg;
  }
}
