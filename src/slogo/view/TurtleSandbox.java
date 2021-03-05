package slogo.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ContextMenu;
import javafx.scene.shape.Line;
import slogo.events.TurtleRecord;

/**
 * @author marthaaboagye
 * @author Joshua Pettima
 *     <p>This class creates the window where the turtle moves. It extends the Pane class so that
 *     the turtle box and the status bar are both displayed when the simulation starts.
 */
public class TurtleSandbox extends StackPane {
  private List<Turtle> turtles;
  private StackPane lines;
  private String penStyle = "#009624";

  /** Constructor for TurtleSandbox. Intializes the pan class. */
  public TurtleSandbox() {
    turtles = new ArrayList<>();
    lines = new StackPane();
    getChildren().addAll(lines);
    addTurtle();
    ContextMenu menu = new ContextMenu();

    setSandboxColor("#03A9F4");
    createMockData();
  }

  private void addTurtle() {
    Turtle turtle = new Turtle();
    turtles.add(turtle);
    getChildren().addAll(turtle);
  }

  private void createMockData() {
    List<TurtleRecord> demos = new ArrayList<>();
    demos.add(new TurtleRecord(0, 0, 100, 0)); // Up 100
    demos.add(new TurtleRecord(0, 0, 100, -90)); // Flip Left
    demos.add(new TurtleRecord(0, 100, 100, -90)); // Left 100
    demos.add(new TurtleRecord(0, 100, 100, 0)); // Rotate Up
    demos.add(new TurtleRecord(0, 100, 200, 0)); // Up 100
    demos.add(new TurtleRecord(0, 100, 200, 0)); // Flip
    demos.add(new TurtleRecord(0, 200, 200, -90)); // Rotate Left
    demos.add(new TurtleRecord(0, 200, 200, -180)); // Rotate down 
    demos.add(new TurtleRecord(0, 200, 100, -180)); // Flip
    demos.add(new TurtleRecord(0, 200, 100, 90)); // Flip
    demos.add(new TurtleRecord(0, 100, 100, 90)); // Flip
    Button button = new Button("DEMO");
    button.setOnAction(
        (e) -> {
          if (demos.size() > 0) {
            updateTurtle(demos.remove(0));
          }
        });
    getChildren().addAll(button);
  }

  public void setSandboxColor(String color) {
    setStyle("-fx-background-color: " + color);
  }

  public void setPenColor(String color) {
    penStyle = color;
  }

  /**
   * This method updates turtles position after the user command is executed on the backend.
   *
   * @param info
   */
  public void updateTurtle(TurtleRecord info) {
    Turtle turtle = turtles.get(info.id());
    double tx = turtle.getCurrX();
    double ty = turtle.getCurrY();
    if (tx != info.xCoord() || ty != info.yCoord()) {
      Line line = new Line();
      line.setStyle("-fx-stroke:" + penStyle);
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
