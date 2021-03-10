package slogo.model.ASTNodes;

public class ASTSum extends ASTBinaryOperator {

  private static final String NAME = "Sum";

  public ASTSum() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return ret1 + ret2;
  }
}
