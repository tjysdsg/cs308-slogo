package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTHeading extends ASTCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "heading";

  public ASTHeading() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    return info.getTurtle().getRotation();
  }
}
