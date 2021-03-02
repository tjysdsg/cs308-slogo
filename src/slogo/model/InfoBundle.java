package slogo.model;

import java.util.Map;
import slogo.model.ASTNodes.ASTNode;

/**
 * Information that ASTNodes required in order to evaluate.
 */
public interface InfoBundle {

  /**
   * @return The turtle within the environment
   */
  public Turtle getTurtle();

  /**
   * Returns the mapping of names to AST trees.
   *
   * @return The table of command names to AST tree.
   */
  public Map<String, ASTNode> getLookupTable();
}