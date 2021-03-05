package slogo.events;

/**
 * The state of a turtle at a specific instance.
 */
public record TurtleRecord(int id, double prevX, double prevY, double prevRot, double xCoord, double yCoord, double rotation) {

}
