package slogo.events;

@FunctionalInterface
public interface UpdateEnvironment {
  void execute(EnvironmentRecord e);
}
