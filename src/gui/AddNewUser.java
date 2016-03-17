package gui;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entity.SchoolClass;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AddNewUser {
	private String whiteBGAndGreyBorder = "-fx-background-color: #FFFFFF; " + "-fx-border-color: #D3D3D3";
	private String frameStyle = "-fx-border-color: #FFA500; " + "-fx-border-width: 2px; "
			+ "-fx-background-color: #FFFFFF";
	private String pushButtonStyle = "-fx-border-color: #FFA500; " + "-fx-border-width: 2px; "
			+ "-fx-background-color: #FFFFFF";
	private String releaseButtonStyle = "-fx-border-color: #D3D3D3; " + "-fx-border-width: 1px; "
			+ "-fx-background-color: #FFFFFF";

	private SchoolClass sc;

	private boolean checkDouble(String userName, List<User> allUsers) {
		for (User user : allUsers) {
			if (userName.equals(user.getUserName()))
				return true;
		}

		return false;
	}

//	private List<SchoolClass> getAvailableClasses(List<SchoolClass> allClasses) {
//		Query findSchoolclasses = em.createQuery("Select sc from SchoolClass sc"); // TODO
//		List<SchoolClass> scList = findSchoolclasses.getResultList();
//		
//		return scList;
//	}

	public StackPane showPane(List<SchoolClass> allClasses, List<User> allUsers, AnchorPane centerPane) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();

		ObservableList<String> accountType = FXCollections.observableArrayList();
		accountType.add("Admin");
		accountType.add("Student");

		// Get available schoolclasses for a student // TODO
		ObservableList<String> availableSchoolClasses = FXCollections.observableArrayList();
		
		for (SchoolClass sc : allClasses) {
			availableSchoolClasses.add(sc.getClassName());
		}

		StackPane userPane = new StackPane();

		Text titleText = new Text("Add user account");
		titleText.setFont(Font.font(30));
		HBox titleBox = new HBox(10);
		titleBox.setPadding(new Insets(10, 10, 5, 10));
		titleBox.setAlignment(Pos.CENTER);
		titleBox.getChildren().add(titleText);

		TextField txtFirstName = new TextField();
		txtFirstName.setPromptText("First name");
		txtFirstName.setMaxWidth(200);

		TextField txtLastName = new TextField();
		txtLastName.setPromptText("Last name");
		txtLastName.setMaxWidth(200);

		TextField txtSSN = new TextField();
		txtSSN.setPromptText("SSN: YYYYMMDD-XXXX");
		txtSSN.setMaxWidth(200);

		TextField txtStreet = new TextField();
		txtStreet.setPromptText("Street address and number");
		txtStreet.setMaxWidth(200);

		TextField txtZipCode = new TextField();
		txtZipCode.setPromptText("ZIP Code");
		txtZipCode.setMaxWidth(200);

		TextField txtCity = new TextField();
		txtCity.setPromptText("City");
		txtCity.setMaxWidth(200);

		TextField txtPhoneNumber = new TextField();
		txtPhoneNumber.setPromptText("Phone number");
		txtPhoneNumber.setMaxWidth(200);

		TextField txtUserName = new TextField();
		txtUserName.setPromptText("Username");
		txtUserName.setMaxWidth(200);

		PasswordField txtPassword = new PasswordField();
		txtPassword.setMaxWidth(200);
		txtPassword.setPromptText("Password");

		PasswordField confirmPassword = new PasswordField();
		confirmPassword.setPromptText("Confirm password");
		confirmPassword.setMaxWidth(200);

		ComboBox cmbAccountType = new ComboBox();
		cmbAccountType.setItems(accountType);
		cmbAccountType.setStyle(whiteBGAndGreyBorder);
		cmbAccountType.setCursor(Cursor.HAND);
		cmbAccountType.setPromptText("Account type");

		TextField txtEmail = new TextField();
		txtEmail.setMaxWidth(200);
		txtEmail.setPromptText("E-mail");

		ComboBox cmbStudentsClass = new ComboBox();
		cmbStudentsClass.setItems(availableSchoolClasses);
		cmbStudentsClass.setStyle(whiteBGAndGreyBorder);
		cmbStudentsClass.setCursor(Cursor.HAND);
		cmbStudentsClass.setPromptText("Select SchoolClass"); 
		cmbStudentsClass.disableProperty().bind(cmbAccountType.valueProperty().isEqualTo("Admin"));
		
		VBox enterBox1 = new VBox(5);
		enterBox1.setPadding(new Insets(5, 10, 5, 10));
		enterBox1.getChildren().addAll(txtUserName, txtFirstName, txtLastName, txtPassword, confirmPassword,
				cmbAccountType, cmbStudentsClass);

		VBox enterBox2 = new VBox(5);
		enterBox2.setPadding(new Insets(5, 10, 5, 10));
		enterBox2.getChildren().addAll(txtSSN, txtEmail, txtPhoneNumber, txtStreet, txtZipCode, txtCity);

		HBox enterBoxes = new HBox();
		enterBoxes.getChildren().addAll(enterBox1, enterBox2);

		Button saveButton = new Button("Save");
		Button cancelButton = new Button("Cancel");
		Button clearButton = new Button("Clear");
		saveButton.setStyle(whiteBGAndGreyBorder);
		BorderPane buttons = new BorderPane();
		saveButton.requestFocus();
		saveButton.setCursor(Cursor.HAND);
		cancelButton.setStyle(whiteBGAndGreyBorder);
		cancelButton.setCursor(Cursor.HAND);
		clearButton.setStyle(whiteBGAndGreyBorder);
		clearButton.setCursor(Cursor.HAND);
		
		buttons.setLeft(saveButton);
		buttons.setRight(cancelButton);
		buttons.setCenter(clearButton);
		buttons.setPadding(new Insets(5, 10, 10, 10));

		cancelButton.setOnAction(cancel -> {
				Alert notSaved = new Alert(AlertType.INFORMATION);
				notSaved.setTitle("Content not saved");
				notSaved.setHeaderText("No user added");
				notSaved.setContentText("Your inputs will not be saved");
				notSaved.showAndWait();
				centerPane.getChildren().clear();
			});
		
		clearButton.setOnAction(clear -> {
			titleText.setText("");
			txtFirstName.setText("");
			txtLastName.setText("");
			txtSSN.setText("");
			txtStreet.setText("");
			txtZipCode.setText("");
			txtCity.setText("");
			txtPhoneNumber.setText("");
			txtUserName.setText("");
			txtPassword.setText("");
			confirmPassword.setText("");
			cmbAccountType.getSelectionModel().clearSelection();
			txtEmail.setText("");
			cmbStudentsClass.getSelectionModel().clearSelection();
		});

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
		
		clearButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent e) {
				clearButton.setStyle(pushButtonStyle);
			}
		});
		
		clearButton.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				clearButton.setStyle(releaseButtonStyle);
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

		String regex = "\\d+";
		
		// TODO ska det finnas poster som inte behöver vara ifyllda?

		saveButton.setOnAction(ok -> {
			Alert error = new Alert(AlertType.ERROR);
			if (checkDouble(txtUserName.getText(), allUsers)) {
				error.setTitle("Username not unique");
				error.setHeaderText("Try enter an other username");
				error.showAndWait();
			} else if (txtUserName.getText().equals("")) {
				error.setTitle("No username chosen");
				error.setHeaderText("You must enter a username");
				error.showAndWait();
			} else if (txtFirstName.getText().equals("")) {
				error.setTitle("No first name was entered");
				error.setHeaderText("You must enter a first name");
				error.showAndWait();
			} else if (txtLastName.getText().equals("")) {
				error.setTitle("No last name was entered");
				error.setHeaderText("You must enter a last name");
				error.showAndWait();
			} else if (!(confirmPassword.getText().equals(txtPassword.getText()))) {
				error.setTitle("Password mismatch");
				error.setHeaderText("The passwords must be the same");
				error.showAndWait();
			} else if (cmbAccountType.getValue() == null) {
				error.setTitle("No accounttype chosen");
				error.setHeaderText("You must choose an account type");
				error.showAndWait();
			} else if (txtSSN.getText().equals("")) {
				error.setTitle("No SSN was entered");
				error.setHeaderText("You must enter a SNN");
				error.showAndWait();
			} else if (!(txtEmail.getText().contains("@"))) {
				error.setTitle("Incorrect e-mail");
				error.setHeaderText("You have not entered a valid e-mail");
				error.showAndWait();
			} else if (txtPhoneNumber.getText().equals("")) {
				error.setTitle("No phone number was entered");
				error.setHeaderText("You must enter a phone number");
				error.showAndWait();
			} else if (txtStreet.getText().equals("")) {
				error.setTitle("No address was entered");
				error.setHeaderText("You must enter an address");
				error.showAndWait();
			} else if (txtZipCode.getText().equals("") || (!txtZipCode.getText().matches(regex))) {
				error.setTitle("No valid ZIP code was entered");
				error.setHeaderText("You must enter a valid ZIP code");
				error.showAndWait();
			} else if (txtCity.getText().equals("")) {
				error.setTitle("No city was entered");
				error.setHeaderText("You must enter a city");
				error.showAndWait();
			} else if (cmbAccountType.getValue() == "Student" && cmbStudentsClass.getValue() == null) { 
				// TODO klassplacering 
				error.setTitle("No schoolclass was chosen");
				error.setHeaderText("You must chose a schoolclass");
				error.showAndWait();

			} else {
				User newUser = new User();
				
				newUser.setfName(txtFirstName.getText());
				newUser.setlName(txtLastName.getText());
				newUser.setUserName(txtUserName.getText());
				newUser.setPassword(txtPassword.getText());
				newUser.setAccountType((String) cmbAccountType.getValue());
				newUser.setSSN(txtSSN.getText());
				newUser.setEmail(txtEmail.getText());
				newUser.setPhone(txtPhoneNumber.getText());
				newUser.setStreet(txtStreet.getText());
				newUser.setZip(Integer.parseInt(txtZipCode.getText()));
				newUser.setCity(txtCity.getText());
				if (cmbAccountType.getValue() == "Student") {
					SchoolClass sc = (SchoolClass) em.createQuery("Select sc from SchoolClass sc where sc.className = '" + cmbStudentsClass.getValue() + "'").getSingleResult();

					sc.addStudent(newUser);
				}
				em.getTransaction().begin();

				em.persist(newUser);
				if (sc != null) {
					em.persist(sc);
				}
				

				em.getTransaction().commit();

				em.close();
				emf.close();

				Alert addedUser = new Alert(AlertType.INFORMATION);
				addedUser.setTitle("New user added");
				addedUser.setHeaderText(null);
				addedUser.setContentText("The user \"" + txtUserName.getText() + "\" has been added");
				addedUser.showAndWait();
				clearButton.fire();

			}
		});

		VBox addUserColumn = new VBox(5);
		addUserColumn.setPadding(new Insets(0));
		addUserColumn.getChildren().addAll(titleBox, enterBoxes, buttons);
		addUserColumn.setStyle(frameStyle);// sets style for addframe
		userPane.getChildren().add(addUserColumn);

		return userPane;
	}

}
