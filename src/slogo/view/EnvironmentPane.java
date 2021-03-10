package slogo.view;

import com.jfoenix.controls.JFXListView;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import slogo.events.DisplayCommand;
import slogo.events.DisplayVariable;
import slogo.events.CommandsRecord;
import slogo.events.VariablesRecord;

public class EnvironmentPane extends Pane {

  public static final int TABLE_SIZE = 200;
  private Label lastRanCommand;
  TableView<DisplayCommand> commandsTable;
  TableView<DisplayVariable> variablesTable;
  JFXListView<Label> previousCommands;
  public static final String RESOURCE_PACKAGE = "slogo.view.resources.";
  ResourceBundle resources;
  TitledPane commandsToggle;
  TitledPane variablesToggle;
  TitledPane prevCommands;

  public EnvironmentPane() {
    VBox vbox = new VBox();
    variablesTable = new TableView<>();
    commandsTable = new TableView<>();
    vbox.setAlignment(Pos.CENTER_LEFT);
    getChildren().add(vbox);

    createTableViews();
//    TitledPane commandsToggle = new TitledPane("Commands", commandsTable);
    commandsToggle = new TitledPane();
    commandsToggle.setContent(commandsTable);
//    TitledPane variablesToggle = new TitledPane("Variables", variablesTable);
    variablesToggle = new TitledPane();
    variablesToggle.setContent(variablesTable);
    previousCommands = new JFXListView<Label>();
    previousCommands.setPrefHeight(200);
    //TitledPane prevCommands = new TitledPane("Previous Commands", previousCommands);
    prevCommands = new TitledPane();
    prevCommands.setContent(previousCommands);

    vbox.getChildren().addAll(variablesToggle, commandsToggle, prevCommands);

    createTitles("English");
    createMockData();
  }

  public void createTitles(String language) {
    resources = ResourceBundle.getBundle(RESOURCE_PACKAGE + language);
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
    //commandsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<DisplayVariable, String> varNameCol = new TableColumn<>("Identifier");
    TableColumn<DisplayVariable, String> varValueCol = new TableColumn<>("Value");
    variablesTable.getColumns().addAll(varNameCol, varValueCol);

    varNameCol.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().name()));
    varValueCol.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().value()));

    //variablesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    variablesTable.setPrefHeight(TABLE_SIZE);
    commandsTable.setPrefHeight(TABLE_SIZE);
  }

  private void createMockData() {
    commandsTable
        .getItems()
        .addAll(
            new DisplayCommand("name", "signature"),
            new DisplayCommand("name1", "signature1"),
            new DisplayCommand("name2", "signature2"));
    variablesTable
        .getItems()
        .addAll(
            new DisplayVariable("name", "value"),
            new DisplayVariable("name1", "value1"),
            new DisplayVariable("name2", "value2"),
            new DisplayVariable("name3", "value3"));
  }

  public void updateVariables(VariablesRecord records) {
    variablesTable.getItems().setAll(records.variables());
  }

  public void updateCommands(CommandsRecord records) {
    commandsTable.getItems().setAll(records.commands());
  }

  /**
   * Changes the color of the most recently run
   * command if it resulted in an error.
   */
  public void previousError() {
    if (lastRanCommand != null) {
      lastRanCommand.setStyle("-fx-text-fill: red");
    }
  }

  public String getPreviousCommand() {
    return lastRanCommand != null ? lastRanCommand.getText() : "";
  }

  public void addPreviousCommand(String command) {
    lastRanCommand = new Label(command);
    previousCommands.getItems().add(lastRanCommand);
  }
}
