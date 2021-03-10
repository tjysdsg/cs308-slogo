package slogo.model.ASTNodes;

public class ASTTangent extends ASTUnaryOperator {

  private static final String NAME = "Tangent";

  public ASTTangent() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1) {
    return Math.tan(ret1);
  }
}
