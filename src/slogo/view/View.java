package slogo.view;

import javafx.stage.Stage;
import slogo.model.EnvironmentFactory;
import slogo.model.TrackableEnvironment;

/**
 * This class coordinates between all other classes
 * from the view package. It needs to be initialized with a stage and a model
 * controller object.  It also contains a private View bundle class
 * that implements the view controller and allows the user to change the
 */
public class View {
	ModelController modelCon;
	TrackableEnvironment environment;

	public View(Stage stage, ModelController modelCon) {
		this.modelCon = modelCon;
		this.environment = EnvironmentFactory.createEnvironment();
	}

	private class ViewBundle implements ViewController {
		public void setBackground(String color) {

		}

		public void setLanguage(String lang) {

		}
	}
}
