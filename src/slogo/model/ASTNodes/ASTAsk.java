package slogo.model.ASTNodes;

import java.util.ArrayList;
import java.util.List;
import slogo.model.InfoBundle;
import slogo.model.Turtle;

/**
 * Children:
 * <ol>
 *   <li>Compound statement containing indices of turtles</li>
 *   <li>Compound statement containing commands to execute</li>
 * </ol>
 */
public class ASTAsk extends ASTCommand {

  private static final int NUM_PARAMS = 2;
  private static final String NAME = "Ask";

  public ASTAsk() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info, List<ASTNode> params) {
    double ret = 0;

    // get previous active turtles so that we can restore them later
    List<Turtle> prevTurtles = info.getActiveTurtles();
    ArrayList<Integer> prevTurtleIdx = new ArrayList<>();
    for (Turtle t : prevTurtles) {
      prevTurtleIdx.add(t.getId());
    }
    // get previous main turtle
    int prevMainTurtle = info.getMainTurtle().getId();

    ASTCompoundStatement comp = (ASTCompoundStatement) params.get(0);
    ASTCompoundStatement commands = (ASTCompoundStatement) params.get(1);

    // run all commands for each turtle index
    for (ASTNode child : comp.getChildren()) {
      int idx = (int) child.evaluate(info);

      // only contains 1 index, so that ASTTurtleCommand works as intended
      ArrayList<Integer> indices = new ArrayList<>();
      indices.add(idx);
      info.setCurrTurtle(indices);

      // run commands
      ret = commands.evaluate(info);
    }

    // restore previous active and main turtles
    info.setCurrTurtle(prevTurtleIdx);
    info.setMainTurtle(prevMainTurtle);
    return ret;
  }
}
