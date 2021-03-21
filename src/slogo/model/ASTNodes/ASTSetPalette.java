package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTSetPalette extends ASTCommand {

  private static final int NUM_PARAMS = 4;
  private static final String NAME = "SetPalette";

  public ASTSetPalette() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    double idx = getChildAt(0).evaluate(info);
    double r = getChildAt(1).evaluate(info);
    double g = getChildAt(2).evaluate(info);
    double b = getChildAt(3).evaluate(info);

    info.setPalette((int) idx, r, g, b);
    return idx;
  }
}
