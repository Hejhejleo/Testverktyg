package gui;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.Question;
import entity.QuestionType;
import entity.SchoolClass;
import entity.StudentAnswer;
import entity.Test;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * GradingTest:
 * 
 * Choose a test in the list of available tests
 * 
 * Choose a schoolclass in the list of schoolclasses assigned to that test
 * 
 * Choose to focus on a question or a student
 * 
 * > If question:
 * 
 * Choose a question in a list of the questions in the test
 * 
 * All students' answers to that question are presented
 * 
 * > If student: 
 * 
 * Pick a student in a list of students in the class
 * 
 * The picked student's answers are presented
 * 
 * Known bugs and flaws: 
 * > no scrollpane connected to the stackpane
 * > doesn't include answer from radionbutton questions 
 * > lots of code written more than once
 * 
 * @author Jannike
 *
 */
public class GradingTest {

	private String whiteBGAndGreyBorder = "-fx-background-color: #FFFFFF; " + "-fx-border-color: #D3D3D3";
	private String frameStyle = "-fx-border-color: #FFA500; " + "-fx-border-width: 2px; "
			+ "-fx-background-color: #FFFFFF";
	private String pushButtonStyle = "-fx-border-color: #FFA500; " + "-fx-border-width: 2px; "
			+ "-fx-background-color: #FFFFFF";
	private String releaseButtonStyle = "-fx-border-color: #D3D3D3; " + "-fx-border-width: 1px; "
			+ "-fx-background-color: #FFFFFF";
	private EntityManagerFactory emf;
	private EntityManager em;
	private Menu gradingTestMenu;
	private Menu gradingTest;
	private VBox gradingTestComponentsQuestion = new VBox(5);
	private VBox gradingTestComponentsStudent = new VBox(5);

	/**
	 * 
	 * @param gradingTest
	 *            - gives the starting point in the main program for the menu
	 */
	public GradingTest(Menu gradingTest) {
		this.gradingTest = gradingTest;
	}

	/**
	 * Gives the point alternatives for the teacher to award on a question, from
	 * 0 to the question's maximum point
	 * 
	 * @param q
	 *            - the question
	 * @return the point alternatives for the teacher to award on the question
	 *         q, from 0 to the maximum point
	 */
	private ObservableList<Double> getGivenPointAlternatives(Question q) {
		ObservableList<Double> givenPointAlternative = FXCollections.observableArrayList();
		Double maxPoint = q.getPoints();
		for (Double i = 0.0; i < ((maxPoint) * 2); i++) {
			givenPointAlternative.add(i * 0.5);
		}
		givenPointAlternative.add(maxPoint);
		return givenPointAlternative;
	}

	/**
	 * 
	 * @return the stackpane for grading tests
	 */
	public StackPane showGradingPane(AnchorPane centerPane) {
		emf = Persistence.createEntityManagerFactory("Testverktyg");
		em = emf.createEntityManager();
		StackPane gradingPane = new StackPane();
		
		// Starting point for the menu where which test to grade is chosen
		gradingTestMenu = new Menu("Available tests");
		gradingTest.getItems().add(gradingTestMenu);
		Query findTests = em.createQuery("Select t from Test t");
		List<Test> testList = findTests.getResultList();

		// List all test in the database, name and starting date
		for (Test t : testList) {
			Menu itemForTestMenu = new Menu("Test: " + t.getTestName() + " " + "date: " + t.getTestStart());
			gradingTestMenu.getItems().add(itemForTestMenu);
			// Lists all schoolclasses assinged to the test
			for (SchoolClass sc : t.getClasses()) {
				Menu itemForClassMenu = new Menu("Class: " + sc.getClassName());
				itemForTestMenu.getItems().add(itemForClassMenu);
				Menu itemChooseFocusQuestionMenu = new Menu("Focus on a question");
				Menu itemChooseFocusStudentMenu = new Menu("Focus on a student");
				itemForClassMenu.getItems().addAll(itemChooseFocusQuestionMenu, itemChooseFocusStudentMenu);
				// ------ Focus on a question ------ // TODO
				// List all questions in the test
				for (Question q : t.getQuestions()) {
					MenuItem itemForQuestionMenu = new MenuItem(q.getQuestionTitle());
					itemChooseFocusQuestionMenu.getItems().add(itemForQuestionMenu);
					// Question is chosen, presentation of the answers from all
					// students in the class
					itemForQuestionMenu.setOnAction(event -> {
						Text titleText = new Text("Grading test - focus on question");
						titleText.setFont(Font.font(30));
						HBox titleBox = new HBox(10);
						titleBox.setPadding(new Insets(10, 10, 5, 10));
						titleBox.setAlignment(Pos.CENTER);
						titleBox.getChildren().add(titleText);

						gradingTestComponentsQuestion.getChildren().addAll(titleBox);
						
						for (User u : sc.getStudents()) {
							// Name of the question
							Label lblQuestionName = new Label(q.getQuestionTitle());
							lblQuestionName.setMaxWidth(200);

							// The question
							TextArea txtQuestion = new TextArea(q.getQuestionText());
							txtQuestion.setDisable(true);
							txtQuestion.setMaxWidth(200);
							txtQuestion.setMaxHeight(100);

							// Name of the student
							Label lblName = new Label(u.getfName() + " " + u.getlName());
							lblName.setFont(Font.font(18));
							lblName.setMaxWidth(200);

							// Name of the test
							Label lblTestName = new Label(t.getTestName());
							lblTestName.setFont(Font.font(18));
							lblTestName.setMaxWidth(200);

							// Set headline
							Label lblAnswerTitle = new Label("Student's answer");
							lblAnswerTitle.setMaxWidth(200);

							// The student's answer
							TextArea txtStudentAnswer = new TextArea();
							Query answerQuery = em.createNamedQuery("getStudentAnswer");
							answerQuery.setParameter("question", q);
							answerQuery.setParameter("user", u);
							StudentAnswer sa = (StudentAnswer) answerQuery.getSingleResult();
							txtStudentAnswer.setDisable(true);
							txtStudentAnswer.setMaxWidth(200);
							txtStudentAnswer.setMaxHeight(100);
							txtStudentAnswer.setText(sa.getAnswer());

							// Set headline
							Label lblTeachersNote = new Label("Teacher's note");
							lblTeachersNote.setMaxWidth(200);

							// The teacher's own note
							TextArea txtTeachersNote = new TextArea();
							if (sa.getNote() != null) {
								txtTeachersNote.setText(sa.getNote());
							}
							txtTeachersNote.setMaxWidth(200);

							// Set headline
							Label lblTeachersComment = new Label("Teacher's comment to the student");
							lblTeachersComment.setMaxWidth(200);

							// The teacher's comment to the student
							TextArea txtTeachersComment = new TextArea();
							if (sa.getTeacherComment() != null) {
								txtTeachersComment.setText(sa.getTeacherComment());
							}
							txtTeachersComment.setMaxWidth(200);

							// Set headline
							Label lblGivenPoints = new Label("Set points");
							lblGivenPoints.setMaxWidth(200);

							// Setting the score on the question
							ComboBox<Double> cmbGivenPoints = new ComboBox();
							ObservableList<Double> givenPointAlternative = getGivenPointAlternatives(q);
							cmbGivenPoints.setItems(givenPointAlternative);

							if (sa.getGivenPoints() != null) {
								cmbGivenPoints.setValue(sa.getGivenPoints());
							} else {
								cmbGivenPoints.setPromptText("Set points");
							}
							cmbGivenPoints.setStyle(whiteBGAndGreyBorder);
							cmbGivenPoints.setCursor(Cursor.HAND);

							// Left column of the presentation
							VBox gradingBoxL = new VBox(5);
							gradingBoxL.setPadding(new Insets(5, 10, 5, 10));
							gradingBoxL.getChildren().addAll(lblName, lblQuestionName, txtQuestion, lblTeachersNote,
									txtTeachersNote);

							// Right column of the presentation
							VBox gradingBoxR = new VBox(5);
							gradingBoxR.setPadding(new Insets(5, 10, 5, 10));
							gradingBoxR.getChildren().addAll(lblTestName, lblAnswerTitle, txtStudentAnswer,
									lblTeachersComment, txtTeachersComment, lblGivenPoints, cmbGivenPoints);

							// Both columns
							HBox gradingBoxes = new HBox();
							gradingBoxes.getChildren().addAll(gradingBoxL, gradingBoxR);

							Button saveButton = new Button("Save");
							Button cancelButton = new Button("Cancel");
							saveButton.setStyle(whiteBGAndGreyBorder);
							BorderPane buttons = new BorderPane();
							saveButton.requestFocus();
							saveButton.setCursor(Cursor.HAND);
							cancelButton.setStyle(whiteBGAndGreyBorder);
							cancelButton.setCursor(Cursor.HAND);

							buttons.setLeft(saveButton);
							buttons.setRight(cancelButton);
							buttons.setPadding(new Insets(5, 10, 10, 10));

							// The look of the button gives feedback when pressed and
							// released
							saveButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent e) {
									saveButton.setStyle(pushButtonStyle);
								}
							});

							saveButton.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent e) {
									saveButton.setStyle(releaseButtonStyle);
								}
							});

							cancelButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent e) {
									cancelButton.setStyle(pushButtonStyle);
								}
							});

							cancelButton.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent e) {
									cancelButton.setStyle(releaseButtonStyle);
								}
							});

							cancelButton.setOnAction(cancel -> {
								Alert notSaved = new Alert(AlertType.INFORMATION);
								notSaved.setTitle("Content not saved");
								notSaved.setHeaderText("No grading added");
								notSaved.setContentText("Your inputs will not be saved");
								notSaved.showAndWait();
								 centerPane.getChildren().clear(); //TODO

							});

							// The input is stored in the database
							saveButton.setOnAction(save -> {
								sa.setGivenPoints(cmbGivenPoints.getValue());
								sa.setNote(txtTeachersNote.getText());
								sa.setTeacherComment(txtTeachersComment.getText());
								em.getTransaction().begin();
								em.persist(sa);
								em.getTransaction().commit();
							});

							// A separator between each student's answer
							HBox separator = new HBox(5);
							separator.setPadding(new Insets(5));
							separator.setStyle("-fx-background-color: #FFA500");
							gradingTestComponentsQuestion.setPadding(new Insets(0));
							gradingTestComponentsQuestion.getChildren().addAll(gradingBoxes, buttons,
									separator);
							gradingTestComponentsQuestion.setStyle("-fx-background-color: #FFFFFF");
						}
						gradingPane.getChildren().add(gradingTestComponentsQuestion);
					});
				}
				// ------ Focus on a student ------ //TODO
				// all students in the schoolclass are listed
				for (User u : sc.getStudents()) {
					MenuItem itemForStudentMenu = new MenuItem(u.getfName() + " " + u.getlName());
					itemChooseFocusStudentMenu.getItems().add(itemForStudentMenu);
					// choose student to focus on
					// Student is chosen, presentation of the students answer to all question in the test
					itemForStudentMenu.setOnAction(event -> {
						Text titleText = new Text("Grading test - focus on student");
						titleText.setFont(Font.font(30));
						HBox titleBox = new HBox(10);
						titleBox.setPadding(new Insets(10, 10, 5, 10));
						titleBox.setAlignment(Pos.CENTER);
						titleBox.getChildren().add(titleText);

						gradingTestComponentsStudent.getChildren().addAll(titleBox);

						for (Question q : t.getQuestions()) {
							// Name of the student
							Label lblName = new Label(u.getfName() + " " + u.getlName());
							lblName.setFont(Font.font(18));
							lblName.setMaxWidth(200);

							// Name of the test
							Label lblTestName = new Label(t.getTestName());
							lblTestName.setFont(Font.font(18));
							lblTestName.setMaxWidth(200);

							// Name of the question
							Label lblQuestionName = new Label(q.getQuestionTitle());
							lblQuestionName.setMaxWidth(200);

							// The question
							TextArea txtQuestion = new TextArea(q.getQuestionText());
							txtQuestion.setDisable(true);
							txtQuestion.setMaxWidth(200);
							txtQuestion.setMaxHeight(100);

							// Set headline
							Label lblAnswerTitle = new Label("Student's answer");
							lblAnswerTitle.setMaxWidth(200);

							// The student's answer
							TextArea txtStudentAnswer = new TextArea();
							Query answerQuery = em.createNamedQuery("getStudentAnswer");
							answerQuery.setParameter("question", q);
							answerQuery.setParameter("user", u);
							StudentAnswer sa = (StudentAnswer) answerQuery.getSingleResult();
							txtStudentAnswer.setDisable(true);
							txtStudentAnswer.setMaxWidth(200);
							txtStudentAnswer.setMaxHeight(100);
							txtStudentAnswer.setText(sa.getAnswer());

							// Set headline
							Label lblTeachersNote = new Label("Teacher's note");
							lblTeachersNote.setMaxWidth(200);

							// The teacher's own note
							TextArea txtTeachersNote = new TextArea();
							if (sa.getNote() != null) {
								txtTeachersNote.setText(sa.getNote());
							}
							txtTeachersNote.setMaxWidth(200);

							// Set headline
							Label lblTeachersComment = new Label("Teacher's comment to the student");
							lblTeachersComment.setMaxWidth(200);

							// The teacher's comment to the student
							TextArea txtTeachersComment = new TextArea();
							if (sa.getTeacherComment() != null) {
								txtTeachersComment.setText(sa.getTeacherComment());
							}
							txtTeachersComment.setMaxWidth(200);

							// Set headline
							Label lblGivenPoints = new Label("Set points");
							lblGivenPoints.setMaxWidth(200);

							// Setting the score on the question
							ComboBox<Double> cmbGivenPoints = new ComboBox();
							ObservableList<Double> givenPointAlternative = getGivenPointAlternatives(q);
							cmbGivenPoints.setItems(givenPointAlternative);

							if (sa.getGivenPoints() != null) {
								cmbGivenPoints.setValue(sa.getGivenPoints());
							} else {
								cmbGivenPoints.setPromptText("Set points");
							}
							cmbGivenPoints.setStyle(whiteBGAndGreyBorder);
							cmbGivenPoints.setCursor(Cursor.HAND);

							VBox gradingBoxL = new VBox(5);
							gradingBoxL.setPadding(new Insets(5, 10, 5, 10));
							gradingBoxL.getChildren().addAll(lblName, lblQuestionName, txtQuestion, lblTeachersNote,
									txtTeachersNote);

							VBox gradingBoxR = new VBox(5);
							gradingBoxR.setPadding(new Insets(5, 10, 5, 10));
							gradingBoxR.getChildren().addAll(lblTestName, lblAnswerTitle, txtStudentAnswer,
									lblTeachersComment, txtTeachersComment, lblGivenPoints, cmbGivenPoints);

							HBox gradingBoxes = new HBox();
							gradingBoxes.getChildren().addAll(gradingBoxL, gradingBoxR);

							Button saveButton = new Button("Save");
							Button cancelButton = new Button("Cancel");
							saveButton.setStyle(whiteBGAndGreyBorder);
							BorderPane buttons = new BorderPane();
							saveButton.requestFocus();
							saveButton.setCursor(Cursor.HAND);
							cancelButton.setStyle(whiteBGAndGreyBorder);
							cancelButton.setCursor(Cursor.HAND);

							buttons.setLeft(saveButton);
							buttons.setRight(cancelButton);
							buttons.setPadding(new Insets(5, 10, 10, 10));

							saveButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent e) {
									saveButton.setStyle(pushButtonStyle);
								}
							});

							saveButton.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent e) {
									saveButton.setStyle(releaseButtonStyle);
								}
							});

							cancelButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent e) {
									cancelButton.setStyle(pushButtonStyle);
								}
							});

							cancelButton.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent e) {
									cancelButton.setStyle(releaseButtonStyle);
								}
							});

							cancelButton.setOnAction(cancel -> {
								Alert notSaved = new Alert(AlertType.INFORMATION);
								notSaved.setTitle("Content not saved");
								notSaved.setHeaderText("No grading added");
								notSaved.setContentText("Your inputs will not be saved");
								notSaved.showAndWait();
								 centerPane.getChildren().clear(); //TODO
							});

							saveButton.setOnAction(save -> {
								sa.setGivenPoints(cmbGivenPoints.getValue());
								sa.setNote(txtTeachersNote.getText());
								sa.setTeacherComment(txtTeachersComment.getText());
								em.getTransaction().begin();
								em.persist(sa);
								em.getTransaction().commit();
							});

							// A separator between each question
							HBox separator = new HBox(5);
							separator.setPadding(new Insets(5));
							separator.setStyle("-fx-background-color: #FFA500");
							gradingTestComponentsStudent.setPadding(new Insets(0));
							gradingTestComponentsStudent.getChildren().addAll(/*titleBox, */gradingBoxes, buttons,
									separator);
							gradingTestComponentsStudent.setStyle("-fx-background-color: #FFFFFF");

						} // end looping the questions on a test
						gradingPane.getChildren().add(gradingTestComponentsStudent);
					});
				}
			}
		}
		return gradingPane;
	}
}
