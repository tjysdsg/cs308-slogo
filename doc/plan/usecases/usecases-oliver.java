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

    }
  }
}

class Environment {
  public double runCommand(String cmd) throws InvalidSyntaxException {
    AST ast = parser.parseCommand(cmd);
    return ast.evaluate(executionBundle);
  }
}

// When the user presses the run button, the view will call this method
try {
    modelController.sendCommand(cmd);
    } catch (UnknownCommandException e) {
    showAlert(ExceptionsBundle.get(e.getName()));
    }

// The controller will then tell the parser to parse
class ModelController {
  public double sendCommand(String cmd) throws UnknownCommandException {
    return environment.runCommand(cmd);
  }
}

class Environment {
  public double runCommand(String cmd) throws UnknownCommandException {
    AST ast = parser.parseCommand(cmd);
    return ast.evaluate(executionBundle);
  }
}
