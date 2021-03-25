package slogo.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.jfoenix.controls.JFXListView;
import java.util.concurrent.TimeUnit;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import slogo.Main;
import slogo.records.DisplayVariable;
import util.DukeApplicationTest;



public class EnvironmentPaneTest extends DukeApplicationTest {


  private Main myApp;
  private Pane environmentPane;
  private Pane commandPane;
  private TextArea codeArea;
  private Button run;
  private JFXListView<Label> prevCommands;
  private TableView<DisplayVariable> variablesTable;
  private TitledPane variablesToggle;




  @Override
  public void start(Stage stage) {
    myApp = new Main();
    myApp.start(stage);
    environmentPane = lookup("#environmentPane").query();
    commandPane = lookup("#commandPane").query();
  }

  @Test
  void testCodeArea() {
    codeArea = (TextArea) commandPane.lookup("#codeArea");
    String expected = "Forward 50";
    writeTo(codeArea, expected);
    assertEquals(expected, codeArea.getText());
    expected = "back 200\n# WITH A COMMENT IN BETWEEN \n fd 100";
    writeTo(codeArea, expected);
    assertEquals(expected, codeArea.getText());
    expected = "frente 60";
    writeTo(codeArea, expected);
    assertEquals(expected, codeArea.getText());

  }

  @Test
  void testPreviousCommandList() throws InterruptedException {
    codeArea = (TextArea) commandPane.lookup("#codeArea");
    String expected = "Forward 50";
    run = (Button) commandPane.lookup("#runButton");
    prevCommands = (JFXListView<Label>) environmentPane.lookup("#prevCommands");
    prevCommands.getItems().add(new Label(expected));
    assertEquals(expected, prevCommands.getItems().get(0).getText());
  }

//  @Test
//  void testVariablePane(){
//    variablesTable = (TableView<DisplayVariable>) environmentPane.lookup("#variableTable");
//    DisplayVariable a = new DisplayVariable(":y", "50");
//    variablesTable.getItems().add(a);
//    assertNotNull(doubleClickOn(variablesTable));
//  }
//
//  @Test
//  void testToggle(){
//    variablesToggle = (TitledPane) environmentPane.lookup("#varToggle");
//    assertNotNull(clickOn(variablesToggle));
//  }



}