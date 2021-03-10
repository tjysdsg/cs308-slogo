package slogo.model.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import slogo.model.ASTNodes.ASTCompoundStatement;
import slogo.model.ASTNodes.ASTNode;

public class Scope {
  private Stack<ASTNode> myStack = new Stack<>();
  private List<ASTNode> commands = new ArrayList<>();

  public void collapse() {
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

  public void push(ASTNode command) {
    if (addNextAsChild()) {
      myStack.peek().addChild(command);
    }
    myStack.push(command);
  }

  public ASTNode getCommands() {
    return new ASTCompoundStatement(commands);
  }

  public boolean isIncomplete() {
    return !myStack.isEmpty() || !getCommands().isDone();
  }

  public boolean addNextAsChild() {
    return !myStack.isEmpty();
  }
}
