package slogo.view;

import java.util.prefs.Preferences;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
  private static final String FONT_SIZE_ICON = "resources/images/fontsizeicon.png";
  private static final String TURTLE_ICON = "resources/images/logo.png";
  private static final String THEME_ICON = "resources/images/themeicon.png";
  private static final String PREFERENCES_ICON = "resources/images/preficon.png";
  private static final String BACK_ICON = "resources/images/backicon.png";
  private VBox vboxContainer;
  private HBox titlePane;
  private HBox settingsBox;
  private ChoiceBox<String> fontPicker;
  private ChoiceBox<String> themePicker;
  private ChoiceBox<String> turtlePicker;
  private TextField fontSizePicker;
  private ResourceBundle resources;
  private ImageView backButton;
  private ImageView prefImage;
  private ViewController vcon;
  private Label fontLabel = new Label();
  private Label fontSizeLabel = new Label();
  private Label themeLabel = new Label();
  private Label turtleLabel = new Label();
  private Preferences settings;

  public PopupSettings(Preferences settings, ViewController vcon) {
    this.settings = settings;
    this.vcon = vcon;
    createPopup();
    getChildren().add(gearImage);
  }

  //TODO: I THINK THIS IS WHERE JOSH PLACES THE
  //GLOBAL PREFERENCES THING
  //
  //IT IS!
  private void createPreferencesAction() {
    fontPicker.setOnAction(e -> {
      String font = fontPicker.getSelectionModel().getSelectedItem();
      if (font != null) {
        settings.put("font",font);
        vcon.setFont(font);
      }
    });
    turtlePicker.setOnAction(e -> {
      String logo =  turtlePicker.getSelectionModel().getSelectedItem();
      if (logo != null) {
        settings.put("turtleLogo", logo);
        vcon.setTurtleLogo(logo);
      }
    });
    themePicker.setOnAction(e -> {
      String theme =  themePicker.getSelectionModel().getSelectedItem();
      if (theme != null) {
        settings.put("theme", theme);
      }
    });
    fontSizePicker.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        String font = fontSizePicker.getText();
        vcon.setFontSize(Integer.parseInt(font));
      }
    });

  }

  protected void setSetting(Preferences settings) {
    this.settings = settings;
  }

  private void setIconSize() {
    gearImage.setFitWidth(ICON_SIZE);
    gearImage.setFitHeight(ICON_SIZE);
    fontImage.setFitWidth(2 * ICON_SIZE);
    fontImage.setFitHeight(ICON_SIZE);
    fontSizeImage.setFitWidth(2 * ICON_SIZE);
    fontSizeImage.setFitHeight(ICON_SIZE);
    turtleImage.setFitWidth(2 * ICON_SIZE);
    turtleImage.setFitHeight(ICON_SIZE);
    themeImage.setFitWidth(2 * ICON_SIZE);
    themeImage.setFitHeight(ICON_SIZE);
    prefImage.setFitHeight(ICON_SIZE);
    prefImage.setFitWidth(ICON_SIZE);
    backButton.setFitHeight(50);
    backButton.setFitWidth(ICON_SIZE);
  }

  private void createImageIcon() {
    gearImage = new ImageView(new Image(getClass().getResourceAsStream(GEAR_ICON)));
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
    createTitlePane();
    createIconAction();
    createVboxContainer();
    createPreferencesAction();
    setIconSize();
    setChildrenIds();
    popup = new Popup();
    popup.getContent().addAll(vboxContainer);
    popup.setAutoHide(true);
  }

  private void setChildrenIds() {
    vboxContainer.setId("verticalContainer");
    titlePane.setId("titlePane");
    setId("popupSettings");
    backButton.setId("backButton");
  }

  private void createVboxContainer() {
    vboxContainer = new VBox(titlePane, settingsBox);
    vboxContainer.setMinSize(WINDOW_SIZE, WINDOW_SIZE);
  }

  private void createDisplayIcons() {
    fontImage = new ImageView(new Image(getClass().getResourceAsStream(FONT_ICON)));
    fontSizeImage = new ImageView(new Image(getClass().getResourceAsStream(FONT_SIZE_ICON)));
    turtleImage = new ImageView(new Image(getClass().getResourceAsStream(TURTLE_ICON)));
    themeImage = new ImageView(new Image(getClass().getResourceAsStream(THEME_ICON)));
    settingsBox = new HBox(fontImage, fontSizeImage, turtleImage, themeImage);
    settingsBox.setMinWidth(WINDOW_SIZE);
    settingsBox.setSpacing(ICON_SIZE);
  }

  private void createTitlePane() {
    Label title = new Label("System Preferences");
    title.setId("settingsTitle");
    prefImage = new ImageView(new Image(getClass().getResourceAsStream(PREFERENCES_ICON)));
    backButton = new ImageView(new Image(getClass().getResourceAsStream(BACK_ICON)));
    backButton.setDisable(true);
    titlePane = new HBox(prefImage, title, backButton);
  }

  private void createIconAction() {
    setAction(fontImage, fontPicker);
    setAction(fontSizeImage, fontSizePicker);
    setAction(turtleImage, turtlePicker);
    setAction(themeImage, themePicker);
    backButton.setOnMouseClicked(e -> {
      settingsBox.getChildren().clear();
      settingsBox.getChildren().addAll(fontImage, fontSizeImage, turtleImage, themeImage);
    });
  }

  private void setAction(ImageView fontImage, Node fontPicker) {
    fontImage.setOnMouseClicked(e -> {
      settingsBox.getChildren().clear();
      settingsBox.getChildren().add(fontPicker);
      backButton.setDisable(false);
    });
  }

  private void createSettingsBox() {
    fontPicker = new ChoiceBox<>();
    fontSizePicker = new TextField();
    themePicker = new ChoiceBox<>();
    turtlePicker = new ChoiceBox<>();
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
    turtlePicker.setValue(resources.getString("turtleText"));
    turtlePicker.getItems().addAll(resources.getString("turtleList").split(","));

  }

}
