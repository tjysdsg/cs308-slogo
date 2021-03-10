package slogo.model.ASTNodes;

public class ASTQuotient extends ASTBinaryOperator {

  private static final String NAME = "Quotient";

  public ASTQuotient() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return ret1 / ret2;
  }
}
