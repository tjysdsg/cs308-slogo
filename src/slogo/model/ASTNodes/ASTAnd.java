 package slogo.model.ASTNodes;

public class ASTAnd extends ASTBinaryOperator {

  private static final String NAME = "and";

  public ASTAnd() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return ((ret1 != 0) && (ret2 != 0)) ? 1.0 : 0.0;
  }
}
