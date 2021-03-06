package slogo.model.ASTNodes;

import slogo.model.InfoBundle;

public class ASTFunction extends ASTDeclaration {

  private static final String NAME = "To";
  private static final int NUM_PARAMS = 2;
  private ASTCompoundStatement vars;
  private ASTCompoundStatement commands;

  public ASTFunction(String identifier, ASTCompoundStatement vars, ASTCompoundStatement commands) {
    super(NAME, identifier, NUM_PARAMS);
  }

  @Override
  protected double doEvaluate(InfoBundle info) {
    /*

     */
    return 0;
  }

//  @Override
//  public int addChild() {
//
//  }
}


/**
 * Children
 * Variables  Commands
 * numParams = 2
 * doThing 2 fd 50
 *
 * To doThing [var1 var2] [commands]
 * set var1 = child(0)
 * set var2 = child(1)
 *
 * function name = doThing
 * 2  fd 50
 *
 * int i = 0;
 * for (var : vars) {
 *   setVariable(var, getChild(i)
 * }
 *
 *
 */