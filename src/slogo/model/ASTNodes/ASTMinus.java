package slogo.model.ASTNodes;

public class ASTMinus extends ASTUnaryOperator {

  private static final String NAME = "Minus";

  public ASTMinus() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1) {
    return -ret1;
  }
}
