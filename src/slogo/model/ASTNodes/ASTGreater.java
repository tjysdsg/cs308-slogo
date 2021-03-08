package slogo.model.ASTNodes;

public class ASTGreater extends ASTBinaryOperator {

  private static final String NAME = "greaterthan";

  public ASTGreater() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return ret1 > ret2 ? 1.0 : 0.0;
  }
}
