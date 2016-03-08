package gui;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.Question;
import entity.Test;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DoTest extends Application {
	Stage primaryStage;
	boolean fade;
	String testName;
	int questionNummer = 0;
	List<Scene> canvases = new ArrayList<>();
	List<Question> questions = new ArrayList<>();

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();
		Query test = em.createNamedQuery("get tests");
		testName = "Test i JPA";
		test.setParameter("name", testName);
		System.out.println("---------------:::Hello:::-----------------");
		// get questions from DB
		System.out.println("---------------:::1:::-----------------");
		List<Test> testsList = new ArrayList<>(test.getResultList());
		System.out.println("---------------:::2:::-----------------");
		
		System.out.println("---------------:::3:::-----------------");
		Test currentTest = new Test();
		currentTest = testsList.get(0);
		System.out.println("---------------:::4:::-----------------");

		for (int i = 0; i < currentTest.getQuestions().size(); i++) {
			questions.add(currentTest.getQuestions().get(i));
			System.out.println(questions.get(i).getQuestionText());
		}
		System.out.println("---------------:::5:::-----------------");

		// RootGrid
		
		//Make scenes
		System.out.println("---------------:::6:::-----------------");
		for (int i = 0; i <= (questions.size()-1); i++) {
			GridPane root = new GridPane();
			System.out.println("---------------:::7:::-----------------");
			
			Scene scene = new Scene(root, 800, 350);

			// TopLeft Corner
			VBox topLeft = new VBox();
			Text titleText = new Text(questions.get(i).getQuestionTitle());
			Text questionText = new Text(questions.get(i).getQuestionText());

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
			;
			top.setTop(topRight);
			topRight.setAlignment(Pos.TOP_RIGHT);
			StackPane spoilerHolder = new StackPane();
			Rectangle spoilerTimer = new Rectangle(40, 15, Color.BLACK);
			Rectangle spoilerDetection = new Rectangle(40, 15, Color.TRANSPARENT);
			Text timerText = new Text("time");
			timerText.setFill(Color.WHITE);
			timerText.setStroke(Color.WHITE);
			Label timer = new Label("18:45");
			Label questionNr = new Label((i + 1) + "/" + (questions.size()));
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
			GridPane.setConstraints(bottomRightButtonHolder, 1, 1);
			bottomRight.getChildren().addAll(bottomRightButtonHolder);
			bottomRightButtonHolder.getChildren().addAll(buttonBack, buttonNext);

			GridPane.setConstraints(bottomRight, 2, 1);
			root.getChildren().addAll(bottomRight);

			// BottomLeft / BottomCenter
			BorderPane questionHolder = new BorderPane();
			BorderPane answerHolder = new BorderPane();

			// v---------------------Mall för text fråga
			if (questions.get(i).getquestionType().getQuestionType().equals("Fritext")) {
				TextArea questionTextArea = new TextArea();
				questionHolder.setCenter(questionTextArea);
				GridPane.setConstraints(questionHolder, 0, 1, 2, 1);
				root.getChildren().addAll(questionHolder);
			}
			// ^---------------------Mall för text fråga

			// v---------------------Mall för radiobutton fråga
			if (questions.get(i).getquestionType().getQuestionType().equals("Radiobuttons")) {
				ToggleGroup group = new ToggleGroup();
				VBox toggleButtons = new VBox();

				RadioButton button1 = new RadioButton("Answer 1");
				button1.setToggleGroup(group);
				RadioButton button2 = new RadioButton("Answer 2");
				button2.setToggleGroup(group);
				RadioButton button3 = new RadioButton("Answer 3");
				button3.setToggleGroup(group);
				RadioButton button4 = new RadioButton("Answer 4");
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
			canvases.add(scene);
			
			buttonBack.setOnMouseClicked(event -> {

				changeQuestion("-");
				System.out.println(questionNummer);
			});

			buttonNext.setOnMouseClicked(event -> {

				changeQuestion("+");
				System.out.println(questionNummer);
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
		

		primaryStage.setScene(canvases.get(questionNummer));
		primaryStage.show();

		

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void changeQuestion(String cond) {
		if (questionNummer < 1 & cond == "-") {
		}
		else if(questionNummer >= (questions.size()-1) & cond=="+"){
			
		}
		 else {
			switch (cond) {
			case "+":
				questionNummer++;
				primaryStage.setScene(canvases.get(questionNummer));
				break;
			case "-":
				questionNummer--;
				primaryStage.setScene(canvases.get(questionNummer));
				break;
			}
		}
	}

	public void drawCanvas() {

	}

}
