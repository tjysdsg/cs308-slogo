package slogo.view;

import com.jfoenix.controls.JFXListView;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import slogo.events.CommandsRecord;
import slogo.events.DisplayCommand;
import slogo.events.DisplayVariable;
import slogo.events.VariablesRecord;

public class EnvironmentPane extends GridPane {

  public static final int TABLE_SIZE = 200;
  private Label lastRanCommand;
  private TableView<DisplayCommand> commandsTable;
  private TableView<DisplayVariable> variablesTable;
  private JFXListView<Label> previousCommands;
  public static final String RESOURCE_PACKAGE = "slogo.view.resources.";
  private ResourceBundle resources;
  private TitledPane commandsToggle;
  private TitledPane variablesToggle;
  private TitledPane prevCommands;
  private ViewController viewController;

  public EnvironmentPane(ViewController viewController) {
    this.viewController = viewController;
    this.resources = viewController.getResources();
    variablesTable = new TableView<>();
    commandsTable = new TableView<>();
    createTableViews();
    commandsToggle = new TitledPane();
    commandsToggle.setContent(commandsTable);
    variablesToggle = new TitledPane();
    variablesToggle.setContent(variablesTable);
    previousCommands = new JFXListView<Label>();
    previousCommands.setPrefHeight(200);
    prevCommands = new TitledPane();
    prevCommands.setContent(previousCommands);

    previousCommands.setOnMouseClicked(
        e -> {
          if (e.getClickCount() == 2) {
            Label selectedLabel = previousCommands.getSelectionModel().getSelectedItem();
            String command = selectedLabel.getText();
            viewController.sendCommand(command);
          }
        });
    variablesToggle.setMaxHeight(Double.MAX_VALUE);

    add(variablesToggle, 0, 0);
    add(commandsToggle, 0, 1);
    add(prevCommands, 0, 2);

    RowConstraints row1 = new RowConstraints();
    row1.setVgrow(Priority.ALWAYS);
    getRowConstraints().add(row1);

    setResources(resources);
    setID();
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
    commandsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<DisplayVariable, String> varNameCol = new TableColumn<>("Identifier");
    TableColumn<DisplayVariable, String> varValueCol = new TableColumn<>("Value");
    variablesTable.getColumns().addAll(varNameCol, varValueCol);

    varNameCol.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().name()));
    varValueCol.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().value()));

    variablesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    variablesTable.setPrefHeight(TABLE_SIZE);
    commandsTable.setPrefHeight(TABLE_SIZE);
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
    return lastRanCommand != null ? lastRanCommand.getText() : "";
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
