package slogo.events;

/**
 * The state of a command within the environment at a specific instance.
 */
public record DisplayCommand(String name,String signature){
  public String getName() {
    return name;
  }

  public String getSignature() {
    return signature;
  }
}
