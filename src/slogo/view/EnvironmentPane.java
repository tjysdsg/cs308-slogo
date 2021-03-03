package slogo.view;


import java.util.List;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import slogo.events.DisplayCommand;
import slogo.events.DisplayVariable;
import slogo.events.VariablesRecord;

public class EnvironmentPane extends Pane {

  public static final int TABLE_SIZE = View.SIZE / 2 - 20;
  TableView<DisplayCommand> commandsTable;
  TableView<DisplayVariable> variablesTable;

  public EnvironmentPane() {
    VBox vbox = new VBox();
    variablesTable = new TableView<>();
    commandsTable = new TableView<>();
    createTableViews();
    vbox.getChildren().add(variablesTable);
    vbox.getChildren().add(commandsTable);
    vbox.setAlignment(Pos.CENTER_LEFT);
    getChildren().add(vbox);

    createMockData();
  }

  public void createTableViews() {
    TableColumn comCol = new TableColumn<>("Commands");
    TableColumn<DisplayCommand, String> comNameCol = new TableColumn<>("Name");
    TableColumn<DisplayCommand, String> comValueCol = new TableColumn<>("Command");

    // Wrap fields as read only to allow it to be used in listview
    comNameCol.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().name()));
    comValueCol.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().signature()));
    comCol.getColumns().addAll(
        comNameCol, comValueCol
    );

    commandsTable.getColumns().add(comCol);
    commandsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn varCol = new TableColumn("Variables");
    TableColumn<DisplayVariable, String> varNameCol = new TableColumn<>("Identifier");
    TableColumn<DisplayVariable, String> varValueCol = new TableColumn<>("Value");
    varCol.getColumns().addAll(varNameCol, varValueCol);

    varNameCol.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().name()));
    varValueCol.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().value()));

    variablesTable.getColumns().addAll(varCol);
    variablesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    variablesTable.setPrefHeight(TABLE_SIZE);
    commandsTable.setPrefHeight(TABLE_SIZE);
  }

  private void createMockData() {
    commandsTable.getItems().addAll(
        new DisplayCommand("name", "signature"),
        new DisplayCommand("name1", "signature1"),
        new DisplayCommand("name2", "signature2")
    );
    variablesTable.getItems().addAll(
        new DisplayVariable("name", "value"),
        new DisplayVariable("name1", "value1"),
        new DisplayVariable("name2", "value2"),
        new DisplayVariable("name3", "value3")
    );
  }

  public void updateVariables(VariablesRecord records) {
    variablesTable.getItems().removeAll();
    variablesTable.getItems().addAll(records.variables());
  }

  public void updateVariables(List<DisplayVariable> records) {
    variablesTable.getItems().removeAll();
    variablesTable.getItems().addAll(records);
  }
}
