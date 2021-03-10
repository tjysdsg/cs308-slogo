package slogo.view;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Optional;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.Group;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import slogo.events.TurtleRecord;
import javafx.scene.control.TextInputDialog;

public class TurtleView extends Group {
  private double currX;
  public static double ANIMATION_SPEED = 10;
  private double currY;
  private double rotation;
  private ImageView turtleImage;
  private Queue<Animation> animationQueue;
  private String penColor;
  private Label name;
  private boolean penDown;


  protected TurtleView(Image image) {
    turtleImage = new ImageView(image);
    getChildren().addAll(turtleImage);
    this.currX = 0;
    this.currY = 0;
    this.name = new Label("Turtle :)");
    this.penDown = true;
    getChildren().add(name);
    this.animationQueue = new LinkedList<>();
    this.penColor = "#009624";
    setupContextMenu();
  }

  public TurtleView() {
    this(new Image(new File("data/images/logo.png").toURI().toString()));
  }

  public void setupContextMenu() {
    ContextMenu menu = new ContextMenu();
    MenuItem setPen = new MenuItem("Set Pen Color");
    MenuItem setImage = new MenuItem("Set Turtle Image");
    MenuItem setName = new MenuItem("Set Turtle Name");
    menu.getItems().addAll(setPen, setImage, setName);
    ColorPicker picker = new ColorPicker();
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
        new ExtensionFilter("All Files", "*.*")
        );

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
            turtleImage.setImage(new Image(file.toURI().toString(), 200, 160, false, false));
          }
        });
    setName.setOnAction( e -> {
      TextInputDialog nameDialog = new TextInputDialog();
      nameDialog.setTitle("Turtle Name");
      nameDialog.setHeaderText("Enter Turtle Name");
      Optional<String> res = nameDialog.showAndWait();
      if (res.isPresent()) {
        name.setText(res.get());
      }
    });
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

  public boolean isPenDown() {
    return this.penDown;
  }

  public void setPenColor(String penColor) {
    this.penColor = penColor;
  }

  public void update(TurtleRecord info) {
    penDown = info.penDown();
    turtleImage.setVisible(info.visible());
    if (getCurrRot() != info.rotation()) {
      RotateTransition rt = new RotateTransition(Duration.millis(ANIMATION_SPEED), turtleImage);
      rt.setByAngle(info.rotation() - getCurrRot());
      //turtleImage.setRotate(info.rotation() - getCurrRot());
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
        //turtleImage.setTranslateX(-info.xCoord());
      }
      if (ty != info.yCoord()) {
        moveTurtle.setToY(-info.yCoord());
        //turtleImage.setTranslateY(-info.yCoord());
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
