package slogo.model.ASTNodes;

import java.util.List;

public abstract class ASTDeclaration extends ASTCommand {

  private String identifier;

  public ASTDeclaration(String cmdName, String identifier, int numParams) {
    super(cmdName, numParams);
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }
}

