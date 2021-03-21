package slogo.view;

import java.util.prefs.Preferences;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
  private static final int HEIGHT = 800;

  private ModelController modelCon;
  private List<Workspace> workspaces;
  private TrackableEnvironment environment;
  private ViewController viewCon;
  private EnvironmentPane environmentPane;
  private HelpPane helpPane;
  private TurtleSandbox turtleSandbox;
  private SettingsPane settingsPane;
  private CommandPane commandPane;
  private HBox topPane;
  private Scene scene;
  private Label workspaceLabel;
  private BorderPane borderPane;
  private ResourceBundle resources;
  private Preferences mainSettings;
  private Preferences settings;
  private static final String STYLESHEET = "gui.css";
  public static final String RESOURCE_PACKAGE = "slogo.view.resources.";

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
    this.viewCon = new ViewBundle();
    this.resources = ResourceBundle.getBundle(RESOURCE_PACKAGE + "English");
    this.borderPane = new BorderPane();
    this.scene = new Scene(borderPane, WIDTH, HEIGHT);
    this.helpPane = new HelpPane(resources);
    this.topPane = new HBox();
    this.workspaces = new ArrayList<>();
    this.mainSettings = Preferences.userRoot().node(this.getClass().getName());
    this.settings = mainSettings.node("0");
    this.settingsPane = new SettingsPane(viewCon, settings);
    scene.getStylesheets().add(getClass().getResource("resources/" + STYLESHEET).toExternalForm());
    stage.setScene(scene);
    stage.setMinHeight(HEIGHT);
    stage.setMinWidth(WIDTH);


    topPane.getChildren().addAll(settingsPane);
    topPane.setAlignment(Pos.CENTER_LEFT);
    createWorkspaceSelector(topPane);
    borderPane.setTop(topPane);
    Workspace mainWorkspace = createWorkspace();
    setWorkspace(mainWorkspace);

    modelCon.setController(viewCon);
    modelCon.setModel(environment);
    refreshBundle();
    stage.show();
  }

  public void createWorkspaceSelector(HBox topPane) {
    this.workspaceLabel = new Label("Workspace: ");
    IntegerSpinnerValueFactory spinValFac = new IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);
    Spinner<Integer> workspacesChoice = new Spinner<>(spinValFac);
    workspacesChoice.setPrefWidth(100);
    workspacesChoice
        .valueProperty()
        .addListener(
            (obs, old, newValue) -> {
              updateWorkspace(newValue);
            });
    topPane.getChildren().addAll(workspaceLabel, workspacesChoice);
  }

  public void updateWorkspace(int space) {
    Workspace workspace = null;
    if (space > workspaces.size() - 1) {
      workspace = createWorkspace();
    } else {
      workspace = workspaces.get(space);
    }
    setWorkspace(workspace);
  }

  public Workspace createWorkspace() {
    TrackableEnvironment environment = EnvironmentFactory.createEnvironment();
    EnvironmentPane environmentPane = new EnvironmentPane(viewCon);
    TurtleSandbox turtleSandbox = new TurtleSandbox(viewCon);
    CommandPane commandPane = new CommandPane(viewCon);
    int workspaceID = workspaces.size();
    Workspace workspace = new Workspace(environment, commandPane, turtleSandbox, environmentPane);
    // Doesn't work within css for some reason :/
    environmentPane.setStyle("-fx-background-color: white");
    commandPane.setStyle("-fx-background-color: white");
    settingsPane.setStyle("-fx-background-color: white");
    helpPane.setStyle("-fx-background-color: white");
    borderPane.setStyle("-fx-background-color: white");

    environment.setOnTurtleUpdate(e -> turtleSandbox.updateTurtle(e));
    environment.setOnVariableUpdate(e -> environmentPane.updateVariables(e));
    environment.setOnCommandUpdate(e -> environmentPane.updateCommands(e));
    environment.setOnClear(() -> turtleSandbox.clearLines());

    workspaces.add(workspace);
    return workspace;
  }

  protected void setWorkspace(Workspace workspace) {
    this.environment = workspace.environment();
    this.turtleSandbox = workspace.turtleSandbox();
    this.commandPane = workspace.commandPane();
    this.environmentPane = workspace.environmentPane();
    modelCon.setModel(environment);

    refreshBundle();
    borderPane.setCenter(turtleSandbox);
    borderPane.setBottom(commandPane);
    borderPane.setLeft(environmentPane);
    // Have to do this so the sandbox remains on the bottom
    borderPane.setTop(null);
    borderPane.setRight(null);
    borderPane.setTop(topPane);
    borderPane.setRight(helpPane);

    setPaneIDs();
  }

  private void refreshBundle() {
    // TODO: Maybe say an instruction like shift+enter to run?
    helpPane.setResources(resources);
    environmentPane.setResources(resources);
    commandPane.setResources(resources);
    settingsPane.setResources(resources);
  }

  private void setPaneIDs() {
    helpPane.setId("helpPane");
    commandPane.setId("commandPane");
    environmentPane.setId("environmentPane");
    settingsPane.setId("settingsPane");
    turtleSandbox.setId("turtlePane");
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
      resources = ResourceBundle.getBundle(RESOURCE_PACKAGE + language);
      refreshBundle();
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
      a.showAndWait().ifPresent(System.out::println);
    }

    public void addTurtle() {
      modelCon.addTurtle();
    }

    public void setCurrTurtle(int id) {
      modelCon.setCurrTurtle(id);
    }

    public ResourceBundle getResources() {
      return resources;
    }

    public void fillCommandArea(String text) {
      commandPane.fillCodeArea(environmentPane.getPreviousCommand());
    }

    public void changeVariable(String variable, double newValue) {
      System.out.println(String.format("Change: %s to %.2f", variable, newValue));
    }

    public void changeCommand(String command, String newValue) {}

    public void sendCommand(String command) {
      if (command.isBlank()) return;
      boolean executed = modelCon.sendCommand(command);
      environmentPane.addPreviousCommand(command, executed);
    }
  }
}

record Workspace(
    TrackableEnvironment environment,
    CommandPane commandPane,
    TurtleSandbox turtleSandbox,
    EnvironmentPane environmentPane) {}
