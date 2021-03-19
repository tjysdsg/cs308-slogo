package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTFunctionReference extends ASTCommand {

  public ASTFunctionReference(String identifier, int NUM_PARAMS) {
    super(identifier, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    ASTNode body = info.getCommand(getName()).clone();
    for (ASTNode child : getChildren()) {
      body.addChild(child);
    }
    return body.evaluate(info.clone());
  }
}
