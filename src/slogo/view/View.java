package slogo.view;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.model.EnvironmentFactory;
import slogo.model.TrackableEnvironment;

/**
 * @author Joshua Pettima
 * @author marthaaboagye
 * This class coordinates between all other classes
 * from the view package. It needs to be initialized with a stage and a model
 * controller object.  It also contains a private View bundle class
 * that implements the view controller and allows the user to change the
 *  color for the pen and background.
 */
public class View {
  private static final int WIDTH = 1200;
  private static final int HEIGHT = 750;
  private Insets layoutPadding = new Insets(10);

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
	private static final String STYLESHEET = "gui.css";
	public static final String RESOURCE_PACKAGE = "resources.";
	public static final String RESOURCE_FOLDER = RESOURCE_PACKAGE.replace(".", "/");
	public static final String LANGUAGE_FOLDER = RESOURCE_FOLDER + "languages/";
	ResourceBundle resources = ResourceBundle.getBundle(LANGUAGE_FOLDER + "English");

	/**
	 * This is teh constructor for the View class.
	 * It needs to be initIalized with a stage which
	 * contains all the nodels to display and a model controller
	 * to allow the view to pass commands to the mode.
	 * @param stage
	 * @param modelCon
	 */
	public View(Stage stage, ModelController modelCon) {
		stage.setTitle("floating");
		this.modelCon = modelCon;
		this.environment = EnvironmentFactory.createEnvironment();

		viewCon = new ViewBundle();
		modelCon.setController(viewCon);
		modelCon.setModel(environment);
		scene = createScene();
		stage.setScene(scene);
		stage.show();

	}

	public Scene createScene() {
		environmentPane = new EnvironmentPane(viewCon);
		helpPane = new HelpPane(viewCon);
		turtleSandbox = new TurtleSandbox();
		commandPane = makeBottomPane();
		settingsPane = new SettingsPane(viewCon);
		borderPane = new BorderPane();
		Scene newScene = new Scene(borderPane, WIDTH, HEIGHT);

		borderPane.setCenter(turtleSandbox);
		borderPane.setTop(settingsPane);
		borderPane.setBottom(commandPane);
		borderPane.setLeft(environmentPane);
		borderPane.setRight(helpPane);
		borderPane.setPadding(layoutPadding);
		newScene.getStylesheets().add(getClass().getResource(RESOURCE_FOLDER + STYLESHEET).toExternalForm());

		environment.setOnTurtleUpdate( e -> {
			turtleSandbox.updateTurtle(e);
		});

		environment.setOnVariableUpdate( e -> {
			environmentPane.updateVariables(e);
		});

		environment.setOnCommandUpdate( e -> {
			environmentPane.updateCommands(e);
		});

		return newScene;
	}

	public Pane makeBottomPane(){
		GridPane pane = new GridPane();
		codeArea = new TextArea();
		run =  new Button();
		changeTextInstruction("English");
		run.setOnMouseClicked(event -> {
			String command = codeArea.getText();
			modelCon.sendCommand(command);
			environmentPane.addPreviousCommand(command);
			codeArea.clear();
		});

		codeArea.setOnKeyPressed( e -> {
			String command = codeArea.getText();
			if (e.getCode() == KeyCode.ENTER && e.isShiftDown()) {
				modelCon.sendCommand(command);
				codeArea.clear();
				environmentPane.addPreviousCommand(command);
			}
		});

		pane.add(codeArea, 0, 0);
		pane.add(run, 1, 0);
		pane.setAlignment(Pos.CENTER);
		return pane;
	}

	private void changeTextInstruction(String language) {
		resources = ResourceBundle.getBundle(LANGUAGE_FOLDER + language);
		// TODO: Maybe say an instruction like shift+enter to run?
		codeArea.setPromptText(resources.getString("userCommand"));
		run.setText(resources.getString("runButton"));
	}


//	public ResourceBundle setBundle(ResourceBundle resources) {
//		return this.resources;
//
//	}






	/**
	 * This class implements the ViewController class. It
	 * implements setBackground and setLanguages methods for when a user changes
	 * the background color and the language for the display.
	 */

	private class ViewBundle implements ViewController {

		/**
		 * This method sets the background color to what the
		 * user picked from the display window.
		 * @param color - The color to set the background to.
		 */
		public void setBackground(String color) {
			color = "#" + color.substring(2);
			turtleSandbox.setSandboxColor(color);
		}

		/**
		 * This method sets the display language to the user's
		 * input.
		 * @param language - The language locale to use.
		 */
		public void setLanguage(String language) {
					changeTextInstruction(language);
				helpPane.createDisplayLanguages();
			environmentPane.createTitles();
		}


		/**
		 * This method sets the color of turtle's pen to the
		 * the color the user picked from the display window
		 * @param color - the color to set the background to.
		 */
		public void setPenColor(String color) {
			color = "#" + color.substring(2);
			turtleSandbox.setPenColor(color);
		}

		/**
		 * This method sets the turtle logo to be displayed.
		 * @param turtleLogo - the turtle logo the user picked.
		 */
		public void setTurtleLogo(String turtleLogo) {

		}

		@Override
		public ResourceBundle setBundle() {
			return resources;
		}


		public void sendAlert(String title, String message) {

		}

//		public void sendUserText(){
//			if (codeArea.getText()!=null) {
//				((EnvironmentPane) environmentPane).addPreviousCommand(codeArea.getText());
//				modelCon.sendCommand(codeArea.getText());
//			}
//			else{
//				viewCon.sendAlert("Error", "STOSAPSGI");
//			}
//		}
	}
}
