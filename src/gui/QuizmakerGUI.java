package gui;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


import entity.Answers;
import entity.Question;
import entity.QuestionType;
import entity.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class QuizmakerGUI {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	
	private ObservableList<Integer> pointObList ;
	private ObservableList<String> questTypeObList ;
	private ObservableList<String> testList = FXCollections.observableArrayList();
	private Test test;
	private QuestionType questType = new QuestionType();
	private Question quest = new Question();
	private Answers answer;
	private int points;
	private String questionName;
	
	private ListView<String> listViewClassTest = new ListView<String>();
	private TextArea questFeedback = new TextArea();
	private TextArea writeQuestionTxt = new TextArea();
	private TextArea writeAnswerTxt = new TextArea();
	private TextField answer1 = new TextField();
	private TextField answer2 = new TextField();
	private TextField answer3 = new TextField();
	private TextField answer4 = new TextField();
	private TextField startTestTxt = new TextField();
	private TextField stopTestTxt = new TextField();
	private TextField setTitleTxt = new TextField();
	private TextField setTitleTxtQ = new TextField();
	private RadioButton radBut1 = new RadioButton();
	private RadioButton radBut2 = new RadioButton();
	private RadioButton radBut3 = new RadioButton();
	private RadioButton radBut4 = new RadioButton();
	private CheckBox feedbackCheck = new CheckBox();
	private ToggleGroup butGroup = new ToggleGroup();
	private Button newQuestBut = new Button("New Question");	
	private Button saveQuestBut = new Button("Save Question");
	private Button newTestBut = new Button("New Test");	
	private Button saveTestBut = new Button("Save Test");
	private Button editTestBut = new Button("Edit Test");
	private Button okBut = new Button("OK");
	private ComboBox<String> questCmbBox = new ComboBox<String>();
	private ComboBox<String> testCmbBox = new ComboBox<String>();
	private ComboBox<String> setTypeCmbBox = new ComboBox<String>();
	private ComboBox<Integer> pointCmbBox = new ComboBox<Integer>();
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
	private Label startLbl = new Label("Test start \n YY/MM/DD-00:00");
	private Label stopLbl = new Label("Test stop \n YY/MM/DD-00:00");
	private Scene popUpScene;
	private Scene popUpScene2;
	private Stage popUpStage = new Stage();
	private Stage popUpStage2 = new Stage();
	private BorderPane popUpPaneNewT = new BorderPane();
	private BorderPane popUpPaneNewQ = new BorderPane();
	
	public QuizmakerGUI(){	}
	
	public QuizmakerGUI(Test test) {
		this.test = test;
		
	}
	
	public BorderPane showPane(){
		
		emf = Persistence.createEntityManagerFactory("Testverktyg");
		em = emf.createEntityManager();
		
		List <Test> tempLista = (List <Test>) em.createQuery("select t from Test t").getResultList();
		testCmbBox.setItems(testList);		
		for( int i = 0; i < tempLista.size(); i++){			
			testList.add(tempLista.get(i).getTestName());			
		}		
		listViewClassTest.setItems(testList);
		
		//Skapar lista och combobox till poängsättare
		pointObList = FXCollections.observableArrayList(1, 2, 3, 4, 5);
		pointCmbBox = new ComboBox<Integer>(pointObList);
		//Lägger in två typer av olika frågor
		questTypeObList = FXCollections.observableArrayList("4-Choice", "Essay");
		setTypeCmbBox = new ComboBox<String>(questTypeObList);
		
				
		
	    //Graphiccomponents to the questionmaker	
		BorderPane root = new BorderPane();
		questFeedback.setVisible(false);
		
		fieldsVBox.getChildren().addAll(answer1, answer2, answer3, answer4, questFeedback);
		answer1.setPromptText("Answer one");
		answer2.setPromptText("Answer two");
		answer3.setPromptText("Answer three");
		answer4.setPromptText("Answer four");
		writeQuestionTxt.setPromptText("Write question here");
		questFeedback.setPromptText("Write student feedback here");
		writeAnswerTxt.setPromptText("Write answer here");
		setTitleTxt.setPromptText("Test Name");
		setTitleTxtQ.setPromptText("Question Name");
		fieldsVBox.setPrefWidth(480.0);
		//Organizing my radiobuttons
		radBut1.setToggleGroup(butGroup);
		radBut2.setToggleGroup(butGroup);
		radBut3.setToggleGroup(butGroup);
		radBut4.setToggleGroup(butGroup);		
		radButsVBox.getChildren().addAll(radBut1, radBut2, radBut3, radBut4, feedbackCheck);		
		// Button VBox right of textarea		
		butsAndCmbTest.getChildren().addAll(newTestBut, editTestBut, testCmbBox);
		// Button VBox right of textarea
		butsAndCmbQuest.getChildren().addAll(newQuestBut, saveQuestBut, questCmbBox);
		//HBox right of textarea where u type in a question
		butsAndtxtAreaHBox.getChildren().addAll(writeQuestionTxt, butsAndCmbQuest, butsAndCmbTest);
		butsAndtxtAreaHBox.setSpacing(20);
		//RadioButtons and textfields where u write the answers HBox
		fieldsAndRadsHBox.getChildren().addAll(radButsVBox, fieldsVBox, listViewClassTest);		
		radButsVBox.setSpacing(10);
		//Add my HBoxes to a big VBox
		componentsVBox.getChildren().addAll(butsAndtxtAreaHBox, fieldsAndRadsHBox);
		componentsVBox.setSpacing(80);
		//Left side VBox Popup NewTest
		startLblVBox.getChildren().addAll(setTitleTxt, startLbl, startTestTxt);
		//Left side VBox popup NewQuest
		setTitleVBox.getChildren().addAll(setTitleTxtQ);
		//Right side VBox NewTest
		stopLblVBox.getChildren().addAll(stopLbl, stopTestTxt, saveTestBut);
		//HBox for VBoxes NewTest popup
		popUpHBox.getChildren().addAll(startLblVBox, stopLblVBox);
		//HBox for VBox and cmbBox in newquest popup
		newQuestHBox.getChildren().addAll(setTitleVBox, setTypeCmbBox, pointCmbBox);
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
		
		//Changes the interface of the question input
		setTypeCmbBox.setOnAction(event -> {
			if(setTypeCmbBox.getValue().equals("Essay")){
				fieldsAndRadsHBox.getChildren().clear();
				fieldsAndRadsHBox.getChildren().addAll(writeAnswerTxt, listViewClassTest);
			}
			if(setTypeCmbBox.getValue().equals("4-Choice")){
				fieldsAndRadsHBox.getChildren().clear();
				fieldsAndRadsHBox.getChildren().addAll(radButsVBox, fieldsVBox, listViewClassTest);
			}
		});
		
		//Save the question to a test in the database
		saveQuestBut.setOnAction(event -> {
			em.getTransaction().begin();
			points = pointCmbBox.getValue();
			quest.setPoints(points);
			quest.setDirectFeedback(feedbackCheck.isSelected());
			questionName = setTitleTxtQ.getText();
			quest.setQuestionTitle(questionName);
			quest.setQuestionText(writeQuestionTxt.getText());
			
			quest.setQuestionType(questType);
			
			answer = new Answers(quest);
			answer.addAnswer(answer1.getText());
			answer.addAnswer(answer2.getText());
			answer.addAnswer(answer3.getText());
			answer.addAnswer(answer4.getText());
			em.persist(answer);
			em.persist(quest);
			em.getTransaction().commit();
		});
		setTypeCmbBox.setOnAction(event -> {
			em.getTransaction().begin();
			questType.setQuestionType("4-Choice");
			em.persist(questType);
			em.getTransaction().commit();
		});
		
		//Save the test to the database
		saveTestBut.setOnAction(event -> {
			test = new Test();
			em.getTransaction().begin();
			test.setTestName(setTitleTxt.getText());
			setTitleTxt.setText("");
			test.setTestStart(startTestTxt.getText());
			startTestTxt.setText("");
			test.setTestEnd(stopTestTxt.getText());
			stopTestTxt.setText("");
			popUpStage.close();			
			em.persist(test);			
			em.getTransaction().commit();
		});
		
		newTestBut.setOnAction(event -> {			
			popUpStage.showAndWait();			
		});
		
		newQuestBut.setOnAction(event -> {
			popUpStage2.showAndWait();			
		});
		
		feedbackCheck.setOnAction(event -> {			
			if(feedbackCheck.isSelected()){
				questFeedback.setVisible(true);
				
			}
			else{
				questFeedback.setVisible(false);}
				
		});
		
		
		return root;		
	}		
}