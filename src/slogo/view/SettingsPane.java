package slogo.view;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;

/**
 * @author marthaaboagye
 * @author Joshua Pettima
 *This class creates all the elements of the Gui that can be customized by the
 * user. It sets up the status bar where users can maninpulate aspects of the
 * the gui such as the display language, the turtle image, the color of the pen 
 * and the background image. 
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
  GridPane penPane;
  GridPane backgroundPane;
  ResourceBundle resources;
  private Button uploadTurtle;
  FileChooser fileChooser;
  File userFile;
  Node node;

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
    createHbox();
    getChildren().add(hbox);
}

  private void createHbox(){
    title = new Label();
    createLanguageList();
    createPenAndBackground();
    createTurtleOptions();
    createTurtleUpload();
    hbox = new HBox(title, languageList, penPane,
            backgroundPane);
    hbox.setAlignment(Pos.CENTER);
    displayLabels("English");
    hbox.setSpacing(20);
    hbox.setMinWidth(SIZE);
    setID();
  }

  private void createTurtleUpload() {
    uploadTurtle = new Button();
    fileChooser = new FileChooser();

    uploadTurtle.setOnAction(e-> {
      node = (Node) e.getSource();
      userFile = fileChooser.showOpenDialog(node.getScene().getWindow());
      System.out.println(userFile.toString());
      vcon.setTurtleLogo(userFile.toString());
      if (userFile == null){
        System.out.println("empty file");
      }
    });


  }

  private void createPenAndBackground() {
    penPane = new GridPane();
    penColorLabel = new Label();
    penColorPicker = new ColorPicker();
    penColorPicker.setOnAction(e-> {
      vcon.setPenColor(penColorPicker.getValue().toString());

    });

    penPane.add(penColorLabel, 0, 0);
    penPane.add(penColorPicker, 1,0);
    penPane.setHgap(10);
    penPane.setAlignment(Pos.CENTER);
    backgroundColorLabel =  new Label();
    backgroundColorPicker = new ColorPicker();

    backgroundColorPicker.setOnAction( e -> {
      vcon.setBackground(backgroundColorPicker.getValue().toString());
    });

    backgroundPane = new GridPane();
    backgroundPane.setAlignment(Pos.CENTER);
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
    uploadTurtle.setText(resources.getString("turtleUpload"));

  }

  private void setID(){
    penColorPicker.setId("penColor");
    title.setId("guiname");
    penColorLabel.setId("penColorText");
    backgroundColorPicker.setId("backgroundColor");
    languageList.setId("languagePicker");

  }


  }
  

