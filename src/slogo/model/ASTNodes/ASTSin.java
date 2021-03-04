package slogo.model.ASTNodes;

import java.util.Random;

public class ASTSin extends ASTUnaryOperator {

  private static final String NAME = "sin";

  public ASTSin() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1) {
    return Math.sin(ret1);
  }
}
