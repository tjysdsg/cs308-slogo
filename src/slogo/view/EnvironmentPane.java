package slogo.view;


import java.util.List;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import slogo.events.DisplayCommand;
import slogo.events.DisplayVariable;
import slogo.events.VariablesRecord;

public class EnvironmentPane extends Pane {
  public static final int TABLE_SIZE = 200;
  TableView<DisplayCommand> commandsTable;
  TableView<DisplayVariable> variablesTable;

  public EnvironmentPane() {
    VBox vbox = new VBox();
    variablesTable = new TableView<>();
    commandsTable = new TableView<>();
    createTableViews();
    vbox.getChildren().add(variablesTable);
    vbox.getChildren().add(commandsTable);
    getChildren().add(vbox);

  }

  public void createTableViews() {
    TableColumn comCol = new TableColumn<>("Commands");
    TableColumn<DisplayCommand, String> comNameCol = new TableColumn<>("Name");
    TableColumn<DisplayCommand, String> comValueCol = new TableColumn<>("Command");

    // The factory uses reflection to set the column names
    comNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    comValueCol.setCellValueFactory(new PropertyValueFactory<>("signature"));
    comCol.getColumns().addAll(
        comNameCol, comValueCol
    );

    commandsTable.getColumns().add(comCol);
    commandsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn varCol = new TableColumn("Variables");
    TableColumn<DisplayVariable, String> varNameCol = new TableColumn<>("Identifier");
    TableColumn<DisplayVariable, String> varValueCol = new TableColumn<>("Value");
    varCol.getColumns().addAll(varNameCol, varValueCol);
    varNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    varValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

    variablesTable.getColumns().addAll(varCol);
    variablesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    variablesTable.setPrefHeight(TABLE_SIZE);
    commandsTable.setPrefHeight(TABLE_SIZE);
  }

  private void createMockData() {
    commandsTable.getItems().addAll(
        new DisplayCommand("name", "signature"),
        new DisplayCommand("name1", "signature1")
    );
    variablesTable.getItems().addAll(
        new DisplayVariable("name", "value"),
        new DisplayVariable("name1", "value1")
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
