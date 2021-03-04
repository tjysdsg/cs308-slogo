package slogo.model.ASTNodes;

public class ASTDiff extends ASTBinaryOperator {

  private static final String NAME = "difference";

  public ASTDiff() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return ret1 - ret2;
  }
}
