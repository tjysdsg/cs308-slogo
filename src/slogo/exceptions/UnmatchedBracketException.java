package slogo.exceptions;

import slogo.model.ASTNodes.ASTNode;

/**
 * This Exception is used when a token is given the wrong number of parameters. It can greater or higher than the required amount
 */
public class UnmatchedBracketException extends ModelException {
  private static final String name = "UnmatchedSquareBracketException";

  /**
   * Creates a new instance of this exception.
   * @param expected number of arguments
   * @param actual number of arguments
   * @param token the command that failed
   */
  public UnmatchedBracketException() {
    super(name);
  }

  @Override
  public String buildException(String format) {
    return format;
  }
}