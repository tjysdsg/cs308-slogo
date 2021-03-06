package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTCompoundStatement extends ASTNode {
  // TODO: numParams()?

  @Override
  protected double doEvaluate(InfoBundle info) {
    double ret = 0;
    for (int i = 0; i < getNumChildren(); ++i) {
      ret = getChildAt(i).evaluate(info);
    }
    return ret;
  }
}
