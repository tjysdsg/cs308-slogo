package slogo.model;

/**
 * Creates a trackable environment.
 */
public class EnvironmentFactory {

  /**
   * Create a trackable environment
   * @return A trackable environment.
   */
  public static TrackableEnvironment createEnvironment() {
    return new Environment();
  }
}