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

  private List<String> parameterNames;
  private ASTNode body;
  private static final String NAME = "FunctionCall";

  /**
   * Constructor
   *
   * @param function The function being called
   */
//  public Func(ASTFunctionDefinition function) {
//    super(function.getIdentifier(), function.getParameterNames().size());
//    identifier = function.getIdentifier();
//    this.parameterNames = function.getParameterNames();
//    this.body = function.getFunctionBody();
//  }

  /**
   * Constructor
   *
   * @param identifier     Name of the function being called
   * @param parameterNames Parameter names
   * @param body           Function body
   */
  public ASTFunctionCall(String identifier, List<String> parameterNames,
      ASTNode body) {
    super(identifier, parameterNames.size());
    this.parameterNames = parameterNames;
    this.body = body;
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    // insert actual parameters into the lookup table
    Map<String, ASTNode> table = info.getLookupTable();
    for (int i = 0; i < getNumParams(); ++i) {
      table.put(parameterNames.get(i), getChildAt(i));
    }

    return body.evaluate(info);
  }

  public ASTFunctionCall clone() {
    return new ASTFunctionCall(getName(), parameterNames, body);
  }
}
