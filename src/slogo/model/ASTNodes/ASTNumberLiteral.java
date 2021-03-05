package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTNumberLiteral extends ASTNode {

  protected double value;

  /**
   * Creates a new ASTNode instance that contains the name and the number of parameters
   */
  public ASTNumberLiteral(double value) {
    super();
    this.value = value;
  }

  public double getValue() {
    return value;
  }

  @Override
  public double doEvaluate(InfoBundle info) {
    return this.value;
  }
}
