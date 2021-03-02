package slogo.view;

import javafx.scene.layout.Pane;

/**
 *This class creates all the elements of the Gui that can be customized by the
 * user. It sets up the status bar where users can maninpulate aspects of the
 * the gui such as the display language, the turtle image, the color of the pen 
 * and the background image. 
 */

public class SettingsPane extends Pane {

  ViewController vcon;

  /**
   * This method intializes a viewController object which defines method
   * for aspects of the GUI to be
   * changed dynamically when the user changes them during the game.
   *
   * @param vcon
   */
  public SettingsPane(ViewController vcon) {
    this.vcon = vcon;
  }
}
  

