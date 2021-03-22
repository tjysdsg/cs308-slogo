package slogo.view;

import slogo.model.notifiers.ModelTracker;
import com.jfoenix.controls.JFXListView;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import slogo.records.CommandsRecord;
import slogo.records.DisplayCommand;
import slogo.records.DisplayVariable;
import slogo.records.VariablesRecord;

public class EnvironmentPane extends GridPane {

  public static final int TABLE_SIZE = 200;
  private Label lastRanCommand;
  private TableView<DisplayCommand> commandsTable;
  private TableView<DisplayVariable> variablesTable;
  private JFXListView<Label> previousCommands;
  private ResourceBundle resources;
  private TitledPane commandsToggle;
  private TitledPane variablesToggle;
  private TitledPane prevCommands;
  private ModelTracker environment;
  private ViewController viewController;
  private TextInputDialog changeVarDialog;
  private TextInputDialog sendCommandDialog;
  private int keycodeUpCount = 0;

  public EnvironmentPane(ViewController viewController, ModelTracker environment) {
    this.environment = environment;
    this.viewController = viewController;
    this.resources = viewController.getResources();
    variablesTable = new TableView<>();
    commandsTable = new TableView<>();
    createTableViews();
    commandsToggle = new TitledPane();
    commandsToggle.setContent(commandsTable);
    variablesToggle = new TitledPane();
    variablesToggle.setContent(variablesTable);
    createVariableDialog();

    this.sendCommandDialog = new TextInputDialog();
    sendCommandDialog.setHeaderText("Enter Parameters: ");

    previousCommands = new JFXListView<Label>();
    previousCommands.setMinHeight(TABLE_SIZE);
    prevCommands = new TitledPane();
    prevCommands.setContent(previousCommands);
    previousCommands.setOnMouseClicked(
        e -> {
          if (e.getClickCount() == 2) {
            Label selectedLabel = previousCommands.getSelectionModel().getSelectedItem();
            String command = selectedLabel.getText();
            viewController.sendCommand(command);
          } else if (e.isShiftDown()) {
            Label selectedLabel = previousCommands.getSelectionModel().getSelectedItem();
            String command = selectedLabel.getText();
            viewController.fillCommandArea(command);
          }
        });

    add(variablesToggle, 0, 0);
    add(commandsToggle, 0, 1);
    add(prevCommands, 0, 2);

    setResources(resources);
    setID();
  }

  private void createVariableDialog() {
    this.changeVarDialog = new TextInputDialog();
    changeVarDialog.setHeaderText("Refactor");
    TextField inputField = changeVarDialog.getEditor();
    Button okButton = (Button) changeVarDialog.getDialogPane().lookupButton(ButtonType.OK);
    okButton.addEventFilter(
        ActionEvent.ACTION,
        ae -> {
          if (isNotNumber(inputField.getText())) {
            ae.consume();
            viewController.sendAlert("Invalid Input", "Please enter a Number");
          }
        });
  }

  private boolean isNotNumber(String text) {
    try {
      double d = Double.parseDouble(text);
    } catch (NumberFormatException e) {
      return true;
    }
    return false;
  }

  private void setID() {
    previousCommands.setId("prevCommands");

  }

  public void setResources(ResourceBundle resources) {
    this.resources = resources;
    commandsToggle.setText(resources.getString("command"));
    variablesToggle.setText(resources.getString("variable"));
    prevCommands.setText(resources.getString("prevCommand"));
  }

  public void createTableViews() {
    TableColumn<DisplayCommand, String> comNameCol = new TableColumn<>("Name");
    TableColumn<DisplayCommand, String> comValueCol = new TableColumn<>("Command");

    // Wrap fields as read only to allow it to be used in listview
    comNameCol.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().name()));
    comValueCol.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().signature()));

    commandsTable.getColumns().addAll(comNameCol, comValueCol);

    TableColumn<DisplayVariable, String> varNameCol = new TableColumn<>("Identifier");
    TableColumn<DisplayVariable, String> varValueCol = new TableColumn<>("Value");
    variablesTable.getColumns().addAll(varNameCol, varValueCol);

    variablesTable.setOnMouseClicked(
        e -> {
          if (e.getClickCount() == 2) {
            DisplayVariable variable = variablesTable.getSelectionModel().getSelectedItem();
            changeVarDialog.setContentText(String.format("Set Value of %s", variable.name()));
            String variableName = variable.name();
            Optional<String> res = changeVarDialog.showAndWait();
            if (res.isPresent()) {
              String value = res.get();
              DisplayVariable dv = new DisplayVariable(variableName, value);
              environment.requestVarUpdate(dv);
            }
          }
        });

    commandsTable.setOnMouseClicked(
        e -> {
          if (e.getClickCount() == 2) {
            DisplayCommand command = commandsTable.getSelectionModel().getSelectedItem();
            String commandName = command.name();
            Optional<String> res = sendCommandDialog.showAndWait();
            if (res.isPresent()) {
              String value = res.get();
              viewController.sendCommand(commandName + " " + value);
            }
          }
        });

    varNameCol.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().name()));
    varValueCol.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().value()));
  }

  public void updateVariables(VariablesRecord records) {
    variablesTable.getItems().setAll(records.variables());
  }

  public void updateCommands(CommandsRecord records) {
    commandsTable.getItems().setAll(records.commands());
    int index = records.commands().size();
    commandsTable.scrollTo(index);
  }

  public String getPreviousCommand() {
    keycodeUpCount++;
    while (keycodeUpCount <= previousCommands.getItems().size()) {
      int index = previousCommands.getItems().size() - keycodeUpCount;
      return lastRanCommand != null ? previousCommands.getItems().get(index).getText() : "";
    }
    ;
    keycodeUpCount = 0;
    return "";

  }

  public void addPreviousCommand(String command, boolean successful) {
    lastRanCommand = new Label(command);
    if (!successful) {
      lastRanCommand.getStyleClass().add("command-error");
    }
    previousCommands.getItems().add(lastRanCommand);
    int index = previousCommands.getItems().size();
    previousCommands.scrollTo(index);
  }
}
