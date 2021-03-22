package slogo.model.ASTNodes;

import java.util.List;
import slogo.model.InfoBundle;

public class ASTFunctionReference extends ASTCommand {

  public ASTFunctionReference(String identifier, int NUM_PARAMS) {
    super(identifier, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info, List<ASTNode> params) {
    ASTNode body = info.getCommand(getName()).clone();
    for (ASTNode child : getChildren()) {
      body.addChild(child);
    }
    return body.evaluate(info.clone());
  }
}
