package slogo.view;

import java.util.ResourceBundle;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class PopupSettings extends Pane {

  private Popup popup;
  private ImageView gearImage;
  private static final int SIZE = 50;
  private static final String GEAR_ICON = "resources/images/gearicon.png";
  private VBox settingsBox;
  private ChoiceBox<String> fontPicker;
  private ChoiceBox<String> themePicker;
  private TextField fontSizePicker;
  private ResourceBundle resources;


  public PopupSettings() {
    createPopup();
    createImageIcon();
    getChildren().add(gearImage);
  }

  private void createImageIcon() {
    gearImage = new ImageView(new Image(getClass().getResourceAsStream(GEAR_ICON)));
    gearImage.setFitWidth(SIZE);
    gearImage.setFitHeight(SIZE);
    gearImage.setOnMouseClicked(e -> {
          if (!popup.isShowing()) {
            Node source = (Node) e.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            popup.show(stage);
            Point2D point = source.localToScene(0, 0);
            popup.setX(stage.getX() + point.getX());
            popup.setY(stage.getY() + point.getY() + 80);
          }
        }
    );
  }
  private void createPopup() {
    createSettingsBox();
    popup = new Popup();
    popup.getContent().add(settingsBox);
    popup.setAutoHide(true);

  }

  private void createSettingsBox() {
    fontPicker = new ChoiceBox<>();
    fontPicker.setPrefSize(2 * SIZE, SIZE);
    fontSizePicker = new TextField();
    fontSizePicker.setPrefSize(2 * SIZE, SIZE);
    themePicker = new ChoiceBox<>();
    themePicker.setPrefSize(2 * SIZE, SIZE);
    settingsBox = new VBox(fontPicker, themePicker, fontSizePicker);

  }


  public void setResources(ResourceBundle resource) {
    this.resources = resource;
    createDisplayText();

  }

  private void createDisplayText() {
    fontSizePicker.setPromptText(resources.getString("fontSize"));
    fontPicker.setValue(resources.getString("fontPicker"));
    fontPicker.getItems().clear();
    fontPicker.getItems().addAll(resources.getString("fontOptions").split(","));
    themePicker.setValue(resources.getString("themePicker"));
    themePicker.getItems().clear();
    themePicker.getItems().addAll(resources.getString("themeOptions").split(","));

  }


}