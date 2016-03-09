package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class QuizmakerGUI {
	
	private TextArea writeQuestionTxt = new TextArea();
	private TextField answer1 = new TextField();
	private TextField answer2 = new TextField();
	private TextField answer3 = new TextField();
	private TextField answer4 = new TextField();
	private TextField startTestTxt = new TextField();
	private TextField stopTestTxt = new TextField();
	private TextField setTitleTxt = new TextField();
	private TextField setTitleTxt2 = new TextField();
	private RadioButton radBut1 = new RadioButton();
	private RadioButton radBut2 = new RadioButton();
	private RadioButton radBut3 = new RadioButton();
	private RadioButton radBut4 = new RadioButton();
	private ToggleGroup butGroup = new ToggleGroup();
	private Button newQuestBut = new Button("New Question");	
	private Button saveQuestBut = new Button("Save Question");
	private Button newTestBut = new Button("New Test");	
	private Button saveTestBut = new Button("Save Test");
	private Button changeTypeQuest = new Button("Change Question type");
	private Button editTestBut = new Button("Edit Test");
	private ComboBox<String> questCmbBox = new ComboBox<String>();
	private ComboBox<String> testCmbBox = new ComboBox<String>();
	private ComboBox<String> setTypeCmbBox = new ComboBox<String>();
	private VBox fieldsVBox = new VBox();
	private VBox radButsVBox = new VBox();	
	private VBox butsAndCmbQuest = new VBox();
	private VBox butsAndCmbTest = new VBox();
	private VBox componentsVBox = new VBox();	
	private VBox startLblVBox = new VBox();
	private VBox stopLblVBox = new VBox();
	private VBox setTitleVBox = new VBox();
	private HBox popUpHBox = new HBox();
	private HBox newQuestHBox = new HBox();
	private HBox fieldsAndRadsHBox = new HBox();
	private HBox butsAndtxtAreaHBox = new HBox();	
	private Label titleLbl = new Label("Test title");
	private Label titleLbl2 = new Label("Test title");
	private Label startLbl = new Label("Test start /n YY/MM/DD-00:00");
	private Label stopLbl = new Label("Test stop /n YY/MM/DD-00:00");
	private Scene popUpScene;
	private Scene popUpScene2;
	private Stage popUpStage = new Stage();
	private Stage popUpStage2 = new Stage();
	private BorderPane popUpPaneNewT = new BorderPane();
	private BorderPane popUpPaneNewQ = new BorderPane();
	
	public BorderPane showPane(){
	// Graphiccomponents to the questionmaker	
		BorderPane root = new BorderPane();
		fieldsVBox.getChildren().addAll(answer1, answer2, answer3, answer4);
		fieldsVBox.setPrefWidth(480.0);
		//Organizing my radiobuttons
		radBut1.setToggleGroup(butGroup);
		radBut2.setToggleGroup(butGroup);
		radBut3.setToggleGroup(butGroup);
		radBut4.setToggleGroup(butGroup);
		radButsVBox.getChildren().addAll(radBut1, radBut2, radBut3, radBut4);
		// Button VBox right of textarea
		butsAndCmbTest.getChildren().addAll(newTestBut, editTestBut, saveTestBut, testCmbBox);
		// Button VBox right of textarea
		butsAndCmbQuest.getChildren().addAll(newQuestBut, changeTypeQuest, saveQuestBut, questCmbBox);
		//HBox right of textarea where u type in a question
		butsAndtxtAreaHBox.getChildren().addAll(writeQuestionTxt, butsAndCmbQuest, butsAndCmbTest);
		butsAndtxtAreaHBox.setSpacing(20);
		//RadioButtons and textfields where u write the answers HBox
		fieldsAndRadsHBox.getChildren().addAll(radButsVBox, fieldsVBox);		
		radButsVBox.setSpacing(10);
		//Add my HBoxes to a big VBox
		componentsVBox.getChildren().addAll(butsAndtxtAreaHBox, fieldsAndRadsHBox);
		componentsVBox.setSpacing(80);
		//Left side VBox Popup NewTest
		startLblVBox.getChildren().addAll(titleLbl, setTitleTxt, startLbl, startTestTxt);
		//Left side VBox popup NewQuest
		setTitleVBox.getChildren().addAll(titleLbl2, setTitleTxt2);
		//Right side VBox NewTest
		stopLblVBox.getChildren().addAll(stopLbl, stopTestTxt);
		//HBox for VBoxes NewTest popup
		popUpHBox.getChildren().addAll(startLblVBox, stopLblVBox);
		//HBox for VBox and cmbBox in newquest popup
		newQuestHBox.getChildren().addAll(setTitleVBox, setTypeCmbBox);
		//Two borderPanes for popups
		popUpPaneNewQ.setTop(newQuestHBox);
		popUpPaneNewT.setBottom(popUpHBox);
		//Two scenes for popups
		popUpScene2 = new Scene(popUpPaneNewQ, 300, 200);
		popUpScene = new Scene(popUpPaneNewT, 300, 200);
		//Set the scenes for both popups
		popUpStage.setScene(popUpScene);
		popUpStage2.setScene(popUpScene2);		
		root.setMargin(componentsVBox, new Insets(12,12,12,12));
		root.setCenter(componentsVBox);
		Scene scene = new Scene(root, 1000, 600);
								
		newTestBut.setOnAction(event -> {			
			popUpStage.showAndWait();			
		});
		
		newQuestBut.setOnAction(event -> {
			popUpStage2.showAndWait();			
		});
		
		return root;		
	}		
}