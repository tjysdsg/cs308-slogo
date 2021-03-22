package slogo.model.ASTNodes;

public class ASTDifference extends ASTReducibleBinaryOperator {

  private static final String NAME = "Difference";

  public ASTDifference() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return ret1 - ret2;
  }
}
