package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;

/**
 * Children:
 * <ol>
 *   <li>Compound statement containing {@code variable} and {@code limit}</li>
 *   <li>Compound statement containing commands</li>
 *
 * </ol>
 */
public class ASTDoTimes extends ASTCommand {

  private static final int NUM_PARAMS = 2;
  private static final String NAME = "DoTimes";

  public ASTDoTimes() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info, List<ASTNode> params) {
    double ret = 0.0;

    // TODO: error checking
    ASTCompoundStatement comp1 = (ASTCompoundStatement) params.get(0);
    ASTCompoundStatement comp2 = (ASTCompoundStatement) params.get(1);

    String counterName = ((ASTNamed) comp1.getChildAt(0)).getName();
    double limit = comp1.getChildAt(1).evaluate(info);

    ASTNumberLiteral variable = new ASTNumberLiteral(1.0);
    for (double i = 1.0; i <= limit; i += 1.0) {
      // counter is set in the lookup table as a variable
      variable.setValue(i);
      info.setVariable(counterName, variable);

      ret = comp2.evaluate(info);
    }

    return ret;
  }
}
