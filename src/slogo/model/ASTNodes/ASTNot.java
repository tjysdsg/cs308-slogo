package slogo.model.ASTNodes;

public class ASTNot extends ASTUnaryOperator {

  private static final String NAME = "not";

  public ASTNot() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1) {
    return ret1 != 0 ? 0.0 : 1.0;
  }
}
