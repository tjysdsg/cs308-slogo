package slogo.model.parser.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import slogo.records.ParserRecord;
import slogo.model.parser.handlers.Handler;

public class HandlerFactory {
  private static final String PACKAGE = Handler.class.getPackageName() + ".";
  private static final String SUFFIX = "Handler";
  private ParserRecord parserInfo;

  public HandlerFactory(ParserRecord parserInfo) {
    this.parserInfo = parserInfo;
  }

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