package slogo.model.parser;
import slogo.model.ASTNodes.*;
import slogo.exceptions.*;

/**
 * The parser interface parses a given command and creates the ASTNode Tree
 *
 * This interface assumes that the parent class will catch the errors it throws and that
 * the resource bundles exist depicting the syntax and languages to use in the parser
 *
 * This class depends on ASTNodes and the Exceptions package
 *
 * This class must be used with a concrete class. The parent class can use the parse command
 * to convert a string to an AST. The parent can also change the language used to parse commands, allowing
 * many different languages to be parsed.
 *
 * @author Oliver Rodas
 */
public interface Parser {
  /**
   * This method parses a string to create the ASTNode Tree.
   *
   * @param command the string to parse
   *
   * @return The ASTNode created by the command
   *
   * @throws UnknownIdentifierException If a command that has not been defined is used
   * @throws InvalidSyntaxException If an unknown token or a token out of order has been used
   * @throws IncorrectParameterCountException If a token expects at least n inputs and fewer are given
   * @throws FloatingValueException If a constant or variable is used without a parent in the outer scope
   * @throws InvalidTokenTypeException If a token is expected, but it not given
   * @throws NotEnoughTokensException If a token is expected, but none are given
   * @throws UnmatchedBracketException If a scope is not ended, started by a ( or [
   */
  ASTNode parseCommand(String command)
      throws UnknownIdentifierException, InvalidSyntaxException, IncorrectParameterCountException,
        FloatingValueException, InvalidTokenTypeException, NotEnoughTokensException, UnmatchedBracketException;

  /**
   * Change the language of the parser to accept a new set of tokens
   * @param language
   */
  void changeLanguage(String language);
}
