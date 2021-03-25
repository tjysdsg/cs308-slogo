package slogo.model.ASTNodes;

/**
 * Base class for AST nodes that has a name
 *
 * @author Jiyang Tang
 */
public abstract class ASTNamed extends ASTNode {

  private String name;

  ASTNamed(String name) {
    super();
    setToken(name);
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
