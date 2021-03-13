package slogo.view;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.Main;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.TimeUnit;
import util.DukeApplicationTest;

/**
 * Test for Turtle SandBox
 */

public class TurtleSandBoxTest  extends DukeApplicationTest{
  private Main myApp;
  private Pane turtleSandBox;

 @Override
  public void start(Stage stage){
   myApp = new Main();
   myApp.start(stage);
   turtleSandBox = lookup("#turtlePane").query();

 }


}
