package slogo.model.parser;
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
   * @throws UnknownIdentifierException
   * @throws InvalidSyntaxException
   * @throws IncorrectParameterCountException
   */
  public ASTNode parseCommand(String command) throws UnknownIdentifierException, InvalidSyntaxException, IncorrectParameterCountException;

  public void changeLanguage(String language);
}
