package slogo.view;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import slogo.Main;
import util.DukeApplicationTest;



/**
 * Tests for HelpPane
 */

public class HelpPaneTest extends  DukeApplicationTest {
  private Main myApp;
  private Button myBackButton;
  private Pane helpPane;

  private ChoiceBox<String> commandList;
  String itemOne;



  @Override
  public void start(Stage stage){
    myApp = new Main();
    myApp.start(stage);

     helpPane = lookup("#helpPane").query();
  }

  @Test
  void testCommandListAction(){
    commandList = (ChoiceBox<String>) helpPane.lookup("#commandList");
    itemOne = "forward|fd";
    String forwardtext = "moves turtle forward in its current heading by pixels distance returns distance turtle moved (the value of pixels)";
    assertEquals(itemOne,commandList.getItems().get(0));
    select(commandList, itemOne);
    sleep(1, TimeUnit.SECONDS);
    Label helpText = (Label) helpPane.lookup("#helpText");
    assertEquals(forwardtext, helpText.getText());
  }


  @Test
  void testBackButton(){
    commandList = (ChoiceBox<String>) helpPane.lookup("#commandList");
    itemOne = "forward|fd";
    myBackButton= (Button) helpPane.lookup("#backButton");
    assertNull(myBackButton);
    select(commandList, itemOne);
    sleep(1, TimeUnit.SECONDS);

    myBackButton= (Button) helpPane.lookup("#backButton");

    clickOn(myBackButton);
    assertEquals(itemOne,commandList.getItems().get(0));

  }




}
