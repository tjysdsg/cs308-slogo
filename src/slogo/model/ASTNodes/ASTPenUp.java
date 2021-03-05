package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTPenUp extends ASTCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "penup";

  public ASTPenUp() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    info.getTurtle().setPenDown(false);
    return 0;
  }
}
