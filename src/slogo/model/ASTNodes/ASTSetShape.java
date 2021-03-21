package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;

public class ASTSetShape extends ASTCommand {

  private static final int NUM_PARAMS = 1;
  private static final String NAME = "SetShape";

  public ASTSetShape() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info, List<ASTNode> params) {
    double arg = params.get(0).evaluate(info);
    info.setShapeIdx((int) arg);
    return arg;
  }
}
