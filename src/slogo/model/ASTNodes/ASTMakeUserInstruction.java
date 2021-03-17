package slogo.model.ASTNodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import slogo.model.InfoBundle;

/**
 * Contains definition of a function, use {@link ASTMakeUserInstruction#getParameterNames()} to call
 * the commands in this function
 */
public class ASTMakeUserInstruction extends ASTDeclaration {

  private static final String NAME = "MakeUserInstruction";
  private static final int NUM_PARAMS = 2;
  private ASTNode vars; // contains a list of ASTVariable names
  private ASTNode commands;
  private Map<String, ASTFunctionCall> functionTable;
  private int result;

  public ASTMakeUserInstruction(String identifier, Map<String, ASTFunctionCall> functionTable) {
    super(NAME, identifier, NUM_PARAMS);
    this.functionTable = functionTable;
  }

  /**
   * Get a list of parameter names of the function
   */
  public List<String> getParameterNames() {
    ArrayList<String> ret = new ArrayList<>();
    for (int i = 0; i < vars.getNumChildren(); ++i) {
      String name = ((ASTNamed) vars.getChildAt(i)).getName();
      ret.add(name);
    }
    return ret;
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    // add to runtime function table
    //info.getCommandTable().put(getIdentifier(), this);
    return result;
  }

  @Override
  public int addChild(ASTNode newChild) {
    int numChildren = super.addChild(newChild);
    if (numChildren == 1) {
      vars = newChild;
      ASTNode prev = functionTable.getOrDefault(getIdentifier(), null);
      if (prev != null) {
        result = 0;
      } else {
        result = 1;
        functionTable.put(getIdentifier(),
            new ASTFunctionCall(getIdentifier(),
                getParameterNames()));
      }

    } else {
      if (result == 1) {
        functionTable.get(getIdentifier()).setBody(newChild);
      }
    }
    return numChildren;
  }

  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder();
    ret.append(getIdentifier()).append(" [");

    // parameters
    int nParams = vars.getNumChildren();
    for (int i = 0; i < nParams; ++i) {
      ASTVariable variable = (ASTVariable) vars.getChildAt(i);
      ret.append(variable.getName());
      if (i < vars.getNumChildren() - 1) {
        ret.append(", ");
      }
    }

    ret.append("]");
    return ret.toString();
  }
}
