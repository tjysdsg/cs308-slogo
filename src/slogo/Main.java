package slogo;
import javafx.application.Application;
import slogo.view.ModelController;
import slogo.view.View;

import javafx.stage.Stage;

public class Main extends Application {

  public void start(Stage primaryStage) {
      ModelController con = new ModelController();
      View view = new View(primaryStage, con);
      primaryStage.show();
  }

  public double getVersion () {
    return 0.001;
  }
  public static void main(String[] args) {
    launch(args);
  }
}