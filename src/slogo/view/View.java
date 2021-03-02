package slogo.view;

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
	ModelController modelCon;
	TrackableEnvironment environment;

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
	}

	/**
	 * This class implements the ViewController class. It
	 * implements setBackground and setLanguages methods for when a user changes
	 * the background color and the language for the display.
	 */

	private class ViewBundle implements ViewController {

		/**
		 * This method sets the background clor to what the
		 * user picked from the display window.
		 * @param color - The color to set the background to.
		 */
		public void setBackground(String color) {

		}

		/**
		 * This method sets the display language to the user's
		 * input.
		 * @param lang - The language locale to use.
		 */

		public void setLanguage(String lang) {

		}
	}
}
