package slogo.model.ASTNodes;

import java.util.ArrayList;
import java.util.List;
import slogo.exceptions.IncorrectParameterCountException;
import slogo.model.InfoBundle;

/**
 * Groupable commands are the ones that can apply on one multiple sets of parameters in one call,
 * they are essentially the shorthand for writing multiple of them.
 * <p>
 * For example, (SETXY 100 200 300 400) is equivalent to [SETXY 100 200 SETXY 300 400]
 *
 * @see ASTReducibleBinaryOperator for another type of group command calls
 */
public abstract class ASTGroupableCommand extends ASTCommand {

  public ASTGroupableCommand(String name, int numParams) {
    super(name, numParams);
  }

  @Override
  public double evaluate(InfoBundle info) {
    List<ASTNode> children = getChildren();
    int n = children.size();
    int nParams = getNumParams();

    // to be correctly grouped, the number of children should be the multiple of this.numParams
    if (n % nParams != 0) {
      throw new IncorrectParameterCountException(this);
    }

    double ret = 0;
    for (int i = 0; i < n; i += nParams) {
      ArrayList<ASTNode> params = new ArrayList<>();
      for (int j = 0; j < nParams; ++j) {
        params.add(children.get(i + j));
      }
      ret = doEvaluate(info, params);
    }
    return ret;
  }
}
