package slogo.model.ASTNodes;

import java.util.List;
import java.util.Map;
import slogo.model.InfoBundle;

/**
 * Children:
 * <ol>
 *   <li>Actual parameter 1</li>
 *   <li>Actual parameter 2</li>
 *   <li>...</li>
 * </ol>
 */
public class ASTFunctionCall extends ASTCommand {

  private ASTFunctionDefinition function;

  /**
   * Constructor
   *
   * @param function The function being called
   */
  public ASTFunctionCall(ASTFunctionDefinition function) {
    super(function.getIdentifier(), function.getParameterNames().size());
    this.function = function;
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    // insert actual parameters into the lookup table
    Map<String, ASTNode> table = info.getLookupTable();
    List<String> parameterNames = function.getParameterNames();
    for (int i = 0; i < getNumParams(); ++i) {
      table.put(parameterNames.get(i), getChildAt(i));
    }

    return function.getCommands().evaluate(info);
  }
}
