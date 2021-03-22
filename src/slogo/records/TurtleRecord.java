package slogo.records;

/**
 * The state of a turtle at a specific instance.
 */
public record TurtleRecord(
    int id, double xCoord, double yCoord, double rotation, boolean visible,
    boolean penDown) {

}
