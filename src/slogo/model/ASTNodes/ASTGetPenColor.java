package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTGetPenColor extends ASTCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "GetPenColor";

  public ASTGetPenColor() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    return info.getPenColorIdx();
  }
}
