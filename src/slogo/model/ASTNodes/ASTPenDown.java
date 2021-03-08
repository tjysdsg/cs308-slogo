package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTPenDown extends ASTCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "PenDown";

  public ASTPenDown() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    info.getTurtle().setPenDown(true);
    return 1;
  }
}
