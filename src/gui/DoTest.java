package gui;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.Answers;
import entity.Question;
import entity.StudentAnswer;
import entity.Test;
import entity.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DoTest extends Application {
	AnchorPane centerPaneDT;
	String testNameDT;
	int questionNumberDT = 0;
	Answers answerDT;
	Boolean goneThroughDT = false;
	List<Pane> canvasesDT = new ArrayList<>();
	List<Question> questionsDT = new ArrayList<>();
	List<String> testOverviewQDT = new ArrayList<>();
	List<String> testOverviewADT = new ArrayList<>();
	User userDT;

	@Override
	public void start(Stage centerPaneDT) throws Exception {
		this.centerPaneDT = centerPane;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();
		Query test = em.createNamedQuery("get tests");
		testNameDT = "Test i JPA";
		test.setParameter("name", testNameDT);
		System.out.println("---------------:::Hello:::-----------------");
		// get questions from DB
		System.out.println("---------------:::1:::-----------------");
		List<Test> testsList = new ArrayList<>(test.getResultList());
		System.out.println("---------------:::2:::-----------------");
		List<Answers> answersList = new ArrayList<>(em.createQuery("select a from Answers a").getResultList());

		System.out.println("---------------:::3:::-----------------");
		Test currentTest = new Test();
		currentTest = testsList.get(0);
		System.out.println("---------------:::4:::-----------------");

		for (int i = 0; i < currentTest.getQuestions().size(); i++) {
			questionsDT.add(currentTest.getQuestions().get(i));
			System.out.println(questionsDT.get(i).getQuestionText());
		}
		System.out.println("---------------:::5:::-----------------");

		
		//Make array-size to fit test
		for(int i=0;i<questionsDT.size();i++){
			testOverviewADT.add(null);
			testOverviewQDT.add(null);
			System.out.println(testOverviewADT.get(i));
		}
		// RootGrid
		
		

		// Make scenes
		System.out.println("---------------:::6:::-----------------");
		for (int i = 0; i <= (questionsDT.size() - 1); i++) {
			for (int j = 0; j <= (answersList.size() - 1); j++) {
				if (answersList.get(j).getQuestion() == questionsDT.get(i)) {
					answerDT = answersList.get(j);
				}

			}
			TextArea questionTextArea = new TextArea();
			ToggleGroup group = new ToggleGroup();
			GridPane root = new GridPane();
			System.out.println("---------------:::7:::-----------------");

			Scene scene = new Scene(root, 800, 350);

			// TopLeft Corner
			VBox topLeft = new VBox();
			Text titleText = new Text(questionsDT.get(i).getQuestionTitle());
			Text questionText = new Text(questionsDT.get(i).getQuestionText());

			// TitleStyle
			titleText.setFont(Font.font(20));
			titleText.setFill(Color.BLACK);

			// QuestionStyle
			questionText.setFont(Font.font(12));

			topLeft.getChildren().addAll(titleText, questionText);
			GridPane.setConstraints(topLeft, 0, 0, 2, 1);
			root.getChildren().addAll(topLeft);

			// TopRight Corner
			BorderPane top = new BorderPane();
			HBox topRight = new HBox(10);
			topRight.spacingProperty().set(10);
			
			top.setTop(topRight);
			topRight.setAlignment(Pos.TOP_RIGHT);
			StackPane spoilerHolder = new StackPane();
			Rectangle spoilerTimer = new Rectangle(40, 15, Color.BLACK);
			Rectangle spoilerDetection = new Rectangle(40, 15, Color.TRANSPARENT);
			Text timerText = new Text("time");
			timerText.setFill(Color.WHITE);
			timerText.setStroke(Color.WHITE);
			Label timer = new Label("18:45");
			Label questionNr = new Label((i + 1) + "/" + (questionsDT.size()));
			spoilerHolder.getChildren().addAll(timer, spoilerTimer, timerText);
			spoilerHolder.getChildren().add(spoilerDetection);
			topRight.getChildren().addAll(spoilerHolder, questionNr);
			GridPane.setConstraints(top, 2, 0);
			root.getChildren().addAll(top);

			// BottomRight
			GridPane bottomRight = new GridPane();
			HBox bottomRightButtonHolder = new HBox();
			bottomRightButtonHolder.setAlignment(Pos.BOTTOM_RIGHT);
			Circle buttonBack = new Circle(15);
			Circle buttonNext = new Circle(15);
			Button buttonDone = new Button("Lämna in");
			buttonDone.setVisible(false);
			GridPane.setConstraints(bottomRightButtonHolder, 1, 1);
			GridPane.setConstraints(buttonDone, 0, 1);
			bottomRight.getChildren().addAll(buttonDone, bottomRightButtonHolder);
			bottomRightButtonHolder.getChildren().addAll(buttonBack, buttonNext);

			GridPane.setConstraints(bottomRight, 2, 1);
			root.getChildren().addAll(bottomRight);

			// BottomLeft / BottomCenter
			BorderPane questionHolder = new BorderPane();
			BorderPane answerHolder = new BorderPane();

			// v---------------------Mall för text fråga
			if (questionsDT.get(i).getquestionType().getQuestionType().equals("Fritext")) {
				questionHolder.setCenter(questionTextArea);
				GridPane.setConstraints(questionHolder, 0, 1, 2, 1);
				root.getChildren().addAll(questionHolder);
			}
			// ^---------------------Mall för text fråga

			// v---------------------Mall för radiobutton fråga
			if (questionsDT.get(i).getquestionType().getQuestionType().equals("Radiobuttons")) {
				VBox toggleButtons = new VBox();
				RadioButton button1 = new RadioButton(answerDT.getAnswerList().get(0));
				button1.setToggleGroup(group);
				RadioButton button2 = new RadioButton(answerDT.getAnswerList().get(1));
				button2.setToggleGroup(group);
				RadioButton button3 = new RadioButton(answerDT.getAnswerList().get(2));
				button3.setToggleGroup(group);
				RadioButton button4 = new RadioButton(answerDT.getAnswerList().get(3));
				button4.setToggleGroup(group);

				toggleButtons.getChildren().addAll(button1, button2, button3, button4);
				answerHolder.setCenter(toggleButtons);
				GridPane.setConstraints(answerHolder, 0, 1, 2, 1);
				root.getChildren().addAll(answerHolder);
	
			}

			// ^---------------------Mall för radiobutton fråg

			// rootGrid
			ColumnConstraints column1 = new ColumnConstraints();
			column1.setPercentWidth(50);
			root.getColumnConstraints().addAll(column1, column1, column1);
			RowConstraints row1 = new RowConstraints();
			row1.setPercentHeight(50);
			root.getRowConstraints().addAll(row1, row1);

			// BottomRightGrid
			RowConstraints row50 = new RowConstraints();
			row50.setPercentHeight(50);
			bottomRight.getColumnConstraints().addAll(column1, column1);
			bottomRight.getRowConstraints().addAll(row50, row50);

			root.setGridLinesVisible(true);
			bottomRight.setGridLinesVisible(true);
			canvasesDT.add(root);
			

			buttonBack.setOnMouseClicked(event -> {
				testOverviewQDT.set(questionNumberDT, questionsDT.get(questionNumberDT).getQuestionText());
				
				if(questionsDT.get(questionNumberDT).getquestionType().getQuestionType().equals("Fritext")){
				testOverviewADT.set(questionNumberDT,questionTextArea.getText());
				}
				if(questionsDT.get(questionNumberDT).getquestionType().getQuestionType().equals("Radiobuttons")){
					try{
					testOverviewADT.set(questionNumberDT,((RadioButton)(group.getSelectedToggle())).getText());
					}catch(Exception e){
						System.out.println(e);
					}
				}
				changeQuestion("-");
				System.out.println(questionNumberDT);
			});

			buttonNext.setOnMouseClicked(event -> {
				testOverviewQDT.set(questionNumberDT, questionsDT.get(questionNumberDT).getQuestionText());
				
				if(questionsDT.get(questionNumberDT).getquestionType().getQuestionType().equals("Fritext")){
					testOverviewADT.set(questionNumberDT,questionTextArea.getText());
					}
					if(questionsDT.get(questionNumberDT).getquestionType().getQuestionType().equals("Radiobuttons")){
						try{
						testOverviewADT.set(questionNumberDT,((RadioButton)(group.getSelectedToggle())).getText());
						}
					catch(Exception e){
						System.out.println(e);
					}
					}

				changeQuestion("+");
				System.out.println(questionNumberDT);
			});

			spoilerDetection.setOnMouseEntered(event -> {
				spoilerTimer.setFill(Color.TRANSPARENT);
				timerText.setFill(Color.TRANSPARENT);
				timerText.setStroke(Color.TRANSPARENT);

			});
			spoilerDetection.setOnMouseExited(event -> {
				spoilerTimer.setFill(Color.BLACK);
				timerText.setFill(Color.WHITE);
				timerText.setStroke(Color.WHITE);

			});


		}
		
		//LastCanvas /START
		
		BorderPane rootLC = new BorderPane();
		Scene lastCanvas = new Scene(rootLC,800,350);
		Button endTest = new Button("End Test");
		rootLC.setCenter(endTest);
		canvasesDT.add(rootLC);
		
		//LastCanvas /SLUT
		
		//Overview för provets svar /START
		BorderPane rootOV = new BorderPane();
		Scene sceneOV = new Scene(rootOV, 800, 350);

		Text overviewTitle = new Text("Overview");
		overviewTitle.setFont(Font.font(18));
		TextArea overview = new TextArea();
		overview.setEditable(false);

		HBox rightContainer = new HBox(12);
		BorderPane leftContainer = new BorderPane();
		rightContainer.setPadding(new Insets(0,3000,20,30));
		rightContainer.setAlignment(Pos.BOTTOM_LEFT);
		
		Button yes = new Button("Skicka in");
		Button cancel = new Button("Cancel");
		rightContainer.getChildren().addAll(yes, cancel);
		//leftContainer.getChildren().addAll(overviewTitle,overview);
		leftContainer.setTop(overviewTitle);
		leftContainer.setCenter(overview);

		rootOV.setLeft(leftContainer);
		rootOV.setRight(rightContainer);

		centerPaneDT.setCenter(canvasesDT.get(0));
		centerPaneDT.show();
		
		cancel.setOnAction(event ->{
			centerPaneDT.setCenter(canvasesDT.get(0));
			questionNumberDT=0;
			overview.setText("");
			
		});
		endTest.setOnAction(event ->{
		overview.appendText(getAnswersForOverview());
			centerPaneDT.setCenter(sceneOV);
		});
		
		yes.setOnAction(event ->{
			for(int i=0;i<questionsDT.size();i++){
			sendToDatabase(userDT,questionsDT.get(i),testOverviewADT.get(i).toString(),em);
			}
		});
		
		//Overview för provets svar /SLUT

		centerPaneDT.setCenter(canvasesDT.get(questionNumberDT));
		centerPaneDT.show();

	}
	

	public static void main(String[] args) {
		launch(args);
	}
	

	public void showDoneButton() {
		for (int i = 0; i <= canvasesDT.size() - 1; i++) {
       
		}
	}
	
	public String getAnswersForOverview(){
		String overview="";
		for(int i=0;i<testOverviewADT.size();i++){
			overview+=testOverviewQDT.get(i)+": "+testOverviewADT.get(i)+"\n";
			
		}
		return overview;
		
		
	}

	public void changeQuestion(String cond) {
		if (questionNumberDT < 1 & cond == "-") {
			this.questionNumberDT = canvasesDT.size() - 1;
			centerPaneDT.setCenter(canvasesDT.get(questionNumberDT));
		} else if (questionNumberDT >= (canvasesDT.size() - 1) & cond == "+") {
			this.questionNumberDT = 0;
			centerPaneDT.setCenter(canvasesDT.get(questionNumberDT));
			goneThroughDT = true;
			

		} else {
			switch (cond) {
			case "+":
				questionNumberDT++;
				centerPaneDT.setCenter(canvasesDT.get(questionNumberDT));
				break;
			case "-":
				questionNumberDT--;
				centerPaneDT.setCenter(canvasesDT.get(questionNumberDT));
				break;
			}
		}
	}
	
public void sendToDatabase(User user, Question question, String answer, EntityManager em){
		StudentAnswer studentAnswer = new StudentAnswer();
		studentAnswer.setUser(user);
		studentAnswer.setQuestion(question);
		studentAnswer.setAnswer(answer);
		em.getTransaction().begin();
		em.persist(studentAnswer);
		em.getTransaction().commit();
	}


}
