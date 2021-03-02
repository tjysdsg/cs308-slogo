/**
 * Use case 1:
 * <p>
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
    return ast.evaluate(executionBundle);
  }
}

  /**
   * Use case 2:
   * <p>
   * The user click on an item in the command table, and a help dialog pops up
   */

// after receiving the selected command, View does this:
Command cmd = modelController.getCommand(cmd);
this.showCommandHelp(Command cmd);

class ModelController {

  public double getCommand(String name) {
    return infoBundle.getLookupTable().get(name);
  }
}

/**
 * Use case 3:
 * <p>
 * The user changes the language of the application
 */

// view calls this
viewController.setLanguage(language);
modelController.setLanguage(language);

/**
 * Use case 4:
 * <p>
 * The user types 'CLEARSCREEN' command, and turtle's trails is erased and it is sent to the home
 * position, returns distance turtle moved
 */

// Same as use case 1, except in ClearScreen#evaluate:
class ClearScreenCmd extends AST {

  public double evaluate(ExecutionBundle executionBundle) {
    Turtle turtle = executionBundle.getTurtle();
    Vec2D prevPos = turtle.getPos();
    turtle.setTransform(0.0, 0.0, 0.0);
    return prevPos.magnitude(new Vec2D(0.0, 0.0));
  }
}

