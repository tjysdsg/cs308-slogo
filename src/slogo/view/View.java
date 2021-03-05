package slogo.view;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
  public static final int SIZE = 700;
  private Insets layoutPadding = new Insets(10);

	ModelController modelCon;
	TrackableEnvironment environment;
	ViewController viewCon;
	EnvironmentPane environmentPane;
	HelpPane helpPane;
	TurtleSandbox turtleSandbox;
	SettingsPane settingsPane;
	Pane commandPane;
	Scene scene;
	BorderPane borderPane;
	TextField userText;
	Button run;
	private static final String STYLESHEET = "gui.css";
	private static final String RESOURCE_PACKAGE = "slogo.view.resources.";
	private static final String RESOURCE_FOLDER = "/" + RESOURCE_PACKAGE.replace(".", "/");

	/**
	 * This is teh constructor for the View class.
	 * It needs to be initIalized with a stage which
	 * contains all the nodels to display and a model controller
	 * to allow the view to pass commands to the mode.
	 * @param stage
	 * @param modelCon
	 */
	public View(Stage stage, ModelController modelCon) {
		this.modelCon = modelCon;
		this.environment = EnvironmentFactory.createEnvironment();
		viewCon = new ViewBundle();
		modelCon.setController(viewCon);
		scene = createScene();
		stage.setScene(scene);
		stage.show();

	}

	public Scene createScene() {
		environmentPane = new EnvironmentPane();
		helpPane = new HelpPane();
		turtleSandbox = new TurtleSandbox();
		commandPane = makeBottomPane();
		settingsPane = new SettingsPane(viewCon);
		Pane topPane =  settingsPane.createSettingsPane();
		borderPane = new BorderPane();
		Scene newScene = new Scene(borderPane, SIZE, SIZE);

		borderPane.setTop(topPane);
		borderPane.setBottom(commandPane);
		borderPane.setLeft(environmentPane);
		borderPane.setRight(helpPane);
		borderPane.setCenter(turtleSandbox);
		borderPane.setPadding(layoutPadding);

		newScene.getStylesheets().add(getClass().getResource(RESOURCE_FOLDER + STYLESHEET).toExternalForm());

		return newScene;
	}

	public Pane makeBottomPane(){
		GridPane pane = new GridPane();
		userText = new TextField();
		run =  new Button();
		changeTextInstruction("English");
		userText.setOnMouseClicked(event -> userText.clear());
		userText.setPrefSize(.9*SIZE, .05*SIZE);
		pane.add(userText, 0, 0);
		pane.add(run, 1, 0);
		viewCon.sendUserText();



		return pane;
	}

	private void changeTextInstruction(String language) {
		ResourceBundle resources = ResourceBundle.getBundle(RESOURCE_PACKAGE + language);
		userText.setText(resources.getString("userCommand"));
		run.setText(resources.getString("runButton"));
	}


	/**
	 * This class implements the ViewController class. It
	 * implements setBackground and setLanguages methods for when a user changes
	 * the background color and the language for the display.
	 */

	private class ViewBundle implements ViewController {

		/**
		 * This method sets the background color to what the
		 * user picked from the display window.
		 * @param colorPicker - The color to set the background to.
		 */
		public void setBackground(ColorPicker colorPicker) {
			EventHandler<ActionEvent> pickBackgroundColor = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e)
				{
					Color c = colorPicker.getValue();
					//do something with this depending on what Josh
					//is doing with turtle sandbox.
				}
			};
			colorPicker.setOnAction(pickBackgroundColor);

		}

		/**
		 * This method sets the display language to the user's
		 * input.
		 * @param choiceBox - The language locale to use.
		 */
		public void setLanguage(ChoiceBox choiceBox) {
			EventHandler<ActionEvent> pickLanguage = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e)
				{
					String language = "" + choiceBox.getValue();
					settingsPane.displayLabels(language);
					changeTextInstruction(language);
				}
			};
			choiceBox.setOnAction(pickLanguage);

		}


		/**
		 * This method sets the color of turtle's pen to the
		 * the color the user picked from the display window
		 * @param colorPicker - the color to set the background to.
		 */
		public void setPenColor(ColorPicker colorPicker) {

			EventHandler<ActionEvent> pickPenColor = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e)
				{
					Color c = colorPicker.getValue();

					// do something with the color.
					System.out.println("Red = " + c.getRed() + ", Green = " + c.getGreen()
							+ ", Blue = " + c.getBlue());
				}
			};
			colorPicker.setOnAction(pickPenColor);
		}

		/**
		 * This method sets the turtle logo to be displayed.
		 * @param turtleLogo - the turtle logo the user picked.
		 */
		public void setTurtleLogo(String turtleLogo) {

		}

		public void sendAlert(String title, String message) {

		}

		public void sendUserText(){
			EventHandler<ActionEvent> sendUser = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e)
				{
					if (userText.getText()!=null) {
						//send out string to the user.
						System.out.print(userText.getText());
					}
					else{
						//send error!
					}


				}

			};
			run.setOnAction(sendUser);

		}
	}
}
