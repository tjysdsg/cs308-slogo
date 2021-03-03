package slogo.events;

/**
 * Displayable variables are used to communicate the value of a variable within the environment.
 */
public record DisplayVariable(String name,String value){
  public static final String NAME = "name";
  public static final String VALUE = "value";

  public String getName() {
    return name;
  }
  public String getValue() {
    return value;
  }
}
