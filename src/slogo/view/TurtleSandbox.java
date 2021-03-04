package slogo.view;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import slogo.events.TurtleRecord;

/**
 * @author marthaaboagye
 * @author Joshua Pettima
 *     <p>This class creates the window where the turtle moves. It extends the Pane class so that
 *     the turtle box and the status bar are both displayed when the simulation starts.
 */
public class TurtleSandbox extends StackPane {
  private Queue<Animation> animationQueue;
  private ImageView turtle;

  /** Constructor for TurtleSandbox. Intializes the pan class. */
  public TurtleSandbox() {
    super();
    File file = new File("data/images/logo.png");
    Image image = new Image(file.toURI().toString());
    turtle = new ImageView(image);
    animationQueue = new LinkedList<>();
    setSandboxColor("#03A9F4");
    getChildren().add(turtle);

    createMockData();
  }

  private void addTurtle() {}

  private void createMockData() {
    updateTurtle(new TurtleRecord(0, 0, 0, 180));
    updateTurtle(new TurtleRecord(0, 0, -100, 0));
    updateTurtle(new TurtleRecord(0, 0 , 0, -90));
    updateTurtle(new TurtleRecord(0, 200, 0, 0));
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
    if (turtle.getRotate() != info.rotation()) {
      RotateTransition rt = new RotateTransition(Duration.seconds(1), turtle);
      rt.setByAngle(turtle.getRotate() - info.rotation());
      addAnimation(rt);
    }
    double tx = turtle.getTranslateX();
    double ty = turtle.getTranslateY();
    if (tx != info.xCoord() || ty != info.yCoord()) {
      TranslateTransition moveTurtle = new TranslateTransition();
      moveTurtle.setDuration(Duration.seconds(1));

    if (tx != info.xCoord()) {
      moveTurtle.setToX(tx - info.xCoord());
    } else {
      moveTurtle.setToY(ty - info.yCoord());
    }

    moveTurtle.setNode(turtle);
    addAnimation(moveTurtle);
    }
  }
}
