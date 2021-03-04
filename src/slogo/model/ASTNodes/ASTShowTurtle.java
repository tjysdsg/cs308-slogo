package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTShowTurtle extends ASTCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "showturtle";

  public ASTShowTurtle() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    info.getTurtle().setVisible(true);
    return 1;
  }
}
