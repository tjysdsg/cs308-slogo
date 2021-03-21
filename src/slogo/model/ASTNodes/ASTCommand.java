package slogo.model.ASTNodes;

import slogo.exceptions.IncorrectParameterCountException;
import slogo.model.InfoBundle;

public abstract class ASTCommand extends ASTNamed {

  protected int numParams;

  /**
   * Creates a new instance that contains the name and the number of parameters
   */
  public ASTCommand(String name, int numParams) {
    super(name);
    this.numParams = numParams;
  }

  @Override
  public int getNumParams() {
    return numParams;
  }

  @Override
  protected void preEvaluate(InfoBundle info) {
  }

  @Override
  public int addChild(ASTNode newChild) throws IncorrectParameterCountException {
    super.addChild(newChild);
    return getNumChildren();
  }

  @Override
  public boolean isDone() {
    return numParams <= getNumChildren();
  }
}
