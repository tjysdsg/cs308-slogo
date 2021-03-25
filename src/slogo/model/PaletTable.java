package slogo.model;

/**
 * Read-only palette table
 *
 * @author Oliver Rodas
 */
public interface PaletTable {

  /**
   * Get the color at given color idx
   */
  Color getColor(int idx);
}
