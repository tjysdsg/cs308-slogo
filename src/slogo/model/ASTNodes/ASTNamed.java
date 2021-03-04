package slogo.model.ASTNodes;

// TODO: load name from language resource file
public abstract class ASTNamed extends ASTNode {

  protected String name;

  ASTNamed(String name) {
    super();
    this.name = name;
  }
}
