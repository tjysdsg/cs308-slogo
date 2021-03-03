package slogo.events;

import java.util.List;

/**
 * Contains the state of commands within an environment at a specific instance in time.
 */
public record CommandsRecord(List<DisplayCommand> commands) {

}
