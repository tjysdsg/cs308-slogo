package slogo.model.ASTNodes;

public class ASTPow extends ASTBinaryOperator {

  private static final String NAME = "pow";

  public ASTPow() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return Math.pow(ret1, ret2);
  }
}
