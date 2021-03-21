package slogo.events;

import slogo.model.Palette;

public record EnvironmentRecord(
    Palette colors,
    int currPenColor, int currShape,
    int currBGColor, int currPenSize) {
}
