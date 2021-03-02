package slogo.exceptions;

/**
 * This Exception is used when a token is given the wrong number of parameters. It can greater or higher than the required amount
 */
public class IncorrectParameterCountException extends RuntimeException {
  private int expected;
  private int actual;

  /**
   * Creates a new instance of this exception.
   * @param expected number of arguments
   * @param actual number of arguments
   * @param token the command that failed
   */
  public IncorrectParameterCountException(int expected, int actual, String token) {
    super(String.format("Incorrect Parameter Count for %s, Expected: %d, Actual: %d",
        token, expected, actual));
    this.expected = expected;
    this.actual = actual;
  }
}