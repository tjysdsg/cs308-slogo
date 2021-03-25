package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;

/**
 * Base class of all unary operators
 *
 * @author Jiyang Tang
 */
public abstract class ASTUnaryOperator extends ASTGroupableCommand {

  public ASTUnaryOperator(String name) {
    super(name, 1);
  }

  protected abstract double calculate(double ret1);

  @Override
  protected double doEvaluate(InfoBundle info, List<ASTNode> params) {
    return calculate(params.get(0).evaluate(info));
  }
}
