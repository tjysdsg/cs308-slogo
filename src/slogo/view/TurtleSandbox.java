package slogo.view;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import slogo.events.TurtleRecord;
import javafx.application.Platform;

/**
 * @author marthaaboagye
 * @author Joshua Pettima
 *     <p>This class creates the window where the turtle moves. It extends the Pane class so that
 *     the turtle box and the status bar are both displayed when the simulation starts.
 */
public class TurtleSandbox extends StackPane {
  private Queue<Animation> animationQueue;
  private double ANIMATION_SPEED = 500;
  private ImageView turtle;
  private StackPane lines;
  // temp until moved to record
  private double tx;
  private double ty;
  private double rot;

  /** Constructor for TurtleSandbox. Intializes the pan class. */
  public TurtleSandbox() {
    super();
    File file = new File("data/images/logo.png");
    Image image = new Image(file.toURI().toString());
    turtle = new ImageView(image);
    animationQueue = new LinkedList<>();
    lines = new StackPane();
    setSandboxColor("#03A9F4");
    getChildren().addAll(lines, turtle);

    createMockData();
  }

  private void addTurtle() {}

  private void createMockData() {
    LinkedList<TurtleRecord> demos = new LinkedList<>(); // lol cuz it's already imported
    demos.add(new TurtleRecord(0, 0, 100, 0)); // Down 100
    demos.add(new TurtleRecord(0, 0, 100, -90)); // Flip
    demos.add(new TurtleRecord(0, 100, 100, -90)); // Right 100
    demos.add(new TurtleRecord(0, 100, 100, 0)); // Rotate Forward
    demos.add(new TurtleRecord(0, 100, 200, 0)); // Up 100
    demos.add(new TurtleRecord(0, 100, 200, 0)); // Flip
    demos.add(new TurtleRecord(0, 200, 200, -90)); // Flip
    demos.add(new TurtleRecord(0, 100, 200, -180)); // Flip
    demos.add(new TurtleRecord(0, 200, 200, -180)); // Flip
    demos.add(new TurtleRecord(0, 200, 100, -180)); // Flip
    demos.add(new TurtleRecord(0, 200, 0, -180));
    demos.add(new TurtleRecord(0, 200, 0, -90));
    demos.add(new TurtleRecord(0, 0, 0, 90));
    Button button = new Button("DEMO");
    button.setOnAction( (e) -> { 
      if (demos.size() > 0) {
        updateTurtle(demos.remove(0));
      }
    });
    getChildren().addAll(button);
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
        });
    if (animationQueue.size() == 1) {
      an.play();
    }
  }

  public void setSandboxColor(String color) {
    setStyle("-fx-background-color: " + color);
  }

  /**
   * This method updates turtles position after the user command is executed on the backend.
   *
   * @param info
   */
  public void updateTurtle(TurtleRecord info) {
    if (rot != info.rotation()) {
      RotateTransition rt = new RotateTransition(Duration.millis(ANIMATION_SPEED), turtle);
      rt.setByAngle(rot - info.rotation());
      rot = info.rotation();
      addAnimation(rt);
    }
    if (tx != info.xCoord() || ty != info.yCoord()) {
      TranslateTransition moveTurtle = new TranslateTransition();
      moveTurtle.setDuration(Duration.millis(ANIMATION_SPEED));

      Line line = new Line();
      if (tx != info.xCoord()) {
        moveTurtle.setToX(info.xCoord());
        line.setTranslateX(tx / 2 + info.xCoord() / 2);
        line.setTranslateY(-1 * info.yCoord());
      } else if (ty != info.yCoord()) {
        moveTurtle.setToY(-info.yCoord());
        line.setTranslateX(tx);
        line.setTranslateY(-1 * info.yCoord() / 2 - ty / 2);
      }
      line.setStartX(tx);
      line.setStartY(ty);
      line.setEndX(info.xCoord());
      line.setEndY(info.yCoord());

      line.setStrokeWidth(7);

      lines.getChildren().addAll(line);

      moveTurtle.setNode(turtle);
      addAnimation(moveTurtle);
      tx = info.xCoord();
      ty = info.yCoord();
    }
  }
}
