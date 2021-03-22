package slogo.records;

import slogo.model.PaletTable;

public record EnvironmentRecord(
    PaletTable colors,
    int currPenColor, int currShape,
    int currBGColor,
    int mainTurtle, double currPenSize) {
}
