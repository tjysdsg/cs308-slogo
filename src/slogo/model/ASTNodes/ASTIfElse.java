package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;

/**
 * Children:
 * <ol>
 *   <li>Predicate</li>
 *   <li>Then branch</li>
 *   <li>Else branch</li>
 * </ol>
 */
public class ASTIfElse extends ASTCommand {

  private static final int NUM_PARAMS = 3;
  private static final String NAME = "IfElse";

  public ASTIfElse() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info, List<ASTNode> params) {
    if (params.get(0).evaluate(info) != 0) {
      return params.get(1).evaluate(info);
    } else {
      return params.get(2).evaluate(info);
    }
  }
}
