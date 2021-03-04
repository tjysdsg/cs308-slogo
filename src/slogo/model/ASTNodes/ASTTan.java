package slogo.model.ASTNodes;

public class ASTTan extends ASTUnaryOperator {

  private static final String NAME = "tan";

  public ASTTan() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1) {
    return Math.tan(ret1);
  }
}
