package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;

/**
 * Reducible binary operators are the commands that can be grouped, and will apply all arguments at
 * once, such as (SUM 1 2 3 4 5) will "reduce" to 15
 * <p>
 * In contrast, (MAKE :x 100 :y 200) is not a reducible operator since it's a shorthand for [MAKE :x
 * 100 MAKE :y 200]
 *
 * @author Jiyang Tang
 */
public abstract class ASTReducibleBinaryOperator extends ASTBinaryOperator {

  public ASTReducibleBinaryOperator(String name) {
    super(name);
  }

  @Override
  protected double doEvaluate(InfoBundle info, List<ASTNode> params) {
    double ret = calculate(
        getChildAt(0).evaluate(info),
        getChildAt(1).evaluate(info)
    );

    int n = getNumChildren();
    if (n > 2) {
      for (int i = 2; i < n; ++i) {
        ret = calculate(ret, getChildAt(i).evaluate(info));
      }
    }
    return ret;
  }
}
