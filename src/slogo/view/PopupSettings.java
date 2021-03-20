package slogo.view;

import java.awt.Rectangle;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
  private static final int WINDOW_SIZE_WIDTH = 500;
  private static final int WINDOW_SIZE_HEIGHT = 600;

  private static final String GEAR_ICON = "resources/images/gearicon.png";
  private static final String FONT_ICON = "resources/images/fonticon.png";
  private static final String FONT_SIZE_ICON = "resources/images/fontsizeicon.png";
  private static final String TURTLE_ICON = "resources/images/logo.png";
  private static final String THEME_ICON = "resources/images/themeicon.png";
  private static final String PREFERENCES_ICON= "resources/images/preficon.png";
  private static final String BACK_ICON= "resources/images/backicon.png";
  private static final String BAKCGROUND_COLOR = "#4C5151";
  private VBox container;
  private HBox titlePane;
  private HBox settingsBox;
  private ChoiceBox<String> fontPicker;
  private ChoiceBox<String> themePicker;
  private ChoiceBox<String> turtlePicker;
  private TextField fontSizePicker;
  private ResourceBundle resources;
  private ImageView backButton;


  public PopupSettings() {
    createPopup();
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
    createImageIcon();
    setIconSize();
    createTitlePane();
    createIconAction();
    container= new VBox(titlePane,settingsBox);
    container.setMinSize(WINDOW_SIZE_WIDTH, WINDOW_SIZE_WIDTH);
    container.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    popup = new Popup();
    popup.getContent().addAll(container);
    popup.setAutoHide(true);

  }

  private void createDisplayIcons() {
    fontImage = new ImageView(new Image(getClass().getResourceAsStream(FONT_ICON)));
    fontSizeImage= new ImageView(new Image(getClass().getResourceAsStream(FONT_SIZE_ICON)));
    turtleImage=new ImageView(new Image(getClass().getResourceAsStream(TURTLE_ICON)));
    themeImage = new ImageView(new Image(getClass().getResourceAsStream(THEME_ICON)));
    settingsBox = new HBox(fontImage, fontSizeImage, turtleImage, themeImage);
    settingsBox.setMinWidth(WINDOW_SIZE_WIDTH);
    settingsBox.setSpacing(50);
  }

  private void createTitlePane() {
    Label title = new Label("System Preferences");
    title.setId("settingsTitle");
    ImageView prefImage = new ImageView(new Image(getClass().getResourceAsStream(PREFERENCES_ICON)));
    backButton = new ImageView(new Image(getClass().getResourceAsStream(BACK_ICON)));
    prefImage.setFitHeight(50);
    prefImage.setFitWidth(50);
    backButton.setFitHeight(50);
    backButton.setFitWidth(50);
    backButton.setDisable(true);
    titlePane= new HBox(prefImage, title, backButton);
    titlePane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

  }

  private void createIconAction() {
    setaction(fontImage, fontPicker);
    setaction(fontSizeImage, fontSizePicker);
    setaction(turtleImage, turtlePicker);
    setaction(themeImage, themePicker);

    backButton.setOnMouseClicked(e->{
      settingsBox.getChildren().clear();
      settingsBox.getChildren().addAll(fontImage,fontSizeImage, turtleImage, themeImage);

    });

  }

  private void setaction(ImageView fontImage, Node fontPicker) {
    fontImage.setOnMouseClicked(e->{
      settingsBox.getChildren().clear();
      settingsBox.getChildren().add(fontPicker);
      backButton.setDisable(false);

    });


  }


  private void createSettingsBox() {
    fontPicker = new ChoiceBox<>();
    fontPicker.setPrefSize(2 * ICON_SIZE, 2*ICON_SIZE);
    fontSizePicker = new TextField();
    fontSizePicker.setPrefSize(2 * ICON_SIZE, 2*ICON_SIZE);
    themePicker = new ChoiceBox<>();
    themePicker.setPrefSize(2 * ICON_SIZE, 2*ICON_SIZE);
    turtlePicker= new ChoiceBox<>();
    turtlePicker.setPrefSize(2 * ICON_SIZE, 2*ICON_SIZE);



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
