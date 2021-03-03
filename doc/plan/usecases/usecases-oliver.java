/**
 * Use case 1:
 * <p>
 The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.
 */

// When the user presses the run button, the view will call this method
try {
  modelController.sendCommand(cmd);
} catch (InvalidSyntaxException e) {
  showAlert(ExceptionsBundle.get(e.getName()));
}

// The controller will then tell the parser to parse
class ModelController {
  public double sendCommand(String cmd) throws InvalidSyntaxException {
    try {
      return environment.runCommand(cmd);
    } catch (ModelException e) {
      String exMessage = exceptions.get(e.getMessage());

      view.showAlert(e.format(exMessage););
    }
  }
}

class Environment {
  public double runCommand(String cmd) throws InvalidSyntaxException {
    AST ast = parser.parseCommand(cmd);
    return ast.evaluate(executionBundle);
  }
}

/**
 * Use Case 2
 * The user uses a command that doesnt exist in the library. When they press the run button, they see an error because that command doesnt exist. Only the exception that is thrown is seen (there will be others depending on what the parser shows)
 */

// When the user presses the run button, the view will call this method
try {
    modelController.sendCommand(cmd);
} catch (InvalidSyntaxException e) {
    showAlert(ExceptionsBundle.get(e.getName()));
}


// The controller will then tell the parser to parse
class ModelController {
  public double sendCommand(String cmd) throws UnknownCommandException {
    try {
      return environment.runCommand(cmd);
    } catch (ModelException e) {
      String exMessage = exceptions.get(e.getMessage());

      view.showAlert(e.format(exMessage););
    }
  }
}

class Environment {
  public double runCommand(String cmd) throws UnknownCommandException {
    AST ast = parser.parseCommand(cmd);
    return ast.evaluate(executionBundle);
  }
}

/**
 * Use Case 3:
 * The user creates a new command using the TO command. They can later use the command
 */

// The normal set of commands is done, but the parser completes
public class Environment {
  public double runCommand(String cmd) throws ModelException {
    AST ast = parser.parseCommand(cmd);
    return ast.evaluate(executionBundle);
  }
}

// In the AST node for the createCommand
public class createCommand extends ASTNode {
  private String myName;
  private static final int TO_SAVE = 0;
  public double evaluate(ExecutionBundle info) {
    Map<String, ASTNode> table = info.getLookUp();
    return table.put(myName, myChildren.get(TO_SAVE));
  }
}

/**
 * Use Case 4
 * The user correctly inputs a command that moves the turtle Ex: forward 50
 */

// The normal set of commands is done, but the parser completes
// In the AST node for the move command
public class forward extends ASTNode {
  private double myVal;
  private static final int TO_SAVE = 0;
  public double evaluate(ExecutionBundle info) {
    Turtle myTurtle = info.getTurtle();
    return myTurtle.move(myVal);
  }
}

// Inside of the turtle
public class Turtle {
  private UpdateTurtle myListener;

  public Turtle(UpdateTurtle callback) {
    myListener = callback;
  }

  public double move(double x) {
    // Do stuff to move

    // Notify environment
    myListener.execute(new TurtleRecord(myX, myY, myRotate));
  }
}
