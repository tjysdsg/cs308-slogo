package slogo.model.ASTNodes;

public class ASTProduct extends ASTBinaryOperator {

  private static final String NAME = "product";

  public ASTProduct() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return ret1 * ret2;
  }
}
