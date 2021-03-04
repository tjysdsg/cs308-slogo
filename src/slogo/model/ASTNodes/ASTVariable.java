package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

/**
 * A variable can be declared without a value, so the default value is 0.0
 */
public class ASTVariable extends ASTNamed {

  private double value = 0.0;

  public ASTVariable(String name) {
    super(name);
  }

  public void setValue(double value) {
    this.value = value;
  }

  @Override
  public double doEvaluate(InfoBundle info) {
    return this.value;
  }
}
