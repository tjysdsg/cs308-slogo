package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

/**
 * Children:
 * <ol>
 *   <li>Expression</li>
 *   <li>Compound statement</li>
 * </ol>
 */
public class ASTRepeat extends ASTCommand {

  private static final int NUM_PARAMS = 2;
  private static final String NAME = "Repeat";
  private static final String REPCOUNT_NAME = "repcount";

  public ASTRepeat() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    double ret = 0.0;
    int count = (int) getChildAt(0).evaluate(info);
    ASTNumberLiteral variable = new ASTNumberLiteral(1.0);
    for (int i = 0; i < count; ++i) {
      // `:repcount` value is set in the lookup table as a variable
      variable.setValue(i + 1); // repcount starts at 1
      info.getLookupTable().put(REPCOUNT_NAME, variable);

      ret = getChildAt(1).evaluate(info);
    }

    return ret;
  }
}
