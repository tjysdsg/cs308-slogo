package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

/**
 * Children:
 * <ol>
 *   <li>Variable name</li>
 *   <li>Expression</li>
 * </ol>
 */
public class ASTMakeVariable extends ASTDeclaration {

  private static final int NUM_PARAMS = 2;
  private static final String NAME = "MakeVariable";

  public ASTMakeVariable() {
    super(NAME, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    String name = ((ASTNamed) getChildAt(0)).getName();
    setIdentifier(name);

    double value = getChildAt(1).evaluate(info);
    ASTNumberLiteral numberLiteral = new ASTNumberLiteral(value);
    info.getVariableTable().put(name, numberLiteral);

    return value;
  }
}
