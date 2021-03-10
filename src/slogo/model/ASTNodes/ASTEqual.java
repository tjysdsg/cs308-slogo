package slogo.model.ASTNodes;

public class ASTEqual extends ASTBinaryOperator {

  private static final String NAME = "Equal";

  public ASTEqual() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return ret1 == ret2 ? 1.0 : 0.0;
  }
}
