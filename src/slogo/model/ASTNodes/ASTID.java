package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;

public class ASTID extends ASTCommand {

  private static final int NUM_PARAMS = 0;
  private static final String NAME = "id";

  public ASTID() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info, List<ASTNode> params) {
    return info.getMainTurtle().getId();
  }
}
