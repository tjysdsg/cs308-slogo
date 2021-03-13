package slogo.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.Main;
import org.junit.jupiter.api.Test;

/**
 *  Tests for SettingsPane
 */
public class SettingsPaneTest extends util.DukeApplicationTest {
  private Pane settingsPane;
  private Main myApp;
  private ColorPicker penPicker;
  private ColorPicker backgroundPicker;
  private ChoiceBox<String> languagePicker;
  private Label title;

  @Override
  public void start(Stage stage) {
    myApp = new Main();
    myApp.start(stage);
    settingsPane = lookup("#settingsPane").query();

  }


  @Test
  void testPenColorPicker(){

    penPicker = (ColorPicker) settingsPane.lookup("#penColor");
    Color[] colorList = {Color.RED, Color.YELLOW, Color.ANTIQUEWHITE, Color.DARKSLATEBLUE};

    for (Color expected: colorList) {
      sleep(1, TimeUnit.SECONDS);
      setValue(penPicker, expected);
      assertEquals(penPicker.getValue(), expected);
    }

  }

  @Test
  void testBackgroundColorPicker(){
    backgroundPicker = (ColorPicker) settingsPane.lookup("#backgroundColor");
    Color[] colorList = {Color.RED, Color.YELLOW, Color.ANTIQUEWHITE, Color.DARKSLATEBLUE};

    for (Color expected: colorList) {
      sleep(1, TimeUnit.SECONDS);
      setValue(backgroundPicker, expected);
      assertEquals(backgroundPicker.getValue(), expected);
    }

  }

  @Test
  void testLanguageAction(){
    languagePicker = (ChoiceBox<String>) settingsPane.lookup("#languagePicker");
    String[] languages = {"English", "Italian", "French", "Spanish"};
    String[] titleNames = {"Logo Interpreter", "Interprete del logo", "Interpreteur de logo", "interprete de logotipos"};
    for (int i=0; i<languages.length; i++){
      select(languagePicker, languages[i]);
      title = (Label) settingsPane.lookup("#guiname");
      assertEquals(languages[i], languagePicker.getValue());
      assertEquals(title.getText(), titleNames[i]);
    }

  }







}
