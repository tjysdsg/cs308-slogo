package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;

public class ASTCompoundStatement extends ASTNode {
  // TODO: numParams()?

  public ASTCompoundStatement(List<ASTNode> children) {
    for (ASTNode child : children) {
      addChild(child);
    }
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    double ret = 0;
    for (int i = 0; i < getNumChildren(); ++i) {
      ret = getChildAt(i).evaluate(info);
    }
    return ret;
  }

  @Override
  public boolean isDone() {
    for (int i = 0; i < getNumChildren(); ++i) {
      if (!getChildAt(i).isDone()) return false;
    }
    return true;
  }
}
