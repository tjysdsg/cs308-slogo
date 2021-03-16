package slogo.view;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Tooltip;
import java.io.File;
import javafx.scene.layout.Priority;
import java.io.IOException;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import java.util.List;
import javafx.animation.TranslateTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import slogo.events.TurtleRecord;

/**
 * @author marthaaboagye
 * @author Joshua Pettima
 *     <p>This class creates the window where the turtle moves. It extends the Pane class so that
 *     the turtle box and the status bar are both displayed when the simulation starts.
 */
public class TurtleSandbox extends GridPane {
  public static final double MAX_ZOOM = 5;
  public static final double MIN_ZOOM = .2;
  public static final double ZOOM_INTENSITY = .05;
  public static final int DEFAULT_SIZE = 300;
  private List<TurtleView> turtles;
  private StackPane lines;
  private StackPane sandbox;
  private HBox controls;
  private double dragX;
  private double dragY;
  private ViewController viewController;

  /** Constructor for TurtleSandbox. Intializes the pan class. */
  public TurtleSandbox(ViewController viewController) {
    this.turtles = new ArrayList<>();
    this.lines = new StackPane();
    this.sandbox = new StackPane();
    this.viewController = viewController;
    this.controls = createControls();
    sandbox.getChildren().add(lines);
    setSandboxColor("#03A9F4");
    getChildren().addAll(sandbox, controls);
    addTurtle();

    controls.setAlignment(Pos.BOTTOM_LEFT);

    RowConstraints row1 = new RowConstraints(DEFAULT_SIZE);
    row1.setPercentHeight(95);
    row1.setValignment(VPos.CENTER);
    row1.setVgrow(Priority.NEVER);

    RowConstraints row2 = new RowConstraints();
    row2.setValignment(VPos.BOTTOM);
    row2.setVgrow(Priority.ALWAYS);

    getRowConstraints().addAll(row1, row2);

    ColumnConstraints col1 = new ColumnConstraints(DEFAULT_SIZE);
    col1.setPercentWidth(100);
    col1.setHalignment(HPos.CENTER);
    col1.setHgrow(Priority.NEVER);
    getColumnConstraints().addAll(col1);

    GridPane.setRowIndex(sandbox, 0);
    GridPane.setColumnIndex(sandbox, 0);

    GridPane.setRowIndex(controls, 1);
    GridPane.setColumnIndex(controls, 0);
    setAlignment(Pos.CENTER);
    makeDraggable(sandbox);
    setID();
  }

  private void setID() {

  }

  private void makeDraggable(Pane pane) {
    setOnMousePressed(
        e -> {
          dragX = e.getX();
          dragY = e.getY();
        });
    setOnMouseDragged(
        e -> {
          double newX = pane.getTranslateX() + (e.getX() - dragX);
          double newY = pane.getTranslateY() + (e.getY() - dragY);
          pane.setTranslateX(newX);
          pane.setTranslateY(newY);
          dragX = e.getX();
          dragY = e.getY();
        });
    setOnScroll( e -> {
      if (e.getDeltaY() == 0) return;
      double scale = 1;
      if (e.getDeltaY() < 0) {
        scale = sandbox.getScaleX() - ZOOM_INTENSITY;
        if (scale < MIN_ZOOM) scale = MIN_ZOOM;
      } else {
        scale = sandbox.getScaleX() + ZOOM_INTENSITY;
        if (scale > MAX_ZOOM) scale = MAX_ZOOM;
      }
        sandbox.setScaleX(scale);
        sandbox.setScaleY(scale);
    });
  }

  private HBox createControls() {
    HBox controls = new HBox();
    controls.setSpacing(5);
    controls.setStyle("-fx-background-color: rgba(95, 84, 87, 0.74)");

    Button addTurtle = createControlButton("Add Turtle");
    addTurtle.setOnAction(
        e -> {
          addTurtle();
        });
    Tooltip addTip = new Tooltip("Select turtle with Shift+Click");
    addTip.setShowDelay(Duration.millis(200));
    addTurtle.setTooltip(addTip);
    Button centerButton = createControlButton("Center");

    TranslateTransition centerSandbox = new TranslateTransition();
    centerSandbox.setDuration(Duration.seconds(1));
    centerSandbox.setToX(0);
    centerSandbox.setToY(0);
    centerSandbox.setNode(sandbox);

    ScaleTransition scaleCenter = new ScaleTransition();
    scaleCenter.setToX(1);
    scaleCenter.setToY(1);
    scaleCenter.setNode(sandbox);
    scaleCenter.setDuration(Duration.seconds(1));
    ParallelTransition center = new ParallelTransition(centerSandbox, scaleCenter);

    centerButton.setOnAction(
        (e) -> {
          center.play();
        });
    Button saveImage = createControlButton("Save");
    saveImage.getStyleClass().add("control-button");
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png"));
    saveImage.setOnAction(
        (e) -> {
          WritableImage wi = new WritableImage((int) sandbox.getWidth(), (int) sandbox.getHeight());
          snapshot(null, wi);
          File file = fileChooser.showSaveDialog(getScene().getWindow());
          if (file == null) return;
          try {
            ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", file);
          } catch (IOException uhoh) {
            uhoh.printStackTrace();
          }
        });
    controls.getChildren().addAll(addTurtle, centerButton, saveImage);
    return controls;
  }

  private Button createControlButton(String name) {
    JFXButton button = new JFXButton(name);
    button.getStyleClass().add("control-button");
    button.setMaxHeight(Double.MAX_VALUE);
    button.setMaxWidth(Double.MAX_VALUE);
    return button;
  }

  private void addTurtle() {
    TurtleView turtle = new TurtleView();
    turtles.add(turtle);
    if (turtles.size() > 1) viewController.addTurtle();
    turtle.setOnMouseClicked( e -> {
      if (e.isShiftDown()) {
        setTurtle(turtles.indexOf(turtle));
      }
    });
    turtle.getStyleClass().add("turtle");
    setTurtle(turtles.size() - 1);
    sandbox.getChildren().addAll(turtle);
  }

  public void setTurtle(int index) {
      viewController.setCurrTurtle(index);
      for (TurtleView turtle : turtles) {
        turtle.setStyle("-fx-opacity: .5");
      }
      turtles.get(index).setStyle("-fx-opacity: 1");
  }

  public void setSandboxColor(String color) {
    setStyle("-fx-background-color: " + color);
  }

  public void clearLines() {
    lines.getChildren().clear();
  }

  public void setPenColor(String color) {
    turtles.get(0).setPenColor(color);
  }

  /**
   * This method updates turtles position after the user command is executed on the backend.
   *
   * @param info
   */
  public void updateTurtle(TurtleRecord info) {
    TurtleView turtle = turtles.get(info.id());
    double tx = turtle.getCurrX();
    double ty = turtle.getCurrY();
    turtle.update(info);
    if (info.penDown() && (tx != info.xCoord() || ty != info.yCoord())) {
      Line line = new Line();
      line.setStyle("-fx-stroke:" + turtle.getPenColor());
      line.setTranslateX(1 * tx);
      line.setTranslateY(-1 * info.yCoord());
      if (tx != info.xCoord()) {
        line.setTranslateX(1 * info.xCoord() / 2 + tx / 2);
      }
      if (ty != info.yCoord()) {
        line.setTranslateY(-1 * info.yCoord() / 2 - ty / 2);
      }
      line.setStartX(-tx);
      line.setStartY(ty);
      line.setEndX(-info.xCoord());
      line.setEndY(info.yCoord());
      line.setStrokeWidth(2);
      lines.getChildren().addAll(line);
    }
  }
}
