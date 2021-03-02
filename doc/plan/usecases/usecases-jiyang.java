/**
 * The user types 'fd 50' in the command window, and sees the turtle move in the display window
 * leaving a trail, and the command is added to the environment's history.
 */

// after receiving a command, View calls this method
modelController.sendCommand(cmd);

class ModelController {

  public double sendCommand(String cmd) {
    return environment.runCommand(cmd);
  }
}

class Environement {

  public double runCommand(String cmd) {
    AST ast = parser.parseCommand(cmd);
    double ret = ast.evaluate(executionBundle);
    this.notifyTurtleUpdate(turtleInfo);
    this.notifyCommandUpdate(commandInfo);
    this.notifyVariableUpdate(variableInfo);
    return ret;
  }
}
