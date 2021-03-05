package slogo.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import slogo.events.TurtleRecord;

/**
 * @author marthaaboagye
 * @author Joshua Pettima
 *     <p>This class creates the window where the turtle moves. It extends the Pane class so that
 *     the turtle box and the status bar are both displayed when the simulation starts.
 */
public class TurtleSandbox extends BorderPane {
  private List<TurtleView> turtles;
  private StackPane lines;
  private StackPane sandbox;
  private String penStyle = "#009624";

  /** Constructor for TurtleSandbox. Intializes the pan class. */
  public TurtleSandbox() {
    turtles = new ArrayList<>();
    lines = new StackPane();
    sandbox = new StackPane();
    sandbox.getChildren().add(lines);
    addTurtle();
    //getChildren().addAll(sandbox);
    setCenter(sandbox);
    setSandboxColor("#03A9F4");
    createMockData();
  }

  private void addTurtle() {
    TurtleView turtle = new TurtleView();
    turtles.add(turtle);
    sandbox.getChildren().addAll(turtle);
  }

  private void createMockData() {
    List<TurtleRecord> demos = new ArrayList<>();
    demos.add(new TurtleRecord(0, 0, 100, 0)); // Up 100
    demos.add(new TurtleRecord(0, 0, 100, -120)); // Flip Left
    demos.add(new TurtleRecord(0, 0, 130, -120)); // Flip Left
    demos.add(new TurtleRecord(0, 50, 150, -140)); // Flip Left
    demos.add(new TurtleRecord(0, 100, 100, -90)); // Left 100
    demos.add(new TurtleRecord(0, 100, 100, 0)); // Rotate Up
    demos.add(new TurtleRecord(0, 100, 200, 0)); // Up 100
    demos.add(new TurtleRecord(0, 100, 200, 0));
    demos.add(new TurtleRecord(0, 200, 200, -90)); // Rotate Left
    demos.add(new TurtleRecord(0, 200, 200, -180)); // Rotate down
    demos.add(new TurtleRecord(0, 200, 100, -180));
    demos.add(new TurtleRecord(0, 200, 100, 90));
    demos.add(new TurtleRecord(0, 100, 100, 90));
    demos.add(new TurtleRecord(0, 300, 400, 90));
    Button button = new Button("DEMO");
    button.setOnAction(
        (e) -> {
          if (demos.size() > 0) {
            updateTurtle(demos.remove(0));
          }
        });
    sandbox.getChildren().addAll(button);
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
      line.setTranslateX(-1 * tx);
      line.setTranslateY(-1 * info.yCoord());
      if (tx != info.xCoord()) {
        line.setTranslateX(-1 * tx / 2 - info.xCoord() / 2);
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
