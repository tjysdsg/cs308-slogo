package slogo.model.ASTNodes;

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
  protected double doEvaluate(InfoBundle info) {
    if (getChildAt(0).evaluate(info) != 0) {
      return getChildAt(1).evaluate(info);
    }
    return getChildAt(2).evaluate(info);
  }
}
