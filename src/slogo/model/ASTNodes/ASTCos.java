package slogo.model.ASTNodes;

public class ASTCos extends ASTUnaryOperator {

  private static final String NAME = "cos";

  public ASTCos() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1) {
    return Math.cos(ret1);
  }
}
