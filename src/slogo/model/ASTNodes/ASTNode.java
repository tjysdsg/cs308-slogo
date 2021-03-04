package slogo.model.ASTNodes;

import java.util.ArrayList;
import java.util.List;
import slogo.model.InfoBundle;

/**
 * This class's purpose is to create a template for which all future ASTNodes will have to follow
 * <p>
 * This class sets up the common instance variables that each ASTNode will use.
 */
public abstract class ASTNode {

  protected List<ASTNode> children;

  /**
   * The original string we parsed from to create this node
   */
  protected String token = "";

  /**
   * Constructor
   */
  public ASTNode() {
    children = new ArrayList<>();
  }

  public void setToken(String token) {
    this.token = token;
  }

  /**
   * An ASTNode can have multiple children, add an ASTNode as this node's child
   *
   * @return The new number of children
   */
  public int addChild(ASTNode newChild) {
    children.add(newChild);
    return children.size();
  }

  /**
   * This method evaluates a command and returns in value most ASTNodes will call this method on
   * their children as well.
   *
   * @return The return value of evaluated method
   */
  public final double evaluate(InfoBundle info) {
    preEvaluate(info);
    return doEvaluate(info);
    // postEvaluate(info);
  }

  /**
   * Subclass can override this method to do things before actual evaluation. For example, commands
   * can check if the number of parameters is correct here.
   */
  protected void preEvaluate(InfoBundle info) {
  }

  /**
   * Subclasses implement this method to do the actual evaluation
   */
  protected abstract double doEvaluate(InfoBundle info);
}