package slogo.view;

import java.awt.Rectangle;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class PopupSettings extends Pane {

  private Popup popup;
  private ImageView gearImage;
  private ImageView fontImage;
  private ImageView fontSizeImage;
  private ImageView turtleImage;
  private ImageView themeImage;
  private static final int ICON_SIZE = 50;
  private static final int WINDOW_SIZE = 500;
  private static final String GEAR_ICON = "resources/images/gearicon.png";
  private static final String FONT_ICON = "resources/images/fonticon.png";
  private static final String FONT_SIZE_ICON = "resources/images/fontsizeicon.jpg";
  private static final String TURTLE_ICON = "resources/images/logo.png";
  private static final String THEME_ICON = "resources/images/themeicon.jpg";
  private HBox settingsBox;
  private ChoiceBox<String> fontPicker;
  private ChoiceBox<String> themePicker;
  private ChoiceBox<String> turtlePicker;
  private TextField fontSizePicker;
  private ResourceBundle resources;


  public PopupSettings() {
    createPopup();
    createImageIcon();
    setIconSize();
    getChildren().add(gearImage);
  }

  private void setIconSize() {
    gearImage.setFitWidth(ICON_SIZE);
    gearImage.setFitHeight(ICON_SIZE);
    fontImage.setFitWidth(2*ICON_SIZE);
    fontImage.setFitHeight(ICON_SIZE);
    fontSizeImage.setFitWidth(2*ICON_SIZE);
    fontSizeImage.setFitHeight(ICON_SIZE);
    turtleImage.setFitWidth(2*ICON_SIZE);
    turtleImage.setFitHeight(ICON_SIZE);
    themeImage.setFitWidth(2*ICON_SIZE);
    themeImage.setFitHeight(ICON_SIZE);

  }

  private void createImageIcon() {
    gearImage = new ImageView(new Image(getClass().getResourceAsStream(GEAR_ICON)));
    gearImage.setFitWidth(ICON_SIZE);
    gearImage.setFitHeight(ICON_SIZE);
    gearImage.setOnMouseClicked(e -> {
          if (!popup.isShowing()) {
            Node source = (Node) e.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            popup.show(stage);
            popup.centerOnScreen();

          }
        }
    );
  }
  private void createPopup() {
    createDisplayIcons();
    createSettingsBox();
    popup = new Popup();
    popup.getContent().add(settingsBox);
    popup.setAutoHide(true);

  }

  private void createDisplayIcons() {
    fontImage = new ImageView(new Image(getClass().getResourceAsStream(FONT_ICON)));
    fontSizeImage= new ImageView(new Image(getClass().getResourceAsStream(FONT_SIZE_ICON)));
    turtleImage=new ImageView(new Image(getClass().getResourceAsStream(TURTLE_ICON)));
    themeImage = new ImageView(new Image(getClass().getResourceAsStream(THEME_ICON)));
    createIconAction();
    settingsBox = new HBox(fontImage, fontSizeImage, turtleImage, themeImage);
    settingsBox.setMinSize(WINDOW_SIZE,WINDOW_SIZE);
    settingsBox.setBackground(new Background(new BackgroundFill(Color.HONEYDEW, CornerRadii.EMPTY, Insets.EMPTY)));

  }

  private void createIconAction() {
    fontImage.setOnMouseClicked(e->{
      settingsBox.getChildren().clear();
      settingsBox.getChildren().add(fontPicker);

    });

    fontSizeImage.setOnMouseClicked(e->{
      settingsBox.getChildren().clear();
      settingsBox.getChildren().add(fontSizePicker);

    });

    turtleImage.setOnMouseClicked(e->{
      settingsBox.getChildren().clear();
      settingsBox.getChildren().add(fontPicker);

    });
  }


  private void createSettingsBox() {
    fontPicker = new ChoiceBox<>();
    fontPicker.setPrefSize(2 * ICON_SIZE, ICON_SIZE);
    fontSizePicker = new TextField();
    fontSizePicker.setPrefSize(2 * ICON_SIZE, ICON_SIZE);
    themePicker = new ChoiceBox<>();
    themePicker.setPrefSize(2 * ICON_SIZE, ICON_SIZE);
    turtlePicker= new ChoiceBox<>();
    turtlePicker.setPrefSize(2 * ICON_SIZE, ICON_SIZE);



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
    turtlePicker.getItems().clear();
    turtlePicker.getItems().addAll(resources.getString("turtleList").split(","));

  }


}
