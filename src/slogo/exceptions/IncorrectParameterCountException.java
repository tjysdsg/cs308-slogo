package slogo.exceptions;

import slogo.model.ASTNodes.ASTCommand;
import slogo.model.ASTNodes.ASTNode;

/**
 * This Exception is used when a token is given the wrong number of parameters. It can greater or higher than the required amount
 */
public class IncorrectParameterCountException extends ModelException {
  private int expected;
  private int actual;
  private String token;
  private static final String name = "IncorrectParameterCountException";

  /**
   * Creates a new instance of this exception.
   * @param expected number of arguments
   * @param actual number of arguments
   * @param token the command that failed
   */
  public IncorrectParameterCountException(ASTNode problem) {
    super(name);
    this.expected = problem.getNumParams();
    this.actual = problem.getNumChildren();
    this.token = problem.getToken();
  }

  @Override
  public String buildException(String format) {
    return String.format(format, token, expected, actual);
  }
}