// 1. The user changes the language of the simulation

class SettingsPane extends Pane {
  //...

  choiceBox.setOnAction( (e) -> {
    viewController.setBackground(e.getString());
  });
}

class View {
  // ...
  //
  private class ViewController {
    public void setBackground(String background) {
      // Will likely change to modifying css styles
      turtleDisplayPane.setFill(Color.getColor(background));
    }
  }
}

// 2. The user sets the language

class SettingsPane extends Pane {
  //...
  //
  choiceBox.setOnAction( (e) -> {
    viewController.setLanguage(e.getString());
  });
}

class View {
  private class ViewController {
    public setLanguage(String language) {
      if (providedLangauges.contains(language) {
        this.resources = ResourceBundle.getBundle(language + "GUI.properties");
        modelController.setLanguage(language);
      } else {
        // They might not understand this but hey...
        sendAlert(resources.getString("Error"), resources.getString("InvalidLanguage"));
      }
    }
  }
}

class ModelController {
  //...
  //
  public setLanguage(String language) {
    // ik .properties isn't needed.
    this.resources = ResourceBundle.getBundle(language + ".properties");
    model.setBundle(resources);
  }
}

// 3. The user enters an invalid commando
class ModelController {
try {
	model.sendCommand("Bad Command");
} catch (ModelException e) {
	String comName = e.getName();
	String format = bundle.getString(comName); // Gets the translated format from the bundle (i.e Incorrect Parameter Count for %s, expected: %d, actual %d in the current langauge)
	String translatedRes = e.formatException(format); // internally does String.format(format, var1, var2);
	view.showAlert(bundle.getString("Error"), translatedRes);
}
