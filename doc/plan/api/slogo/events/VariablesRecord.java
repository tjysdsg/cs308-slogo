package slogo.events;

import java.util.List;

/**
 * Information on the variables within the environment at a specific instance.
 */
public record VariablesRecord(List<DisplayVariable> variables) {

}
