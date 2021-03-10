package slogo.model.ASTNodes;

public class ASTLessThan extends ASTBinaryOperator {

  private static final String NAME = "LessThan";

  public ASTLessThan() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return ret1 < ret2 ? 1.0 : 0.0;
  }
}
