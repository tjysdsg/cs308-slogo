package slogo.model.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import slogo.exceptions.IncorrectParameterCountException;
import slogo.model.ASTNodes.ASTCompoundStatement;
import slogo.model.ASTNodes.ASTNode;

/**
 * The parsing scope class is a helper class used to store multiple commands as a list or a as a
 * tree.
 * <p>
 * This class assumes that handlers implement the rules for which this class is accessed. This class
 * acts as a stack with more overhead and methods to collapse itself
 * <p>
 * This class depends on Java's util, expections, and ASTNode packages.
 * <p>
 * This class can be created, then have nodes added to it using the addNode methods. The scope can
 * be destroyed and its contents obtained by calling the get commands methods. If the commands
 * cannot be constructed correctly, then an exception will be thrown
 *
 * @author Oliver Rodas
 */
public class ParsingScope {
  private Stack<ASTNode> myStack = new Stack<>();
  private List<ASTNode> commands = new ArrayList<>();

  private void collapse() {
    while (myStack.size() > 1 && myStack.peek().isDone()) {
      myStack.pop();
    }

    if (!myStack.isEmpty() && myStack.peek().isDone()) {
      commands.add(myStack.pop());
    }
  }

  /**
   * Add a node, if the previous wants a child, it is added as a child.
   *
   * @param command the command to add
   */
  public void addNode(ASTNode command) {
    if (isNextAChild()) {
      myStack.peek().addChild(command);
    }
    myStack.push(command);
    collapse();
  }

  /**
   * Add all new nodes to a single node.
   *
   * This method assumes that there is at least one command in the stack
   *
   * @param orphanage the nodes to add
   */
  public void addAllNodes(List<ASTNode> orphanage) {
    for (ASTNode child : orphanage) {
      myStack.peek().addChild(child);
    }
    collapse();
  }

  /**
   * Gets the commands in the scope.
   *
   * @return the commands stored in the scope
   * @throws IncorrectParameterCountException If the scope is not finished and a command
   *   does not have all of its children
   */
  public ASTNode getCommands() throws IncorrectParameterCountException {
    if (isIncomplete()) {
      throw new IncorrectParameterCountException(myStack.peek());
    }
    return new ASTCompoundStatement(commands);
  }

  private boolean isIncomplete() {
    return !myStack.isEmpty();
  }

  /**
   * Check if the next node can be a child
   *
   * @return if the next node can be a child
   */
  public boolean isNextAChild() {
    return !myStack.isEmpty();
  }
}
