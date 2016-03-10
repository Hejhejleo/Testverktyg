package gui;

import java.util.ArrayList;

import com.sun.glass.ui.GestureSupport;

import entity.Question;
import entity.StudentAnswer;
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

	
	public StackPane showGradingPane() {
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
		
		
		Text titleText = new Text("Grading test");
		titleText.setFont(Font.font(30));
		HBox titleBox = new HBox(10);
		titleBox.setPadding(new Insets(10, 10, 5, 10));
		titleBox.setAlignment(Pos.CENTER);
		titleBox.getChildren().add(titleText);

		// PSEUDOKOD TODO
		// Studentens namn
		Label lblName = new Label("Studentens namn"); // studentens namn
		//Label lblName = new Label(StudentAnswer.User.getfName() + " " + StudentAnswer.User.getlName()); // studentens namn
		lblName.setMaxWidth(200);
		
		// provets namn
		Label lblTestName = new Label("Provets namn"); 
//		Label lblTestName = new Label(Question.Test.getTestName()); 
		lblTestName.setMaxWidth(200);

		// frågans nummer/namn
		Label lblQuestionName = new Label("Frågans titel");
//		Label lblQuestionName = new Label(Question.getQuestionTitle());
		lblQuestionName.setMaxWidth(200);

		// frågetexten, textarea bästa alternativet?
		TextArea txtQuestion = new TextArea("Frågetexten");
//		TextArea txtQuestion = new TextArea(Question.getQuestionText());
		txtQuestion.setDisable(true);
		txtQuestion.setMaxWidth(200);

		// rubrik, "studentens svar"
		Label lblAnswerTitle = new Label("Student's answer");
		lblAnswerTitle.setMaxWidth(200);

		// studentens svar
		TextArea txtStudentAnswer = new TextArea("Studentens svar");
//		TextField txtCity = new TextField(StudentAnswer.getAnswer());
		txtStudentAnswer.setDisable(true);
		txtStudentAnswer.setMaxWidth(200);

		// rubrik: lärarens egna noteringar
		Label lblTeachersNote = new Label("Teacher's note");
		lblTeachersNote.setMaxWidth(200);

		// lärarens egna noteringar
		TextArea txtTeachersNote = new TextArea();
		txtTeachersNote.setMaxWidth(200);

		// rubrik: lärarens kommentarer till studenten
		Label lblTeachersComment = new Label("Teacher's comment to the student");
		lblTeachersComment.setMaxWidth(200);

		// lärarens kommentarer till studenten
		TextArea txtTeachersComment = new TextArea();
		txtTeachersComment.setMaxWidth(200);

		// rubrik: sätta poäng
		Label lblGivenPoints = new Label("Set points");
		lblGivenPoints.setMaxWidth(200);

		// poäng på uppgiften
		ComboBox cmbGivenPoints = new ComboBox();
		cmbGivenPoints.setItems(givenPointAlternative);
		cmbGivenPoints.setValue(0.0);
		cmbGivenPoints.setStyle(whiteBGAndGreyBorder);
		cmbGivenPoints.setCursor(Cursor.HAND);
		cmbGivenPoints.setPromptText("Set points");

		VBox gradingBoxL = new VBox(5);
		gradingBoxL.setPadding(new Insets(5, 10, 5, 10));
		gradingBoxL.getChildren().addAll(lblName, lblQuestionName, txtQuestion, 
				lblTeachersNote, txtTeachersNote);

		VBox gradingBoxR = new VBox(5);
		gradingBoxR.setPadding(new Insets(5, 10, 5, 10));
		gradingBoxR.getChildren().addAll(lblTestName, lblAnswerTitle, 
				txtStudentAnswer, lblTeachersComment, txtTeachersComment, 
				cmbGivenPoints);

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

		
		String regex = "\\d+";

		saveButton.setOnAction(save -> {
			// spara note, comment, points
		});

		VBox addUserColumn = new VBox(5);
		addUserColumn.setPadding(new Insets(0));
		addUserColumn.getChildren().addAll(titleBox, gradingBoxes, buttons);
		addUserColumn.setStyle(frameStyle);// sets style for addframe
		gradingPane.getChildren().add(addUserColumn);
		return gradingPane;
	}

}
