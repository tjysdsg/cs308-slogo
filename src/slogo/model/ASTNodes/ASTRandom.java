package slogo.model.ASTNodes;

import java.util.Random;

public class ASTRandom extends ASTUnaryOperator {

  private static final String NAME = "Random";
  private final Random rand;

  public ASTRandom() {
    super(NAME);
    rand = new Random();
  }

  @Override
  protected double calculate(double ret1) {
    return rand.nextInt((int) Math.ceil(ret1));
  }
}
