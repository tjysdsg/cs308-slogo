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
    int size = getNumChildren();
    if (size > numParams) {
      throw new IncorrectParameterCountException(this);
    }
  }

  @Override
  public int addChild(ASTNode newChild) throws IncorrectParameterCountException {
    super.addChild(newChild);
    int size = getNumChildren();

    if (size > numParams) {
      throw new IncorrectParameterCountException(this);
    }
    return size;
  }

  public boolean isDone() {
    return numParams == getNumChildren();
  }
}
