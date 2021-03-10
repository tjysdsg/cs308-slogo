package slogo.model.ASTNodes;

public class ASTSine extends ASTUnaryOperator {

  private static final String NAME = "Sine";

  public ASTSine() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1) {
    return Math.sin(ret1);
  }
}
