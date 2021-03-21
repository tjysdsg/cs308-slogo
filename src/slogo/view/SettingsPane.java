package slogo.view;

import java.util.prefs.Preferences;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

/**
 * @author marthaaboagye
 * @author Joshua Pettima This class creates all the elements of the Gui that can be customized by
 *     the user. It sets up the status bar where users can maninpulate aspects of the the gui such
 *     as the display language, the turtle image, the color of the pen and the background image.
 */
public class SettingsPane extends Pane {
  public static final int SIZE = 1000;
  private final ChoiceBox<String> languageList = new ChoiceBox();
  private final ChoiceBox<String> turtleList = new ChoiceBox();

  private HBox hbox;
  private ColorPicker backgroundColorPicker;
  private ColorPicker penColorPicker;
  private Label penColorLabel;
  private Label backgroundColorLabel;
  private Label title;
  private GridPane penPane;
  private GridPane backgroundPane;
  private ResourceBundle resources;
  private Button uploadTurtle;
  private FileChooser fileChooser;
  private File userFile;
  private Node node;
  private Preferences settings;
  private ViewController vcon;
  private PopupSettings popupSettings;

  /**
   * This method intializes a viewController object which defines method for aspects of the GUI to
   * be changed dynamically when the user changes them during the game.
   *
   * @param vcon
   */
  public SettingsPane(ViewController vcon, Preferences settings) {
    this.vcon = vcon;
    createHbox();
    getChildren().add(hbox);
    this.settings = settings;
  }

  private void createHbox() {
    title = new Label();
    createLanguageList();
    createPenAndBackground();
    createTurtleOptions();
    createTurtleUpload();
    popupSettings = new PopupSettings(settings);
    hbox = new HBox(title, languageList, penPane, backgroundPane, popupSettings);
    hbox.setAlignment(Pos.CENTER);
    hbox.setSpacing(20);
    hbox.setMinWidth(SIZE);
    setID();
  }

  private void createTurtleUpload() {
    uploadTurtle = new Button();
    fileChooser = new FileChooser();

    uploadTurtle.setOnAction(
        e -> {
          node = (Node) e.getSource();
          userFile = fileChooser.showOpenDialog(node.getScene().getWindow());
          vcon.setTurtleLogo(userFile.toString());
          if (userFile == null) {
            System.out.println("empty file");
          }
        });
  }

  private void createPenAndBackground() {
    penPane = new GridPane();
    penColorLabel = new Label();
    penColorPicker = new ColorPicker();
    penColorPicker.setOnAction(
        e -> {
          vcon.setPenColor(penColorPicker.getValue().toString());
        });

    penPane.add(penColorLabel, 0, 0);
    penPane.add(penColorPicker, 1, 0);
    penPane.setHgap(10);
    penPane.setAlignment(Pos.CENTER);
    backgroundColorLabel = new Label();
    backgroundColorPicker = new ColorPicker();

    backgroundColorPicker.setOnAction(
        e -> {
          vcon.setBackground(backgroundColorPicker.getValue().toString());
        });

    backgroundPane = new GridPane();
    backgroundPane.setAlignment(Pos.CENTER);
    backgroundPane.add(backgroundColorLabel, 0, 0);
    backgroundPane.add(backgroundColorPicker, 1, 0);
    backgroundPane.setHgap(10);
  }

  private void createTurtleOptions() {
    List<String> turtles = Arrays.asList("Happy", "Sad", "Angry", "Rainbow", "White");
    turtleList.getItems().addAll(turtles);
    turtleList.setValue("TurtleLogo");
    turtleList.setOnAction(
        (e -> {
          vcon.setTurtleLogo(turtleList.getValue().toString());
        }));
  }

  private void createLanguageList() {
    List<String> languages =
        Arrays.asList(
            "English",
            "Chinese",
            "French",
            "German",
            "Italian",
            "Portuguese",
            "Russian",
            "Spanish",
            "Urdu");
    languageList.getItems().addAll(languages);
    languageList.setValue("English");
    languageList.setOnAction(
        e -> {
          vcon.setLanguage(languageList.getValue());
        });
  }

  protected void setSetting(Preferences settings) {
    this.settings = settings;
  }

  public void setResources(ResourceBundle resource) {
    this.resources = resource;
    popupSettings.setResources(resource);
    displayLabels();
  }


  private void displayLabels() {
    title.setText(resources.getString("title"));
    penColorLabel.setText(resources.getString("penColorLabel"));
    backgroundColorLabel.setText(resources.getString("backgroundColorLabel"));
    uploadTurtle.setText(resources.getString("turtleUpload"));
  }

  private void setID() {
    penColorPicker.setId("penColor");
    title.setId("guiname");
    penColorLabel.setId("penColorText");
    backgroundColorPicker.setId("backgroundColor");
    languageList.setId("languagePicker");
  }
}
