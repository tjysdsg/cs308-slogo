package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTIsPenDown extends ASTCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "IsPenDown";

  public ASTIsPenDown() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    info.getTurtle().isPenDown();
    return 1;
  }
}
