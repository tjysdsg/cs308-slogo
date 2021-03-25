package slogo.model.ASTNodes;

/**
 * Baseclass for variable/command declarations
 *
 * @author Jiyang Tang, Oliver Rodas
 */
public abstract class ASTDeclaration extends ASTGroupableCommand {

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

  /**
   * Get identifier
   */
  public String getIdentifier() {
    return identifier;
  }
}

