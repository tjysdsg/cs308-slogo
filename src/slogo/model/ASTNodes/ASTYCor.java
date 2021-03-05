package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTYCor extends ASTCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "ycor";

  public ASTYCor() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    return info.getTurtle().getY();
  }
}
