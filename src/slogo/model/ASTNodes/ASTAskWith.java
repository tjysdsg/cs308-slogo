package slogo.model.ASTNodes;

import java.util.ArrayList;
import java.util.List;
import slogo.model.InfoBundle;
import slogo.model.Turtle;

/**
 * Children:
 * <ol>
 *   <li>Condition</li>
 *   <li>Compound statement containing commands to execute</li>
 * </ol>
 */
public class ASTAskWith extends ASTCommand {

  private static final int NUM_PARAMS = 2;
  private static final String NAME = "AskWith";

  public ASTAskWith() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    double ret = 0;

    // get previous active turtles so that we can restore them later
    List<Turtle> prevTurtles = info.getActiveTurtles();
    ArrayList<Integer> prevTurtleIdx = new ArrayList<>();
    for (Turtle t : prevTurtles) {
      prevTurtleIdx.add(t.getId());
    }
    // get previous main turtle
    int prevMainTurtle = info.getMainTurtle().getId();

    ASTCompoundStatement condition = (ASTCompoundStatement) getChildAt(0);
    ASTCompoundStatement commands = (ASTCompoundStatement) getChildAt(1);

    // check conditions and run commands for all turtle
    for (Turtle turtle : info.getAllTurtles()) {
      // only contains 1 index, so that ASTTurtleCommand works as intended
      ArrayList<Integer> indices = new ArrayList<>();
      indices.add(turtle.getId());
      info.setCurrTurtle(indices);
      info.setMainTurtle(turtle.getId()); // ASTID doesn't automatically set main turtle

      // check condition commands
      if (condition.evaluate(info) != 0) {
        commands.evaluate(info);
      }
    }

    // restore previous active and main turtles
    info.setCurrTurtle(prevTurtleIdx);
    info.setMainTurtle(prevMainTurtle);
    return ret;
  }
}
