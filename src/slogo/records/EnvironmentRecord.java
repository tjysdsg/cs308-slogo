package slogo.records;

import java.util.List;
import slogo.model.PaletTable;

public record EnvironmentRecord(
    PaletTable colors,
    int currPenColor, int currShape,
    int currBGColor,
    List<Integer> activeTurtles, double currPenSize) {
}
