package slogo.view;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javafx.scene.image.ImageView;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import slogo.Main;

public class PopupSettingsTest extends util.DukeApplicationTest {

  private PopupSettings popupSettings;
  private ImageView gearImage;
  private Main myApp;
  private Popup popup;

  @Override
  public void start(Stage stage) {
    myApp = new Main();
    myApp.start(stage);
    popupSettings = lookup("#popupSettings").query();

  }

  @Test
  void testBackButton(){
    assertNotNull(popupSettings);
  }



}
