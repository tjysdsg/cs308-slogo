package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTNumberLiteral extends ASTNode {

  protected int value;

  /**
   * Creates a new ASTNode instance that contains the name and the number of parameters
   */
  public ASTNumberLiteral(int value) {
    super();
    this.value = value;
  }

  @Override
  public double doEvaluate(InfoBundle info) {
    return this.value;
  }
}
