package slogo.model.ASTNodes;

public class ASTRemainder extends ASTBinaryOperator {

  private static final String NAME = "remainder";

  public ASTRemainder() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return ret1 % ret2;
  }
}
