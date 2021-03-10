package slogo.model.ASTNodes;

public class ASTPower extends ASTBinaryOperator {

  private static final String NAME = "power";

  public ASTPower() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return Math.pow(ret1, ret2);
  }
}
