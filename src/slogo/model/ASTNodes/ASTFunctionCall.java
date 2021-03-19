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
   * @param identifier     Name of the function being called
   * @param parameterNames Parameter names
   */
  public ASTFunctionCall(String identifier, List<String> parameterNames) {
    super(identifier, parameterNames.size());
    this.parameterNames = parameterNames;
  }

  public ASTFunctionCall(String identifier, List<String> parameterNames, ASTNode body) {
    super(identifier, parameterNames.size());
    this.parameterNames = parameterNames;
    this.body = body;
  }

  public void setBody(ASTNode body) {
    this.body = body;
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    // insert actual parameters into the lookup table
    for (int i = 0; i < getNumParams(); ++i) {
      ASTNumberLiteral value = new ASTNumberLiteral(getChildAt(i).evaluate(info));
      info.setVariable(parameterNames.get(i), value);
    }

    // TODO: Create Clone
    return body.evaluate(info.clone());
  }

  public ASTFunctionCall clone() {
    return new ASTFunctionCall(getName(), parameterNames, body);
  }

  // TODO: func.toString() doesn't return signature
  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder();
    ret.append(getName()).append(" [");

    // parameters
    int nParams = parameterNames.size();
    for (int i = 0; i < nParams; ++i) {
      ret.append(parameterNames.get(i));
      if (i < nParams - 1) {
        ret.append(", ");
      }
    }

    ret.append("]");
    return ret.toString();
  }
}
