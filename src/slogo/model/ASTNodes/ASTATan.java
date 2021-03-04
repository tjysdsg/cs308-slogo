package slogo.model.ASTNodes;

public class ASTATan extends ASTUnaryOperator {

  private static final String NAME = "atan";

  public ASTATan() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1) {
    return Math.atan(ret1);
  }
}
