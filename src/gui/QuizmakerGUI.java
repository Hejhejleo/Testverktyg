package gui;




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
	private ObservableList<String> questList = FXCollections.observableArrayList();
 	private Test test;
 	private AssignTest assignTest = new AssignTest();
	private QuestionType questType = new QuestionType();
	private Question quest;
	private Answers answer;
	private int points;
	private int correctAnswerIndex;
	private String questionName;	
	private ListView<String> listViewQuest = new ListView<String>();
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
	private Button changeQuestBut = new Button("Save Changes");
	private Button newTestBut = new Button("New Test");	
	private Button saveTestBut = new Button("Save Test");
	private Button assignTestBut = new Button("Assign Test");
	private Button deleteTestBut = new Button("Delete Test");
	private Button okBut = new Button("OK");
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
	private Label startLbl = new Label("Test start");
	private Label stopLbl = new Label("Test stop");
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
		
		List <Test> tempTestLista = (List <Test>) em.createQuery("select t from Test t").getResultList();
		testCmbBox.setPromptText("Select test");
		testCmbBox.setItems(testList);
		for( int i = 0; i < tempTestLista.size(); i++){			
			testList.add(tempTestLista.get(i).getTestName());			
		}
		
		//Skapar lista och combobox till poängsättare
		pointObList = FXCollections.observableArrayList(1, 2, 3, 4, 5);
		pointCmbBox = new ComboBox<Integer>(pointObList);
		//Lägger in två typer av olika frågor
		questTypeObList = FXCollections.observableArrayList("Radiobuttons", "Fritext");
		setTypeCmbBox = new ComboBox<String>(questTypeObList);
								
	    //Graphiccomponents to the questionmaker	
		BorderPane root = new BorderPane();
		questFeedback.setVisible(false);
		listViewQuest.setVisible(false);
		fieldsVBox.getChildren().addAll(answer1, answer2, answer3, answer4, questFeedback);
		fieldsVBox.setVisible(false);
		answer1.setPromptText("Answer one");
		answer2.setPromptText("Answer two");
		answer3.setPromptText("Answer three");
		answer4.setPromptText("Answer four");
		writeQuestionTxt.setPromptText("Write question here");
		questFeedback.setPromptText("Write student feedback here");
		writeAnswerTxt.setPromptText("Write answer here");
		writeAnswerTxt.setVisible(false);
		writeAnswerTxt.setPrefSize(480, 100);
		setTitleTxt.setPromptText("Test Name");
		setTitleTxtQ.setPromptText("Question Name");
		fieldsVBox.setPrefWidth(480.0);
		//Organizing my radiobuttons
		radBut1.setToggleGroup(butGroup);
		radBut2.setToggleGroup(butGroup);
		radBut3.setToggleGroup(butGroup);
		radBut4.setToggleGroup(butGroup);		
		radButsVBox.getChildren().addAll(radBut1, radBut2, radBut3, radBut4);
		radButsVBox.setVisible(false);
		// Button VBox right of textarea		
		butsAndCmbTest.getChildren().addAll(newTestBut, deleteTestBut, assignTestBut, testCmbBox);
		// Button VBox right of textarea
		butsAndCmbQuest.getChildren().addAll(newQuestBut, saveQuestBut, changeQuestBut);
		//HBox right of textarea where u type in a question
		butsAndtxtAreaHBox.getChildren().addAll(writeQuestionTxt, butsAndCmbQuest, butsAndCmbTest);
		butsAndtxtAreaHBox.setSpacing(20);
		writeQuestionTxt.setVisible(false);
		//RadioButtons and textfields where u write the answers HBox
		fieldsAndRadsHBox.getChildren().addAll(radButsVBox, fieldsVBox, listViewQuest);		
		radButsVBox.setSpacing(10);
		//Add my HBoxes to a big VBox
		componentsVBox.getChildren().addAll(butsAndtxtAreaHBox, fieldsAndRadsHBox);
		componentsVBox.setSpacing(80);
		//Left side VBox Popup NewTest
		startLblVBox.getChildren().addAll(setTitleTxt, startLbl, startTestTxt);
		//Left side VBox popup NewQuest
		setTitleVBox.getChildren().addAll(setTitleTxtQ);
		//Right side VBox NewTest
		stopTestTxt.setPromptText("YY/MM/DD-00:00");
		startTestTxt.setPromptText("YY/MM/DD-00:00");
		stopLblVBox.getChildren().addAll(stopLbl, stopTestTxt, saveTestBut);
		//HBox for VBoxes NewTest popup
		popUpHBox.getChildren().addAll(startLblVBox, stopLblVBox);
		//HBox for VBox and cmbBox in newquest popup
		setTypeCmbBox.setPromptText("Question type");
		pointCmbBox.setPromptText("Point value");
		newQuestHBox.getChildren().addAll(setTitleVBox, setTypeCmbBox, pointCmbBox, okBut);
		//Two borderPanes for popups
		popUpPaneNewQ.setTop(newQuestHBox);
		popUpPaneNewT.setTop(popUpHBox);
		//Two scenes for popups
		popUpScene2 = new Scene(popUpPaneNewQ, 400, 50);
		popUpScene = new Scene(popUpPaneNewT, 300, 200);
		//Set the scenes for both popups
		popUpStage.setTitle("Set Test title");
		popUpStage2.setTitle("Create New Question");
		popUpStage.setScene(popUpScene);
		popUpStage2.setScene(popUpScene2);
		
				
		root.setMargin(componentsVBox, new Insets(12,12,12,12));
		root.setCenter(componentsVBox);
		Scene scene = new Scene(root, 1000, 600);
			
		testCmbBox.setOnAction(event -> {			
			listViewQuest.getItems().clear();
			//Gets the selected string from combobox and insert into a test variable
			test = tempTestLista.get(testCmbBox.getSelectionModel().getSelectedIndex());
			//Query for getting the question according by the testname
			Test tempTest = (Test) em.createQuery("select t from Test t where t.testName ='" + testCmbBox.getValue()+"'").getSingleResult();
			
			for (Question q : tempTest.getQuestions()){
				questList.add(q.getQuestionTitle());
			}
			listViewQuest.setVisible(true);
			listViewQuest.setItems(questList);			
		});
		saveQuestBut.setOnMouseReleased(event2->{
			listViewQuest.getItems().clear();
			//Gets the selected string from combobox and insert into a test variable
			test = tempTestLista.get(testCmbBox.getSelectionModel().getSelectedIndex());
			//Query for getting the question according by the testname
			Test tempTest = (Test) em.createQuery("select t from Test t where t.testName ='" + testCmbBox.getValue()+"'").getSingleResult();
			
			for (Question q : tempTest.getQuestions()){
				questList.add(q.getQuestionTitle());
			}
			listViewQuest.setVisible(true);
			listViewQuest.setItems(questList);
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
			test.addQuestion(quest);			
			answer = new Answers(quest);
			quest.setAnswer(answer);
			if(radBut1.isSelected()){
				correctAnswerIndex = 0;
				System.out.println(correctAnswerIndex);
			}
			else if(radBut2.isSelected()){
				correctAnswerIndex = 1;
				System.out.println(correctAnswerIndex);
			}
			else if(radBut3.isSelected()){
				correctAnswerIndex = 2;
				System.out.println(correctAnswerIndex);
			}
			else if(radBut4.isSelected()){
				correctAnswerIndex = 3;
				System.out.println(correctAnswerIndex);				
			}
			if(setTypeCmbBox.getValue().equals("Fritext")){
				answer.addAnswer(writeAnswerTxt.getText());								
			}
			else if(setTypeCmbBox.getValue().equals("Radiobuttons")){
			answer.setCorrectAnswer(correctAnswerIndex);			
			answer.addAnswer(answer1.getText());
			answer.addAnswer(answer2.getText());
			answer.addAnswer(answer3.getText());
			answer.addAnswer(answer4.getText());
			}
			answer1.setText("");
			answer2.setText("");
			answer3.setText("");
			answer4.setText("");
			setTitleTxtQ.setText("");
			writeQuestionTxt.setText("");
			writeAnswerTxt.setText("");
			questFeedback.setText("");
			radBut1.setSelected(false);
			radBut2.setSelected(false);
			radBut3.setSelected(false);
			radBut4.setSelected(false);
			feedbackCheck.setSelected(false);
			em.persist(answer);
			em.persist(quest);			
			em.getTransaction().commit();
		});
		
		//Delete the test chosen in the testcombobox
		deleteTestBut.setOnAction(event -> {
			em.getTransaction().begin();
			 Test t = (Test) em.createQuery("select t from Test t where t.testName ='" + testCmbBox.getValue()+"'").getSingleResult();			 
			 
			 for (Question q : t.getQuestions()){
				 q.setTest(null);
				 em.persist(q);
			 }			 
			 t.getQuestions().clear();
			 em.persist(t);
			 em.remove(t);
			 em.getTransaction().commit();
		});
		
		//Shows the selected question in the questionmaker GUI
		//Save the changes made to the selected question
		listViewQuest.setOnMouseClicked(event -> {
			
			Question tempSelcQuest = (Question) em.createQuery("select t from Question t where t.questionTitle ='" + listViewQuest.getSelectionModel().getSelectedItem() +"'").getSingleResult();
			
				if(tempSelcQuest.getquestionType().getQuestionType().equals("Fritext")){
					fieldsAndRadsHBox.getChildren().clear();
					fieldsAndRadsHBox.getChildren().addAll(writeAnswerTxt, listViewQuest);				
				}
				else if (tempSelcQuest.getquestionType().getQuestionType().equals("Radiobuttons")){
					fieldsAndRadsHBox.getChildren().clear();
					fieldsAndRadsHBox.getChildren().addAll(radButsVBox, fieldsVBox, listViewQuest);
				}			
				
			fieldsVBox.setVisible(true);
			radButsVBox.setVisible(true);
			writeQuestionTxt.setVisible(true);
			writeAnswerTxt.setVisible(true);
			
			List<Answers> tempAnsView =  (List<Answers>) em.createQuery("select t from Answers t").getResultList();
			
			for(Answers a: tempAnsView){
				if(a.getQuestion().getQuestionTitle().equals(listViewQuest.getSelectionModel().getSelectedItem())){
					System.out.println();
					if(a.getCorrectAnswerIndex() == 0){
						radBut1.setSelected(true);
						System.out.println(a.getCorrectAnswer());
					}
					else if(a.getCorrectAnswerIndex() == 1){
						radBut2.setSelected(true);
						System.out.println(a.getCorrectAnswer());
					}
					else if(a.getCorrectAnswerIndex() == 2){
						radBut3.setSelected(true);
						System.out.println(a.getCorrectAnswer());
					}
					else if(a.getCorrectAnswerIndex() == 3){
						radBut4.setSelected(true);
						System.out.println(a.getCorrectAnswer());
					}
					
					if(tempSelcQuest.getquestionType().getQuestionType().equals("Fritext")){
						writeAnswerTxt.setText(a.getAnswerList().get(0));
						writeQuestionTxt.setText(a.getQuestion().getQuestionText());
					}
					
					else if (tempSelcQuest.getquestionType().getQuestionType().equals("Radiobuttons")){
						answer1.setText(a.getAnswerList().get(0));
						answer2.setText(a.getAnswerList().get(1));
						answer3.setText(a.getAnswerList().get(2));
						answer4.setText(a.getAnswerList().get(3));
						writeQuestionTxt.setText(a.getQuestion().getQuestionText());
						
					}					
					
					changeQuestBut.setOnAction(event2 -> {
						a.getAnswerList().clear();
						if(tempSelcQuest.getquestionType().getQuestionType().equals("Radiobuttons")){
							em.getTransaction().begin();
							a.addAnswer(answer1.getText());
							a.addAnswer(answer2.getText());
							a.addAnswer(answer3.getText());
							a.addAnswer(answer4.getText());
							a.getQuestion().setQuestionText(writeQuestionTxt.getText());
							em.persist(a);
							em.getTransaction().commit();
						}
						else if (tempSelcQuest.getquestionType().getQuestionType().equals("Fritext")){
							em.getTransaction().begin();
							a.addAnswer(writeAnswerTxt.getText());
							a.getQuestion().setQuestionText(writeQuestionTxt.getText());
							em.persist(a);
							em.getTransaction().commit();
						}					
												
					});					
				}				
			}			
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
			testList.add(test.getTestName());
			tempTestLista.add(test);
			testCmbBox.getSelectionModel().select(testList.size()-1);
		});
		//Opens a newtest popup
		newTestBut.setOnAction(event -> {
			fieldsVBox.setVisible(false);
			radButsVBox.setVisible(false);
			writeQuestionTxt.setVisible(false);
			writeAnswerTxt.setVisible(false);							
			popUpStage.showAndWait();			
		});
		//Closes the newQuestion popup and sets values to the database
		okBut.setOnAction(event -> {
			//Get a questType from questionType
			QuestionType tempQuestType = (QuestionType) em.createQuery("select t from QuestionType t where t.questionType ='" + setTypeCmbBox.getValue()+"'").getSingleResult();
			em.getTransaction().begin();
			//Assign our questtype variable to the fetched qType and create connect the two entities
			questType = tempQuestType;
			questType.addQuestion(quest);
			quest.setQuestionType(questType);
			em.persist(questType);
			em.persist(quest);
			em.getTransaction().commit();			
			if(setTypeCmbBox.getValue().equals("Fritext")){
				fieldsAndRadsHBox.getChildren().clear();
				fieldsAndRadsHBox.getChildren().addAll(writeAnswerTxt, listViewQuest);
			}
			else if(setTypeCmbBox.getValue().equals("Radiobuttons")){
				fieldsAndRadsHBox.getChildren().clear();
				fieldsAndRadsHBox.getChildren().addAll(radButsVBox, fieldsVBox, listViewQuest);
			}											
			popUpStage2.close();
		});
		//Assigns test to the different classes
		assignTestBut.setOnAction(event -> {			
			assignTest.doAssign(test, em);			
		});
		//Opens new question popup
		newQuestBut.setOnAction(event -> {
			fieldsVBox.setVisible(true);
			radButsVBox.setVisible(true);
			writeQuestionTxt.setVisible(true);
			writeAnswerTxt.setVisible(true);
			quest = new Question(points, true, null, null, test, null, answer);		
			popUpStage2.show();	
		});
		/*
		feedbackCheck.setOnAction(event -> {			
			if(feedbackCheck.isSelected()){
				questFeedback.setVisible(true);				
			}
			else{
				questFeedback.setVisible(false);}				
		});*/
		
		
		return root;		
	}		
}