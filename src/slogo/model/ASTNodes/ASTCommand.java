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
  protected void preEvaluate(InfoBundle info) {
    int size = children.size();
    if (size > numParams) {
      throw new IncorrectParameterCountException(numParams, size, token);
    }
  }

  @Override
  public int addChild(ASTNode newChild) throws IncorrectParameterCountException {
    int size = children.size();
    if (size + 1 > numParams) {
      throw new IncorrectParameterCountException(numParams, size, token);
    }
    children.add(newChild);
    return size + 1;
  }
}
