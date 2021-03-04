package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public abstract class ASTUnaryOperator extends ASTCommand {

  public ASTUnaryOperator(String name) {
    super(name, 1);
  }

  protected abstract double calculate(double ret1);

  @Override
  protected double doEvaluate(InfoBundle info) {
    return calculate(children.get(0).evaluate(info));
  }
}
