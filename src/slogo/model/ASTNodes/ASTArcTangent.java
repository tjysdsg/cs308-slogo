package slogo.model.ASTNodes;

public class ASTArcTangent extends ASTUnaryOperator {

  private static final String NAME = "ArcTangent";

  public ASTArcTangent() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1) {
    return Math.atan(ret1);
  }
}
