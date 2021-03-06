package slogo.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class takes user input and allows running commands
 * from file input.
 */
public class CommandPane extends Pane {

  ViewController vcon;
  GridPane pane = new GridPane();
  private TextArea codeArea;
  private Button run;
  private Button uploadToRun;
  private Button uploadToTextArea;
  ResourceBundle resources;
  VBox vbox;
  FileChooser fileChooser = new FileChooser();

  public CommandPane(ViewController viewcon) {
    vcon = viewcon;
    makeBottomPane();
    setChildrenIDs();
    getChildren().add(pane);
    setChildrenIDs();
    pane.setTranslateX(300);
  }

  private Pane makeBottomPane() {
    codeArea = new TextArea();

    run = new Button();
    uploadToRun = new Button();
    uploadToTextArea = new Button();
    run.setOnMouseClicked(e -> sendCodeArea());
    fileChooser
        .getExtensionFilters()
        .addAll(
            new ExtensionFilter("Slogo files", "*.slogo", "*.logo"),
            new ExtensionFilter("All files", "*.*"));
    uploadToRun.setOnMouseClicked(
        e -> {
          File file = fileChooser.showOpenDialog(getScene().getWindow());

          if (file != null) {
            try {
              Scanner fileReader = new Scanner(file);
              fileReader.useDelimiter("\\Z");
              String command = fileReader.next();
              vcon.sendCommand(command);

            } catch (FileNotFoundException fileNotFoundException) {
              fileNotFoundException.printStackTrace();
            }
          }
        });
    uploadToTextArea.setOnMouseClicked(
        e -> {
          File file = fileChooser.showOpenDialog(getScene().getWindow());

          if (file != null) {
            try {
              Scanner fileReader = new Scanner(file);
              fileReader.useDelimiter("\\Z");
              String command = fileReader.next();
              fillCodeArea(command);

            } catch (FileNotFoundException fileNotFoundException) {
              fileNotFoundException.printStackTrace();
            }
          }
        });

    codeArea.setOnKeyPressed(
        e -> {
          if (e.getCode() == KeyCode.ENTER && e.isShiftDown()) {
            sendCodeArea();
          } else if (codeArea.getText().equals("") && e.getCode() == KeyCode.UP) {
            // fix this.
            vcon.fillCommandArea("");
          }
        });

    vbox = new VBox(run, uploadToRun, uploadToTextArea);
    vbox.setSpacing(10);

    pane.add(codeArea, 0, 0);
    pane.add(vbox, 1, 0);
    return pane;
  }

  private void sendCodeArea() {
    String command = codeArea.getText();
    vcon.sendCommand(command);
    codeArea.clear();
  }

  private void setChildrenIDs() {
    run.setId("runButton");
    codeArea.setId("codeArea");
  }

  /** Sets the language of the components */
  public void setResources(ResourceBundle resource) {
    this.resources = resource;
    createDisplayText();
  }

  private void createDisplayText() {
    codeArea.setPromptText(resources.getString("userCommand"));
    run.setText(resources.getString("runButton"));
    uploadToRun.setText(resources.getString("uploadFile"));
    uploadToTextArea.setText(resources.getString("uploadToText"));
  }

  /**
   * Fills the command text area with the specified string.
   */
  public void fillCodeArea(String text) {
    codeArea.setText(text);
  }
  ;
}
