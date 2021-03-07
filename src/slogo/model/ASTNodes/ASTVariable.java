package slogo.model.ASTNodes;

import slogo.exceptions.UnknownIdentifierException;
import slogo.model.InfoBundle;

/**
 * A variable can be declared without a value, so the default value is 0.0
 */
public class ASTVariable extends ASTNamed {

  public ASTVariable(String name) {
    super(name);
  }

  @Override
  public double doEvaluate(InfoBundle info) throws UnknownIdentifierException {
    ASTNode referred = info.getLookupTable().get(getName());
    if (referred == null) {
      throw new UnknownIdentifierException(getName());
    }
    return referred.evaluate(info);
  }

  @Override
  public boolean isDone() {
    return true;
  }
}
