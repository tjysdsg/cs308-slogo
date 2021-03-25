package slogo.model.parser.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import slogo.records.ParserRecord;
import slogo.model.parser.handlers.Handler;

/**
 * The Handler factory is used to encapsulate the creation of the handlers.
 *
 * This class assumes that the handlers are in the same package as the handler class.
 *
 * This class depends on the java reflection, records, and handler package.
 *
 * This class is used by instantiating it with a parser record. The buildHandler can then be used
 * to create a handler that can be invoked
 *
 * @author Oliver Rodas
 */
public class HandlerFactory {
  private static final String PACKAGE = Handler.class.getPackageName() + ".";
  private static final String SUFFIX = "Handler";
  private ParserRecord parserInfo;

  /**
   * Instantiates a new Handler factory.
   *
   * @param parserInfo the parser info
   */
  public HandlerFactory(ParserRecord parserInfo) {
    this.parserInfo = parserInfo;
  }

  /**
   * Build a handler of the specified type.
   *
   * @param type the type of handler
   * @return the handler of the type
   */
  public Handler buildHandler(String type) {
    Class clazz = null;

    try {
      clazz = Class.forName(PACKAGE + type + SUFFIX);
    } catch (ClassNotFoundException e) {
      System.out.println("DEBUG: Syntax Type Not Implemented");
    }

    Constructor constructor = null;
    try {
      assert clazz != null;
      constructor = clazz.getConstructor(ParserRecord.class);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }

    try {
      assert constructor != null;
      return (Handler) constructor.newInstance(parserInfo);
    } catch ( IllegalAccessException | InvocationTargetException | InstantiationException e) {
      e.printStackTrace();
    }

    return null;
  }
}
