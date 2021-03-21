package slogo.model.ASTNodes;

public class ASTRemainder extends ASTReducibleBinaryOperator {

  private static final String NAME = "Remainder";

  public ASTRemainder() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return ret1 % ret2;
  }
}
