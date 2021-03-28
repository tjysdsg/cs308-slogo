package slogo.view;

import java.io.File;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import slogo.records.TurtleRecord;

/**
 * A graphical representation of a turtle.
 *
 * <p>This object details where an internal turtle is currently located and holds the meta data
 * associated with that turtle such as pen color, thickness, and turtle image.
 */
public class TurtleView extends Group {
  private double currX;
  public static final double ANIMATION_SPEED = 10;
  public static final double IMAGE_WIDTH = 200;
  public static final double IMAGE_HEIGHT = 160;
  public static final int PANE_HEIGHT = 200;
  public static final String TURTLES = "resources/images/turtles/";
  private double currY;
  private double rotation;
  private double penThickness;
  private int id;
  private ImageView turtleImage;
  private Queue<Animation> animationQueue;
  private String penColor;
  private Pane trackerPane;
  private Label nameLabel;
  private Label penOnLabel;
  private Label penColorLabel;
  private Label positionLabel;
  private Label rotationLabel;
  private Label penThicknessLabel;
  private Label idLabel;
  private boolean penDown;

  protected TurtleView(Image image, int id) {
    turtleImage = new ImageView(image);
    getChildren().addAll(turtleImage);
    this.currX = 0;
    this.currY = 0;
    this.nameLabel = new Label("Turtle :)");
    this.positionLabel = new Label();
    this.rotationLabel = new Label();
    updateLocationLabels();
    this.penDown = true;
    this.animationQueue = new LinkedList<>();
    this.penColor = "#009624";
    this.penThickness = 5;
    this.id = id;
    HBox labelBox = new HBox();
    createTrackerPane();
    labelBox.setAlignment(Pos.CENTER_RIGHT);
    labelBox.setSpacing(IMAGE_WIDTH / 7);
    labelBox.getChildren().addAll(nameLabel, rotationLabel, positionLabel);
    trackerPane.getStyleClass().add("turtle-tracker");
    trackerPane.setMinWidth(IMAGE_WIDTH);
    trackerPane.setMinHeight(IMAGE_HEIGHT);

    updateTracker();
    trackerPane.setOnMouseClicked(e -> trackerPane.setVisible(false));
    getChildren().addAll(labelBox, trackerPane);
    setID();
    setupContextMenu();
  }

  public void createTrackerPane() {
    trackerPane = new Pane();
    trackerPane.setVisible(false);
    VBox items = new VBox();
    penOnLabel = new Label();
    penColorLabel = new Label();
    penThicknessLabel = new Label();
    idLabel = new Label("ID: " + this.id);

    items.setSpacing(IMAGE_WIDTH / 10);
    items.getChildren().addAll(penOnLabel, penColorLabel, penThicknessLabel, idLabel);
    trackerPane.getChildren().add(items);
    Insets insets = new Insets(10);
    items.setPadding(insets);
  }

  public TurtleView(int id) {
    this(new Image(new File("data/images/logo.png").toURI().toString()), id);
  }

  private void setPenThinkcess(double thickness) {
    this.penThickness = thickness;
    updateTracker();
  }

  private void setupContextMenu() {
    ContextMenu menu = new ContextMenu();
    MenuItem setPen = new MenuItem("Set Pen Color");
    MenuItem setImage = new MenuItem("Set Turtle Image");
    MenuItem setName = new MenuItem("Set Turtle Name");
    MenuItem openTracker = new MenuItem("More...");
    menu.getItems().addAll(setPen, setImage, setName, openTracker);
    ColorPicker picker = new ColorPicker();
    FileChooser fileChooser = new FileChooser();
    fileChooser
        .getExtensionFilters()
        .addAll(
            new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
            new ExtensionFilter("All Files", "*.*"));

    picker.setVisible(false);
    getChildren().add(picker);
    picker.setOnAction(
        (event) -> {
          setPenColor("#" + picker.getValue().toString().substring(2));
          picker.setVisible(false);
        });
    turtleImage.setOnContextMenuRequested(
        e -> menu.show(turtleImage, e.getScreenX(), e.getScreenY()));

    setPen.setOnAction(
        e -> {
          picker.setVisible(true);
          picker.show();
        });
    setImage.setOnAction(
        e -> {
          File file = fileChooser.showOpenDialog(getScene().getWindow());
          if (file != null) {
            turtleImage.setImage(
                new Image(file.toURI().toString(), IMAGE_WIDTH, IMAGE_HEIGHT, false, false));
          }
        });
    setName.setOnAction(
        e -> {
          TextInputDialog nameDialog = new TextInputDialog();
          nameDialog.setTitle("Turtle Name");
          nameDialog.setHeaderText("Enter Turtle Name");
          Optional<String> res = nameDialog.showAndWait();
          if (res.isPresent()) {
            nameLabel.setText(res.get());
          }
        });
    openTracker.setOnAction(e -> trackerPane.setVisible(true));
  }

  /** The current X position of the turtle. */
  public double getCurrX() {
    return this.currX;
  }

  /** The current Rotation of the turtle. */
  public double getCurrRot() {
    return this.rotation;
  }

  /** The current Y position of the turtle. */
  public double getCurrY() {
    return this.currY;
  }

  /** The current pen color of the turtle. */
  public String getPenColor() {
    return this.penColor;
  }

  /** Whether the turtle's pen is down. */
  public boolean isPenDown() {
    return this.penDown;
  }

  /** The current pen thickness for the turtle. */
  public double getPenThickness() {
    return this.penThickness;
  }

  /** Set the HEX code value for the pen's color */
  public void setPenColor(String penColor) {
    this.penColor = penColor;
    updateTracker();
  }

  /** Set the image for the current turtle. */
  public void setImage(String image) {
    if (image.equals("")) return;
    Image imageFile =
        new Image(
            getClass().getResourceAsStream(TURTLES + image),
            IMAGE_WIDTH,
            IMAGE_HEIGHT,
            false,
            false);
    turtleImage.setImage(imageFile);
  }

  /** Update the location, rotation, and pen status of the turtle. */
  public void update(TurtleRecord info) {
    penDown = info.penDown();
    turtleImage.setVisible(info.visible());
    updateTracker();
    if (getCurrRot() != info.rotation()) {
      RotateTransition rt = new RotateTransition(Duration.millis(ANIMATION_SPEED), turtleImage);
      rt.setByAngle(info.rotation() - getCurrRot());
      this.rotation = info.rotation();
      addAnimation(rt);
    }
    double tx = getCurrX();
    double ty = getCurrY();
    if (tx != info.xCoord() || ty != info.yCoord()) {
      TranslateTransition moveTurtle = new TranslateTransition();
      moveTurtle.setDuration(Duration.millis(ANIMATION_SPEED));
      if (tx != info.xCoord()) {
        moveTurtle.setToX(info.xCoord());
      }
      if (ty != info.yCoord()) {
        moveTurtle.setToY(-info.yCoord());
      }
      moveTurtle.setNode(this);
      addAnimation(moveTurtle);
      this.currX = info.xCoord();
      this.currY = info.yCoord();
    }
  }

  private void updateTracker() {
    penOnLabel.setText("Pen Down: " + penDown);
    penColorLabel.setText("Pen Color: " + penColor);
    penThicknessLabel.setText("Pen Thickness: " + penThickness);
  }

  private void addAnimation(Animation an) {
    animationQueue.add(an);
    an.setOnFinished(
        e -> {
          if (animationQueue.peek() == an) {
            animationQueue.poll();
          }
          if (animationQueue.size() > 0) {
            animationQueue.poll().play();
          }
          updateLocationLabels();
        });
    if (animationQueue.size() == 1) {
      an.play();
    }
  }

  private void updateLocationLabels() {
    int x = (int) getTranslateX();
    int y = (int) getTranslateY();
    int rotation = (int) turtleImage.getRotate();
    positionLabel.setText(String.format("%d, %d", x, -y));
    rotationLabel.setText(String.format("%d\u00B0", rotation));
  }

  private void setID() {
    turtleImage.setId("turtleImage");
  }
}
