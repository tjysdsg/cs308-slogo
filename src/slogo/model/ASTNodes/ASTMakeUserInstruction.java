package slogo.model.ASTNodes;

import java.util.ArrayList;
import java.util.List;
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
  private InfoBundle functionTable;
  private boolean addedCorrectly;

  public ASTMakeUserInstruction(String identifier, InfoBundle functionTable) {
    super(NAME, identifier, NUM_PARAMS);
    this.functionTable = functionTable;
  }

  /**
   * Get a list of parameter names of the function
   */
  public List<String> getParameterNames() {
    ArrayList<String> ret = new ArrayList<>();
    for (ASTNode child : vars.getChildren()) {
      String name = ((ASTNamed) child).getName();
      ret.add(name);
    }
    return ret;
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    // add to runtime function table
    //info.getCommandTable().put(getIdentifier(), this);
    return addedCorrectly ? 1 : 0;
  }

  @Override
  public int addChild(ASTNode newChild) {
    int numChildren = super.addChild(newChild);
    if (numChildren == 1) {
      vars = newChild;
      addedCorrectly = functionTable.setCommand(getIdentifier(),
          new ASTFunctionCall(getIdentifier(),
              getParameterNames()));
    } else {
      if (addedCorrectly) {
        functionTable.getCommand(getIdentifier()).setBody(newChild);
      }
    }
    return numChildren;
  }

  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder();
    ret.append(getIdentifier()).append(" [");

    // parameters
    int idx = 0;
    for (ASTNode param : vars.getChildren()) {
      ASTVariable variable = (ASTVariable) param;
      ret.append(variable.getName());
      if (idx < vars.getNumChildren() - 1) {
        ret.append(", ");
      }
      ++idx;
    }

    ret.append("]");
    return ret.toString();
  }
}
