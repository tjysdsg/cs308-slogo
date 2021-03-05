package slogo.model.ASTNodes;

public class ASTLog extends ASTUnaryOperator {

  private static final String NAME = "naturallog";

  public ASTLog() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1) {
    return Math.log(ret1);
  }
}
