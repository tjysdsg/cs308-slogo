package slogo.model;

import java.util.Map;

public class Palette {

  private Map<Integer, Color> colors;
  private static final Color DEFAULT_COLOR = new Color(0, 0, 0);

  public void setColor(int idx, Color color) {
    colors.put(idx, color);
  }

  public Color getColor(int idx) {
    return colors.getOrDefault(idx, DEFAULT_COLOR);
  }
}
