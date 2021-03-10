package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

/**
 * Children:
 * <ol>
 *   <li>Compound statement containing {@code variable}, {@code start} and {@code end}</li>
 *   <li>Compound statement containing commands</li>
 *
 * </ol>
 */
public class ASTFor extends ASTCommand {

  private static final int NUM_PARAMS = 2;
  private static final String NAME = "For";

  public ASTFor() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    double ret = 0.0;

    // TODO: error checking
    ASTCompoundStatement comp1 = (ASTCompoundStatement) getChildAt(0);
    ASTCompoundStatement comp2 = (ASTCompoundStatement) getChildAt(1);

    String counterName = ((ASTNamed) comp1.getChildAt(0)).getName();
    double start = comp1.getChildAt(1).evaluate(info);
    double end = comp1.getChildAt(2).evaluate(info);
    double step = comp1.getChildAt(3).evaluate(info);

    ASTNumberLiteral variable = new ASTNumberLiteral(1.0);
    for (double i = start; i <= end; i += step) {
      // counter is set in the lookup table as a variable
      variable.setValue(i);
      info.getVariableTable().put(counterName, variable);

      ret = comp2.evaluate(info);
    }

    return ret;
  }
}
