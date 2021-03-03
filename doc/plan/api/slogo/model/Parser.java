package slogo.model;
import slogo.model.ASTNodes.*;
import slogo.exceptions.*;

/**
 * The parser interface parses a given command and creates the ASTNode Tree
 */
public interface Parser {

  /**
   * This method parses a string to create the ASTNode Tree.
   *
   * @param command the string to parse
   * @return The ASTNode created by the command
   * @throws UnknownCommandException
   * @throws InvalidSyntaxException
   * @throws IncorrectParameterCountException
   */
  public static ASTNode parseCommand(String command) throws UnknownCommandException, InvalidSyntaxException, IncorrectParameterCountException {
    return null;
  }
}
