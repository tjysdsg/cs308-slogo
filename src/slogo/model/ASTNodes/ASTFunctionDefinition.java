package slogo.model.ASTNodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import slogo.model.InfoBundle;

/**
 * Contains definition of a function, use {@link ASTFunctionDefinition#getCommands()} and {@link
 * ASTFunctionDefinition#getParameterNames()} to call the commands in this function
 */
public class ASTFunctionDefinition extends ASTDeclaration {

  private static final String NAME = "makeuserinstruction";
  private static final int NUM_PARAMS = 2;
  private ASTCompoundStatement vars; // contains a list of ASTVariable names
  private ASTCompoundStatement commands;

  public ASTFunctionDefinition(String identifier, ASTCompoundStatement vars,
      ASTCompoundStatement commands) {
    super(NAME, identifier, NUM_PARAMS);
    this.vars = vars;
    this.commands = commands;
  }

  public ASTCompoundStatement getCommands() {
    return commands;
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
    Map<String, ASTNode> table = info.getLookupTable();

    // check if there's already a function with the same name
    ASTNode prev = table.getOrDefault(getIdentifier(), null);
    if (prev != null) {
      return 0;
    }

    // insert this into the lookup table
    info.getLookupTable().put(getIdentifier(), this);
    return 1;
  }
}
