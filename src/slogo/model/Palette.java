package slogo.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Color palette
 *
 * @author Jiyang Tang, Oliver Rodas
 */
public class Palette implements PaletTable, Serializable {

  private final Map<Integer, Color> colors = new HashMap<>();
  private static final Color DEFAULT_COLOR = new Color(0, 0, 0);

  /**
   * Set the color at given color idx
   */
  public void setColor(int idx, Color color) {
    colors.put(idx, color);
  }

  /**
   * Get the color at given color idx
   */
  public Color getColor(int idx) {
    return colors.getOrDefault(idx, DEFAULT_COLOR);
  }
}
