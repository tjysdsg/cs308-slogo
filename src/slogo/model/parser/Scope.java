package slogo.model.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import slogo.exceptions.IncorrectParameterCountException;
import slogo.model.ASTNodes.ASTCompoundStatement;
import slogo.model.ASTNodes.ASTNode;

public class Scope {
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

  public ASTNode peek() {
    if (myStack.isEmpty())
      return null;
    return myStack.peek();
  }

  public void addNode(ASTNode command) {
    if (addNextAsChild()) {
      myStack.peek().addChild(command);
    }
    myStack.push(command);
    collapse();
  }

  public void addAllNodes(List<ASTNode> orphanage) {
    for (ASTNode child : orphanage) {
      myStack.peek().addChild(child);
    }
    collapse();
  }

  public ASTNode getCommands() {
    if (isIncomplete()) {
      throw new IncorrectParameterCountException(myStack.peek());
    }
    return new ASTCompoundStatement(commands);
  }

  private boolean isIncomplete() {
    return !myStack.isEmpty();
  }

  public boolean addNextAsChild() {
    return !myStack.isEmpty();
  }
}
