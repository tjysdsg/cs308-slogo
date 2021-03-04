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
    setStyle("-fx-background-color: #03A9F4");
    getChildren().add(turtle);

    createMockData();
  }

  private void addTurtle() {}

  private void createMockData() {
    TranslateTransition moveTurtle = new TranslateTransition();
    moveTurtle.setDuration(Duration.seconds(2));
    moveTurtle.setToX(200);
    moveTurtle.setToY(200);
    moveTurtle.setNode(turtle);
    addAnimation(moveTurtle);

    moveTurtle = new TranslateTransition();
    moveTurtle.setDuration(Duration.seconds(2));
    moveTurtle.setNode(turtle);
    moveTurtle.setToX(-100);
    moveTurtle.setToY(-100);
    addAnimation(moveTurtle);

    RotateTransition rt = new RotateTransition(Duration.seconds(1), turtle);
    rt.setByAngle(180);
    addAnimation(rt);

    moveTurtle = new TranslateTransition();
    moveTurtle.setDuration(Duration.seconds(2));
    moveTurtle.setNode(turtle);
    moveTurtle.setToY(100);
    addAnimation(moveTurtle);
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

  /**
   * This method updates turtles position after the user command is executed on the backend.
   *
   * @param info
   */
  public void updateTurtle(TurtleRecord info) {}
}
