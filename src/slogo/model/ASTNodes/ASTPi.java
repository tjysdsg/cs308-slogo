package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTPi extends ASTCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "pi";

  public ASTPi() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    return Math.PI;
  }
}
