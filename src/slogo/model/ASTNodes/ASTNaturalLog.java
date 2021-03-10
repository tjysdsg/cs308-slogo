package slogo.model.ASTNodes;

public class ASTNaturalLog extends ASTUnaryOperator {

  private static final String NAME = "NaturalLog";

  public ASTNaturalLog() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1) {
    return Math.log(ret1);
  }
}
