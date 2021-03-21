package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;

public class ASTSetPalette extends ASTCommand {

  private static final int NUM_PARAMS = 4;
  private static final String NAME = "SetPalette";

  public ASTSetPalette() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info, List<ASTNode> params) {
    double idx = params.get(0).evaluate(info);
    double r = params.get(1).evaluate(info);
    double g = params.get(2).evaluate(info);
    double b = params.get(3).evaluate(info);

    info.setPalette((int) idx, r, g, b);
    return idx;
  }
}
