package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

// TODO: load name from language resource file
public class ASTForward extends ASTCommand {

  private static final int NUM_PARAMS = 1;
  private static final String NAME = "forward";

  public ASTForward() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    double delta = children.get(0).evaluate(info);
    info.getTurtle().move(delta);
    return 0;
  }
}
