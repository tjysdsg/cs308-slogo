package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public abstract class ASTBinaryOperator extends ASTCommand {

  public ASTBinaryOperator(String name) {
    super(name, 2);
  }

  protected abstract double calculate(double ret1, double ret2);

  @Override
  protected double doEvaluate(InfoBundle info) {
    return calculate(
        getChildAt(0).evaluate(info),
        getChildAt(1).evaluate(info)
    );
  }
}
