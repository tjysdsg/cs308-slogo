package slogo.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.TranslateTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
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
public class TurtleSandbox extends BorderPane {
  public static final double MAX_ZOOM = 3;
  public static final double MIN_ZOOM = .3;
  public static final double ZOOM_INTENSITY = .05;
  private List<TurtleView> turtles;
  private StackPane lines;
  private StackPane sandbox;
  private HBox controls;
  private double dragX;
  private double dragY;

  /** Constructor for TurtleSandbox. Intializes the pan class. */
  public TurtleSandbox() {
    turtles = new ArrayList<>();
    lines = new StackPane();
    sandbox = new StackPane();
    sandbox.getChildren().add(lines);
    addTurtle();
    setCenter(sandbox);
    setSandboxColor("#03A9F4");
    makeDraggable(sandbox);
    createControls();
  }

  private void makeDraggable(Pane pane) {
    pane.setOnMouseEntered(
        e -> {
          // pane.
        });
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

  private void createControls() {
    controls = new HBox();
    Button addTurtle = new Button("Add Turtle");
    addTurtle.setOnAction(
        e -> {
          addTurtle();
        });
    Button centerButton = new Button("Center");
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
    Button saveImage = new Button("Save");
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
    setBottom(controls);
  }

  private void addTurtle() {
    TurtleView turtle = new TurtleView();
    turtles.add(turtle);
    sandbox.getChildren().addAll(turtle);
  }

  public void setSandboxColor(String color) {
    setStyle("-fx-background-color: " + color);
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
    if (tx != info.xCoord() || ty != info.yCoord()) {
      Line line = new Line();
      line.setStyle("-fx-stroke:" + turtle.getPenColor());
      line.setTranslateX(1 * tx);
      line.setTranslateY(-1 * info.yCoord());
      if (tx != info.xCoord()) {
        line.setTranslateX(1 * tx / 2 + info.xCoord() / 3);
      }
      if (ty != info.yCoord()) {
        line.setTranslateY(-1 * info.yCoord() / 2 - ty / 2);
      }

      line.setStartX(tx);
      line.setStartY(ty);
      line.setEndX(info.xCoord());
      line.setEndY(info.yCoord());
      line.setStrokeWidth(7);
      lines.getChildren().addAll(line);
    }
    turtle.update(info);
  }
}
