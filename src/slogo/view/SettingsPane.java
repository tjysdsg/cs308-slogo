package slogo.view;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * @author marthaaboagye
 * @author Joshua Pettima
 *This class creates all the elements of the Gui that can be customized by the
 * user. It sets up the status bar where users can maninpulate aspects of the
 * the gui such as the display language, the turtle image, the color of the pen 
 * and the background image. 
 */

public class SettingsPane extends Pane {
  public static final int SIZE = 700;
  private final ChoiceBox<String> languageList = new ChoiceBox();
  private final ChoiceBox<String> turtleList = new ChoiceBox();

  private HBox hbox;
  private ColorPicker backgroundColorPicker;
  private ColorPicker penColorPicker;
  private Label penColorLabel;
  private Label backgroundColorLabel;
  private Label title;
  GridPane penPane;
  GridPane backgroundPane;
  ResourceBundle resources;

  ViewController vcon;

  public static final String RESOURCE_PACKAGE = "slogo.view.resources.";
  //public static final String RESOURCE_FOLDER = "/" + RESOURCE_PACKAGE.replace(".", "/");

  /**
   * This method intializes a viewController object which defines method
   * for aspects of the GUI to be
   * changed dynamically when the user changes them during the game.
   *
   * @param vcon
   */
  public  SettingsPane(ViewController vcon) {
    this.vcon = vcon;
}

  public Pane createSettingsPane() {
    createHbox();
    return hbox;

  }

  private void createHbox(){
    title = new Label();
    title.setId("guiname");
    createLanguageList();
    createPenAndBackground();
    createTurtleOptions();
    hbox = new HBox(title, languageList,  turtleList, penPane,
            backgroundPane);
    displayLabels("English");
    hbox.setSpacing(20);
    hbox.setId("settingsPane");

  }

  private void createPenAndBackground() {
    penPane = new GridPane();
    penColorLabel = new Label();
    penColorLabel.setId("penColorText");
    penColorPicker = new ColorPicker();
    penColorPicker.setOnAction(e-> {
      vcon.setBackground(backgroundColorPicker.getValue().toString());

    });

    penPane.add(penColorLabel, 0, 0);
    penPane.add(penColorPicker, 1,0);
    penPane.setHgap(10);
    backgroundColorLabel =  new Label();
    backgroundColorPicker = new ColorPicker();

    backgroundColorPicker.setOnAction( e -> {
      vcon.setPenColor(backgroundColorPicker.getValue().toString());
    });

    backgroundPane = new GridPane();
    backgroundPane.add(backgroundColorLabel, 0, 0);
    backgroundPane.add(backgroundColorPicker, 1, 0);
    backgroundPane.setHgap(10);

  }

  private void createTurtleOptions() {
    List<String> turtles =  Arrays.asList("Happy", "Sad", "Angry", "Rainbow", "White");
    turtleList.getItems().addAll(turtles);
    turtleList.setValue("TurtleLogo");
    turtleList.setOnAction((e->{
      vcon.setTurtleLogo(turtleList.getValue().toString());
    }));
  }

  private  void createLanguageList(){
    List<String> languages =  Arrays.asList("English", "Chinese", "French", "German",
        "Italian", "Portuguese", "Russian", "Spanish", "Urdu");
    languageList.getItems().addAll(languages);
    languageList.setValue("English");
    languageList.setOnAction(e->{
      displayLabels(languageList.getValue().toString());
      vcon.setLanguage(languageList.getValue().toString());


    });

  }

  private void displayLabels(String language){
    resources = ResourceBundle.getBundle(RESOURCE_PACKAGE + language);
    title.setText(resources.getString("title"));
    penColorLabel.setText(resources.getString("penColorLabel"));
    backgroundColorLabel.setText(resources.getString("backgroundColorLabel"));

  }


  }
  

