package slogo.model.parser.handlers;

/**
 * The Handler interface is meant to handle a variety of different kinds of symbols. I used
 * an interface because while all handlers have this one public method, they can have drastically
 * different implementations.
 *
 * This method assumes that a handler for a type of symbol is defined
 *
 * This particular class does not depend on anything
 *
 * By instantiating this class and calling the handle method, the action will be done
 *
 * @author Oliver Rodas
 */
public interface Handler {

  /**
   * Handle the specified token.
   *
   * @param currentToken the current token
   */
  void handle(
      String currentToken);
}
