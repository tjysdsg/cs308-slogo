package slogo.model.ASTNodes;
import java.util.ArrayList;
import java.util.List;
import slogo.exceptions.IncorrectParameterCountException;
import slogo.model.InfoBundle;

/**
 * This class's purpose is to create a template for which all future ASTNodes will have to follow
 *
 * This class sets up the common instance variables that each ASTNode will use.
 *
 */

public abstract class ASTNode {
  private String myName;
  private int numParams;
  private List<ASTNode> myChildren;

  /**
   * Creates a new ASTNode instance that contains the name and the number of parameters
   *
   * @param name
   * @param params
   */
  public ASTNode(String name, int params) {
    myName = name;
    myChildren = new ArrayList<>();
    numParams = params;
  }

  /**
   * An ASTNode can have multiple children. They can be added using the this method. If there are too many children, then there will be an error
   *
   * @param newChild
   *
   * @return The new number of children
   */
  public int addChild(ASTNode newChild) throws IncorrectParameterCountException {
    myChildren.add(newChild);
    return myChildren.size();
  }

  /**
   * This method evaluates a command and returns in value
   * Most ASTNodes will call this method on their children as well.
   *
   * @param info
   *
   * @return The evaluated method
   */
  public abstract double evaluate(InfoBundle info);
}