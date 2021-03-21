package slogo.model.ASTNodes;

import java.util.List;
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
  public double doEvaluate(InfoBundle info, List<ASTNode> params) throws UnknownIdentifierException {
    ASTNumberLiteral referred = info.getVariable(getName());
    if (referred == null) { // default to 0
      ASTNumberLiteral value = new ASTNumberLiteral(0);
      info.setVariable(getName(), value);
      return 0;
    }
    return referred.evaluate(info);
  }

  @Override
  public boolean isDone() {
    return true;
  }
}
