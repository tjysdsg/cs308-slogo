package slogo.model.ASTNodes;

public class ASTCosine extends ASTUnaryOperator {

  private static final String NAME = "Cosine";

  public ASTCosine() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1) {
    return Math.cos(ret1);
  }
}
