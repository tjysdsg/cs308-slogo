package slogo.model.ASTNodes;

public class ASTSin extends ASTUnaryOperator {

  private static final String NAME = "sine";

  public ASTSin() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1) {
    return Math.sin(ret1);
  }
}
