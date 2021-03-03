package slogo.exceptions;

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
  public IncorrectParameterCountException(int expected, int actual, String token) {
    super(name);
    this.expected = expected;
    this.actual = actual;
    this.token = token;
  }

  @Override
  public String buildException(String format) {
    return String.format(format, token, expected, actual);
  }
}