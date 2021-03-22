package slogo.model.ASTNodes;

public class ASTProduct extends ASTReducibleBinaryOperator {

  private static final String NAME = "Product";

  public ASTProduct() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return ret1 * ret2;
  }
}
