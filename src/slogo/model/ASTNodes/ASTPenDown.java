package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTPenDown extends ASTCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "pendown";

  public ASTPenDown() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    // TODO: make the pen down
    return 1;
  }
}
