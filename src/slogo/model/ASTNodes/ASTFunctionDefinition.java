package slogo.model.ASTNodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import slogo.model.InfoBundle;

/**
 * Contains definition of a function, use {@link ASTFunctionDefinition#getParameterNames()} to call
 * the commands in this function
 */
public class ASTFunctionDefinition extends ASTDeclaration {

  private static final String NAME = "makeuserinstruction";
  private static final int NUM_PARAMS = 2;
  private ASTNode vars; // contains a list of ASTVariable names
  private ASTNode commands;
  private Map<String, ASTFunctionCall> functionTable;
  private int result;

  public ASTFunctionDefinition(String identifier, Map<String, ASTFunctionCall> functionTable) {
    super(NAME, identifier, NUM_PARAMS);
    this.functionTable = functionTable;
//    this.vars = vars;
//    this.commands = commands;
  }

//  public ASTNode getFunctionBody() {
//    return commands;
//  }

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
    return result;
  }

  @Override
  public int addChild(ASTNode newChild) {
    int numChildren = super.addChild(newChild);
    if (numChildren == 1) {
      vars = newChild;
    } else {
      commands = newChild;

      ASTNode prev = functionTable.getOrDefault(getIdentifier(), null);
      if (prev != null) {
        result = 0;
      } else {
        result = 1;
      }

      functionTable.put(getIdentifier(),
          new ASTFunctionCall(getIdentifier(),
              getParameterNames(),
              commands));
    }
    return numChildren;
  }
}
