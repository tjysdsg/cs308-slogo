package slogo.exceptions;

/**
 * This class is used to have an abstract exception object that can be used to display exceptions in any language using any format for its children.
 * @author Oliver Rodas
 */
public abstract class ModelException extends RuntimeException {

  /**
   * Create a new runtime exception whose message is the name
   * @param name the name of the exception
   */
  public ModelException(String name) {
    super(name);
  }

  /**
   *
   * @param format the format of the string to use
   * @return the string of the exception message
   */
  public abstract String buildException(String format);
}
