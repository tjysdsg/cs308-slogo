package slogo.model.ASTNodes;

public class ASTOr extends ASTBinaryOperator {

  private static final String NAME = "or";

  public ASTOr() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return ((ret1 != 0) || (ret2 != 0)) ? 1.0 : 0.0;
  }
}
