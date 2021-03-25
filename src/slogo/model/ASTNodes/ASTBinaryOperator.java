package slogo.model.ASTNodes;

import java.util.List;
import slogo.exceptions.IncorrectParameterCountException;
import slogo.model.InfoBundle;

/**
 * Base class of binary operators, override {@link ASTBinaryOperator#calculate(double, double)} to
 * create a new operator
 *
 * @author Jiyang Tang
 */
public abstract class ASTBinaryOperator extends ASTCommand {

  public ASTBinaryOperator(String name) {
    super(name, 2);
  }

  protected abstract double calculate(double ret1, double ret2);

  @Override
  protected void preEvaluate(InfoBundle info) {
    super.preEvaluate(info);

    if (getNumChildren() < 2) {
      throw new IncorrectParameterCountException(this);
    }
  }

  @Override
  protected double doEvaluate(InfoBundle info, List<ASTNode> params) {
    return calculate(
        params.get(0).evaluate(info),
        params.get(1).evaluate(info)
    );
  }
}
