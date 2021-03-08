package slogo.model.ASTNodes;

import java.util.List;

public abstract class ASTDeclaration extends ASTCommand {

  private String identifier;

  /**
   * Declaration knowing the name of variable/function beforehand
   */
  public ASTDeclaration(String cmdName, String identifier, int numParams) {
    super(cmdName, numParams);
    this.identifier = identifier;
  }

  /**
   * Declaration not knowing the name of variable/function yet
   *
   * @implNote subclass should call {@link ASTDeclaration#setIdentifier} to set the identifier
   * before evaluation
   */
  public ASTDeclaration(String cmdName, int numParams) {
    super(cmdName, numParams);
    this.identifier = null;
  }

  /**
   * Set identifier
   *
   * @implNote Intended to be called only by subclasses
   */
  protected void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }
}

