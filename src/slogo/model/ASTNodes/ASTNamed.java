package slogo.model.ASTNodes;

// TODO: load name from language resource file
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
