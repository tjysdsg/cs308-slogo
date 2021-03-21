package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;

public class ASTNumberLiteral extends ASTNode {

  protected double value;

  /**
   * Creates a new ASTNode instance that contains the name and the number of parameters
   */
  public ASTNumberLiteral(double value) {
    super();
    setToken(String.valueOf(value));
    this.value = value;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  @Override
  public double doEvaluate(InfoBundle info, List<ASTNode> params) {
    return this.value;
  }

  @Override
  public boolean isDone() {
    return true;
  }
}
