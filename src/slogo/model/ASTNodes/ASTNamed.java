package slogo.model.ASTNodes;

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
