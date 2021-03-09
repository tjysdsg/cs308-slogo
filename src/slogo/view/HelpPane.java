package slogo.view;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList; import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HelpPane extends Pane {
  public final static String RESOURCE_PACKAGE = View.RESOURCE_PACKAGE;
  public final static String RESOURCE_FOLDER = View.RESOURCE_FOLDER;
  private static final int SIZE = 200;
  private final static String SEARCH_ICON = "images/searchicon.png";
  private final static String CLEAR_ICON = "images/clearicon.png";
  private final static String HELP_ICON = "images/helpicon.png";
  private VBox vbox;
  private GridPane searchBar;
  private GridPane displayWindow;
  private Label helpLabel;
  private ListView<ChoiceBox<String>> list;
  ResourceBundle resources;
  private  ChoiceBox<String> commandList = new ChoiceBox<>();
  private  ChoiceBox<String> queriesList = new ChoiceBox();
  private  ChoiceBox<String> mathList = new ChoiceBox();
  private  ChoiceBox<String> booleanList = new ChoiceBox();
  private  ChoiceBox<String> variablesList = new ChoiceBox();
  private  ChoiceBox<String> userCommandList = new ChoiceBox();
  ViewController vcon;



  public HelpPane(ViewController vcon ){
    this.vcon = vcon;
    createHelpWindow();
    getChildren().add(vbox);
    
  }

  private void createHelpWindow() {
    createDisplayWindow();
    createSearchBar();
    createList();
    createDisplayLanguages();
    vbox = new VBox(displayWindow, searchBar, list);
    vbox.setPadding(new Insets(0,0, 0, 2));
    vbox.setSpacing(5);
  }

  private void createList() {
    list = new ListView<>();
    ObservableList<ChoiceBox<String>> items =FXCollections.observableArrayList (commandList,queriesList,mathList,booleanList, variablesList, userCommandList );
    list.setItems(items);
    list.setPrefSize(SIZE, 3*SIZE);


  }

  public void createDisplayLanguages() {
    resources = vcon.setBundle();
    helpLabel.setText(resources.getString("helpTitle"));
    commandList.getItems().clear();
    commandList.getItems().addAll(resources.getString("commandList").split(","));
    queriesList.getItems().clear();
    queriesList.getItems().addAll(resources.getString("queriesList").split(","));
    mathList.getItems().clear();
    mathList.getItems().addAll(resources.getString("mathList").split(","));
    booleanList.getItems().clear();
    booleanList.getItems().addAll(resources.getString("booleanList").split(","));
    variablesList.getItems().clear();
    variablesList.getItems().addAll(resources.getString("variableList").split(","));
    userCommandList.getItems().clear();
    userCommandList.getItems().addAll(resources.getString("userList").split(","));
    addDefaultChoiceBoxTest();
  }

  private void addDefaultChoiceBoxTest() {
    commandList.setValue(resources.getString("command"));
    queriesList.setValue(resources.getString("queries"));
    mathList.setValue(resources.getString("math"));
    booleanList.setValue(resources.getString("boolean"));
    variablesList.setValue(resources.getString("variable"));
    userCommandList.setValue(resources.getString("user"));


  }

  private void createDisplayWindow() {
    displayWindow = new GridPane();
    ImageView helpIcon = new ImageView(getClass().getResource(RESOURCE_FOLDER + HELP_ICON).toExternalForm());
    helpIcon.setFitHeight(.3*SIZE);
    helpIcon.setFitWidth(.4*SIZE);
    helpLabel = new Label();
    helpLabel.setId("helpLabel");
    displayWindow.add(helpIcon, 0, 0);
    displayWindow.add(helpLabel, 1, 0);
    displayWindow.setHgap(10);


  }

  private void createSearchBar() {
    searchBar= new GridPane();
    TextField search = new TextField();
    search.setPrefWidth(.8*SIZE);
    ImageView searchIcon = new ImageView(getClass().getResource(RESOURCE_FOLDER + SEARCH_ICON).toExternalForm());
    searchIcon.setFitWidth(.1*SIZE);
    searchIcon.setFitHeight(.1*SIZE);
    ImageView clearIcon = new ImageView(getClass().getResource(RESOURCE_FOLDER + CLEAR_ICON).toExternalForm());
    clearIcon.setFitWidth(.1*SIZE);
    clearIcon.setFitHeight(.1*SIZE);
    searchBar.add(searchIcon, 0, 0);
    searchBar.add(search, 1,0);
    searchBar.add(clearIcon, 2, 0);

    //vbox.setId("helpWindow");


  }






}
