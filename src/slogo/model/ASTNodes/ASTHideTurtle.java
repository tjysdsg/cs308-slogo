package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTHideTurtle extends ASTCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "hideturtle";

  public ASTHideTurtle() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    info.getTurtle().setVisible(false);
    return 0;
  }
}
