package slogo.model.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import slogo.model.ASTNodes.ASTCommand;
import slogo.model.ASTNodes.ASTCompoundStatement;
import slogo.model.ASTNodes.ASTNode;

public class Scope {
  private Stack<ASTCommand> myStack = new Stack<>();
  private List<ASTNode> commands = new ArrayList<>();

  public void collapse() {
    while (myStack.size() > 1 && myStack.peek().isDone()) {
      myStack.pop();
    }

    if (myStack.peek().isDone()) {
      commands.add(myStack.pop());
    }
  }

  public ASTCommand peek() {
    return myStack.peek();
  }

  public void push(ASTCommand command) {
    if (addNextAsChild()) {
      myStack.peek().addChild(command);
    }
    myStack.push(command);
  }

  public ASTNode getCommands() {
    if (commands.size() == 1) {
      return commands.get(0);
    } else {
      //TODO: Add the commands
      return new ASTCompoundStatement();
    }
  }

  public boolean isIncomplete() {
    return commands.isEmpty() || !myStack.isEmpty();
  }

  public boolean addNextAsChild() {
    return !myStack.isEmpty();
  }
}
