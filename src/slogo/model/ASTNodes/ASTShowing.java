package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTShowing extends ASTCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "isshowing";

  public ASTShowing() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    return info.getTurtle().isVisible() ? 1.0 : 0.0;
  }
}
