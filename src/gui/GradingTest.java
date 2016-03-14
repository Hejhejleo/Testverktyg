package gui;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.sun.glass.ui.GestureSupport;

import entity.Question;
import entity.SchoolClass;
import entity.StudentAnswer;
import entity.Test;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableFloatArray;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GradingTest {

	private String whiteBGAndGreyBorder = "-fx-background-color: #FFFFFF; " + "-fx-border-color: #D3D3D3";

	private String frameStyle = "-fx-border-color: #FFA500; " + "-fx-border-width: 2px; "
			+ "-fx-background-color: #FFFFFF";
	private EntityManagerFactory emf;
	private EntityManager em;
	private Menu gradingTestMenu;
	private Menu mnuFile;

	public GradingTest(Menu mnuFile) {
		this.mnuFile = mnuFile;

	}

	public StackPane showGradingPane() {
		emf = Persistence.createEntityManagerFactory("Testverktyg");
		em = emf.createEntityManager();
		ObservableList<Double> givenPointAlternative = FXCollections.observableArrayList();
		givenPointAlternative.add(-1.0);
		givenPointAlternative.add(-0.5);
		givenPointAlternative.add(0.0);
		givenPointAlternative.add(0.5);
		givenPointAlternative.add(1.0);
		givenPointAlternative.add(1.5);
		givenPointAlternative.add(2.0);
		givenPointAlternative.add(2.5);
		givenPointAlternative.add(3.0);
		givenPointAlternative.add(3.5);
		givenPointAlternative.add(4.0);
		givenPointAlternative.add(4.5);
		givenPointAlternative.add(5.0);

		StackPane gradingPane = new StackPane();

		gradingTestMenu = new Menu("Available tests");
		mnuFile.getItems().add(gradingTestMenu);
		Query findTests = em.createQuery("Select t from Test t");
		List<Test> testList = findTests.getResultList();

		// TODO
		Test t2 = new Test("test 2", "20160317-09:00", "20160317-11:00");
		testList.add(t2);
		// TODO

		for (Test t : testList) {
			Menu itemForTestMenu = new Menu("Test: " + t.getTestName() + " " + "date: " + t.getTestStart());
			gradingTestMenu.getItems().add(itemForTestMenu);
			for (SchoolClass sc : t.getClasses()) {
				Menu itemForClassMenu = new Menu("Class: " + sc.getClassName());
				itemForTestMenu.getItems().add(itemForClassMenu);
				for (User u : sc.getStudents()) {
					System.out.println(u.getUserName());
					MenuItem itemForStudentMenu = new MenuItem(u.getfName() + " " + u.getlName());
					itemForClassMenu.getItems().add(itemForStudentMenu);
					itemForClassMenu.setOnAction(event -> {
						Text titleText = new Text("Grading test");
						titleText.setFont(Font.font(30));
						HBox titleBox = new HBox(10);
						titleBox.setPadding(new Insets(10, 10, 5, 10));
						titleBox.setAlignment(Pos.CENTER);
						titleBox.getChildren().add(titleText);

						// Studentens namn
						Label lblName = new Label(u.getfName() + " " + u.getlName()); // studentens
																						// namn
						lblName.setFont(Font.font(18));
						lblName.setMaxWidth(200);

						// provets namn
						Label lblTestName = new Label(t.getTestName());
						lblTestName.setFont(Font.font(18));
						lblTestName.setMaxWidth(200);

						for (Question q : t.getQuestions()) {
							// frågans nummer/namn
							Label lblQuestionName = new Label(q.getQuestionTitle());
							lblQuestionName.setMaxWidth(200);

							// frågetexten, textarea bästa alternativet?
							TextArea txtQuestion = new TextArea(q.getQuestionText());
							txtQuestion.setDisable(true);
							txtQuestion.setMaxWidth(200);
							txtQuestion.setMaxHeight(100);

							// rubrik, "studentens svar"
							Label lblAnswerTitle = new Label("Student's answer");
							lblAnswerTitle.setMaxWidth(200);

							// studentens svar
							TextArea txtStudentAnswer = new TextArea();
							Query answerQuery = em.createNamedQuery("getStudentAnswer");
							answerQuery.setParameter("question", q);
							answerQuery.setParameter("user", u);
							StudentAnswer sa = (StudentAnswer) answerQuery.getSingleResult();
							txtStudentAnswer.setDisable(true);
							txtStudentAnswer.setMaxWidth(200);
							txtStudentAnswer.setMaxHeight(100);
							txtStudentAnswer.setText(sa.getAnswer());

							// rubrik: lärarens egna noteringar
							Label lblTeachersNote = new Label("Teacher's note");
							lblTeachersNote.setMaxWidth(200);

							// lärarens egna noteringar
							TextArea txtTeachersNote = new TextArea();
							if (sa.getNote() != null) {
								txtTeachersNote.setText(sa.getNote());
							}
							txtTeachersNote.setMaxWidth(200);

							// rubrik: lärarens kommentarer till studenten
							Label lblTeachersComment = new Label("Teacher's comment to the student");
							lblTeachersComment.setMaxWidth(200);

							// lärarens kommentarer till studenten
							TextArea txtTeachersComment = new TextArea();
							if (sa.getTeacherComment() != null) {
								txtTeachersComment.setText(sa.getTeacherComment());
							}
							txtTeachersComment.setMaxWidth(200);

							// rubrik: sätta poäng
							Label lblGivenPoints = new Label("Set points");
							lblGivenPoints.setMaxWidth(200);

							// poäng på uppgiften
							ComboBox<Double> cmbGivenPoints = new ComboBox();
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

							cancelButton.setOnAction(cancel -> {
								Alert notSaved = new Alert(AlertType.ERROR);
								// varning för att innehållet inte är sparat

							});

							saveButton.setOnAction(save -> {

								sa.setGivenPoints(cmbGivenPoints.getValue());
								sa.setNote(txtTeachersNote.getText());
								sa.setTeacherComment(txtTeachersComment.getText());
								em.getTransaction().begin();
								em.persist(sa);
								em.getTransaction().commit();
							});

							HBox separator = new HBox(5);
							separator.setPadding(new Insets(5));
							separator.setStyle("-fx-background-color: #FFA500");
							VBox addUserColumn = new VBox(5);
							addUserColumn.setPadding(new Insets(0));
							addUserColumn.getChildren().addAll(titleBox, gradingBoxes, buttons, separator);
							addUserColumn.setStyle("-fx-background-color: #FFFFFF");// TODO
																					// sets
																					// style
																					// for
																					// addframe

							gradingPane.getChildren().add(addUserColumn);
						}
					});
				}
			}
		}
		return gradingPane;
	}
}
