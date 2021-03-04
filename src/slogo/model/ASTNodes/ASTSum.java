package slogo.model.ASTNodes;

// TODO: load name from language resource file
public class ASTSum extends ASTBinaryOperator {

  private static final String NAME = "sum";

  public ASTSum() {
    super(NAME);
  }

  @Override
  protected double calculate(double ret1, double ret2) {
    return ret1 + ret2;
  }
}
