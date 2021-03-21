package slogo.model.ASTNodes;

import java.util.ArrayList;
import slogo.model.InfoBundle;

/**
 * Children:
 * <ol>
 *   <li>Compound statement containing indices of turtles</li>
 * </ol>
 */
public class ASTTell extends ASTCommand {

  private static final int NUM_PARAMS = 1;
  private static final String NAME = "Tell";

  public ASTTell() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    ASTCompoundStatement comp = (ASTCompoundStatement) getChildAt(0);
    double ret = info.getMainTurtle().getId();
    ArrayList<Integer> indices = new ArrayList<>();
    for (ASTNode node : comp.getChildren()) {
      int idx = (int) node.evaluate(info);
      ret = idx;
      indices.add(idx);
    }
    info.setCurrTurtle(indices);
    return ret;
  }
}
