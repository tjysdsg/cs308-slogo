package slogo.view;

import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.model.EnvironmentFactory;
import slogo.model.TrackableEnvironment;

/**
 * @author Joshua Pettima
 * @author marthaaboagye This class coordinates between all other classes from the view package. It
 *     needs to be initialized with a stage and a model controller object. It also contains a
 *     private View bundle class that implements the view controller and allows the user to change
 *     the color for the pen and background.
 */
public class View {
  private static final int WIDTH = 1200;
  private static final int HEIGHT = 720;

  private ModelController modelCon;
  private TrackableEnvironment environment;
  private ViewController viewCon;
  private EnvironmentPane environmentPane;
  private HelpPane helpPane;
  private TurtleSandbox turtleSandbox;
  private SettingsPane settingsPane;
  private Pane commandPane;
  private Scene scene;
  private BorderPane borderPane;
  private TextArea codeArea;
  private Button run;
  private ResourceBundle resources;
  private static final String STYLESHEET = "gui.css";
  public static final String RESOURCE_PACKAGE = "slogo.view.resources.";
  public static final String RESOURCE_FOLDER = RESOURCE_PACKAGE.replace(".", "/");

  /**
   * This is teh constructor for the View class. It needs to be initIalized with a stage which
   * contains all the nodels to display and a model controller to allow the view to pass commands to
   * the mode.
   *
   * @param stage
   * @param modelCon
   */
  public View(Stage stage, ModelController modelCon) {
    stage.setTitle("Turtle IDE... T-IDE");
    this.modelCon = modelCon;
    this.environment = EnvironmentFactory.createEnvironment();
    this.resources = ResourceBundle.getBundle(RESOURCE_PACKAGE + "English");

    viewCon = new ViewBundle();
    modelCon.setController(viewCon);
    modelCon.setModel(environment);
    scene = createScene();
    stage.setScene(scene);
    stage.show();
  }

  public Scene createScene() {
    helpPane = new HelpPane(resources);
    environmentPane = new EnvironmentPane();
    turtleSandbox = new TurtleSandbox(viewCon);
    commandPane = makeBottomPane();
    settingsPane = new SettingsPane(viewCon);
    borderPane = new BorderPane();
    setId();
    Scene newScene = new Scene(borderPane, WIDTH, HEIGHT);

    environmentPane.setStyle("-fx-background-color: white");
    commandPane.setStyle("-fx-background-color: white");
    settingsPane.setStyle("-fx-background-color: white");
    helpPane.setStyle("-fx-background-color: white");

    borderPane.setStyle("-fx-background-color: white");
    borderPane.setCenter(turtleSandbox);
    borderPane.setTop(settingsPane);
    borderPane.setBottom(commandPane);
    borderPane.setLeft(environmentPane);
    borderPane.setRight(helpPane);
    newScene
        .getStylesheets()
        .add(getClass().getResource("resources/" + STYLESHEET).toExternalForm());

    environment.setOnTurtleUpdate(e -> turtleSandbox.updateTurtle(e));

    environment.setOnVariableUpdate(e -> environmentPane.updateVariables(e));

    environment.setOnCommandUpdate(e -> environmentPane.updateCommands(e));

    environment.setOnClear(() -> turtleSandbox.clearLines());

    return newScene;
  }

  public Pane makeBottomPane() {
    GridPane pane = new GridPane();
    codeArea = new TextArea();
    run = new Button();
    changeTextInstruction("English");
    run.setOnMouseClicked(e -> sendCodeArea());

    codeArea.setOnKeyPressed(
        e -> {
          if (e.getCode() == KeyCode.ENTER && e.isShiftDown()) {
            sendCodeArea();
          } else if (e.getCode() == KeyCode.UP && codeArea.getText() == "") {
            codeArea.setText(environmentPane.getPreviousCommand());
            codeArea.end();
          }
        });

    pane.add(codeArea, 0, 0);
    pane.add(run, 1, 0);
    pane.setAlignment(Pos.CENTER);
    return pane;
  }

  public void sendCodeArea() {
    String command = codeArea.getText();
    sendCommand(command);
    codeArea.clear();
  }

  private void changeTextInstruction(String language) {
    this.resources = ResourceBundle.getBundle(RESOURCE_PACKAGE + language);
    // TODO: Maybe say an instruction like shift+enter to run?
    codeArea.setPromptText(resources.getString("userCommand"));
    helpPane.setResources(resources);
    run.setText(resources.getString("runButton"));
  }

  private void sendCommand(String command) {
    boolean executed = modelCon.sendCommand(command);
    environmentPane.addPreviousCommand(command, executed);
  }

  private void setId() {
    helpPane.setId("helpPane");
    commandPane.setId("commandPane");
    environmentPane.setId("environmentPane");
    settingsPane.setId("settingsPane");
    turtleSandbox.setId("turtlePane");
    run.setId("runButton");
    codeArea.setId("codeArea");
  }

  /**
   * This class implements the ViewController class. It implements setBackground and setLanguages
   * methods for when a user changes the background color and the language for the display.
   */
  private class ViewBundle implements ViewController {

    /**
     * This method sets the background color to what the user picked from the display window.
     *
     * @param color - The color to set the background to.
     */
    public void setBackground(String color) {
      color = "#" + color.substring(2);
      turtleSandbox.setSandboxColor(color);
    }

    /**
     * This method sets the display language to the user's input.
     *
     * @param language - The language locale to use.
     */
    public void setLanguage(String language) {
      changeTextInstruction(language);
      environmentPane.createTitles(language);
      modelCon.setLanguage(language);
    }

    /**
     * This method sets the color of turtle's pen to the the color the user picked from the display
     * window
     *
     * @param color - the color to set the background to.
     */
    public void setPenColor(String color) {
      color = "#" + color.substring(2);
      turtleSandbox.setPenColor(color);
    }

    /**
     * This method sets the turtle logo to be displayed.
     *
     * @param turtleLogo - the turtle logo the user picked.
     */
    public void setTurtleLogo(String turtleLogo) {}

    public void sendAlert(String title, String message) {
      Alert a = new Alert(AlertType.ERROR, message, ButtonType.CLOSE);
      a.showAndWait()
          .ifPresent(
              res -> {
                System.out.println(res);
              });
    }

    public void addTurtle() {
      modelCon.addTurtle();
    }

    public void setCurrTurtle(int id) {
      modelCon.setCurrTurtle(id);
    }

    public void sendCommand(String command) {}
  }
}
