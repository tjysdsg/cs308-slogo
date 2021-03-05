package slogo.view;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import slogo.events.TurtleRecord;

public class TurtleView extends ImageView {
  private double currX;
  public static double ANIMATION_SPEED = 200;
  private double currY;
  private double rotation;
  private Queue<Animation> animationQueue;
  private String penColor;

  protected TurtleView(Image image) {
    super(image);
    this.currX = 0;
    this.currY = 0;
    this.animationQueue = new LinkedList<>();
    this.penColor = "#009624";
  }

  public TurtleView() {
    this(new Image(new File("data/images/logo.png").toURI().toString()));
  }

  public double getCurrX() {
    return this.currX;
  }

  public double getCurrRot() {
    return this.rotation;
  }

  public double getCurrY() {
    return this.currY;
  }

  public String getPenColor() {
    return this.penColor;
  }

  public void setPenColor(String penColor) {
    this.penColor = penColor;
  }

  public void update(TurtleRecord info) {
    if (getCurrRot() != info.rotation()) {
      RotateTransition rt = new RotateTransition(Duration.millis(ANIMATION_SPEED), this);
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
        moveTurtle.setToX(-info.xCoord());
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

  public void addAnimation(Animation an) {
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
}
