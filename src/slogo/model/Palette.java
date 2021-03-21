package slogo.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Palette implements PaletTable, Serializable {

  private final Map<Integer, Color> colors = new HashMap<>();
  private static final Color DEFAULT_COLOR = new Color(0, 0, 0);

  public void setColor(int idx, Color color) {
    colors.put(idx, color);
  }

  public Color getColor(int idx) {
    return colors.getOrDefault(idx, DEFAULT_COLOR);
  }
}
