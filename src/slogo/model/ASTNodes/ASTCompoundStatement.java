package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;

public class ASTCompoundStatement extends ASTNode {

  public ASTCompoundStatement(List<ASTNode> children) {
    for (ASTNode child : children) {
      addChild(child);
    }
  }

  @Override
  protected double doEvaluate(InfoBundle info, List<ASTNode> params) {
    double ret = 0;
    for (ASTNode node : getChildren()) {
      ret = node.evaluate(info);
    }
    return ret;
  }

  @Override
  public boolean isDone() {
    for (ASTNode node : getChildren()) {
      if (!node.isDone()) {
        return false;
      }
    }
    return true;
  }
}
