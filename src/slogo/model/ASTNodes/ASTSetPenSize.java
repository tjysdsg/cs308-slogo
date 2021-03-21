package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTSetPenSize extends ASTCommand {
  private static final int NUM_PARAMS = 1;
  private static final String NAME = "SetPenColor";

  public ASTSetPenSize() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    double val = getChildAt(0).evaluate(info);
    info.setPenSize(val);
    return val;
  }
}
