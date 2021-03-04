package slogo.model.ASTNodes;

public abstract class ASTNamed extends ASTNode {

  protected String name;

  ASTNamed(String name) {
    super();
    this.name = name;
  }
}
