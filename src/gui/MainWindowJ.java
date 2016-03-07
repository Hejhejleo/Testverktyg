package gui;

import connectivity.AddUser;
import connectivity.Login;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainWindowJ extends Application {
	private AddUser addUser;
	private Login logIn;
	// No Comment
	private BooleanProperty isLoggedIn = new SimpleBooleanProperty(true);
	private BooleanProperty isAdmin = new SimpleBooleanProperty(true);
	private Stage stage;
	private String whiteBGAndGreyBorder = "-fx-background-color: #FFFFFF; " + "-fx-border-color: #D3D3D3";
	private String whiteBackground = "-fx-background-color: #FFFFFF";
	private String frameStyle = "-fx-border-color: #FFA500; " + "-fx-border-width: 2px; "
			+ "-fx-background-color: #FFFFFF";

	public void start(Stage stage) {
		addUser = new AddUser();
		logIn = new Login();
		this.stage = stage;
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root,800,600);  
		Pane backgroundPane = new Pane(); // TODO bakgrundsf�rg
		backgroundPane.setStyle(whiteBackground);
		root.setCenter(backgroundPane);
		stage.setScene(scene);
		stage.setMaximized(true);

		stage.show();
		stage.setOnCloseRequest(close -> {
			System.exit(0);
		});
		root.setTop(initMenu());
	}

	public static void main(String[] args) {
		launch(args);
	}

	public MenuBar initMenu() {
		BooleanBinding canAdd = isAdmin.and(isLoggedIn);
		MenuBar menu = new MenuBar();
		Menu mnuFile = new Menu("File");
		MenuItem mnuLogIn = new MenuItem("Log in");
		MenuItem mnuLogOut = new MenuItem("Log out");

		MenuItem mnuAddUser = new MenuItem("Add user");
		mnuAddUser.disableProperty().bind(canAdd.not());
		mnuAddUser.setOnAction(addUserAction -> {
			addUser();
		});

		MenuItem mnuExit = new MenuItem("Exit");
		mnuExit.setOnAction(exitAction -> {
			System.exit(0);
		});

		mnuFile.getItems().addAll(mnuLogIn, mnuLogOut, mnuAddUser, new SeparatorMenuItem(), mnuExit);
		stage.setTitle("Guest");
		mnuLogIn.setOnAction(logInAction -> {
			if (!isLoggedIn.get()) {
				showLogin();
			}
		});
		mnuLogIn.disableProperty().bind(isLoggedIn); // TODO properties
														// bindings???

		mnuLogOut.setOnAction(logOutAction -> {
			stage.setTitle("Guest");
			isLoggedIn.set(false);
			isAdmin.set(false);
		});
		mnuLogOut.disableProperty().bind(isLoggedIn.not());
		menu.getMenus().add(mnuFile);
		menu.setStyle(whiteBGAndGreyBorder); // TODO
												// str�ngvariabel?
		return menu;
	}

	// LOGIN VIEW
	public void showLogin() {
		Stage logInStage = new Stage();
		StackPane logInPane = new StackPane();
		Scene logInScene = new Scene(logInPane, 500, 300);
		logInStage.setScene(logInScene);
		logInStage.initStyle(StageStyle.UNDECORATED);
		logInStage.show();
		Text loginTitle = new Text("Log in");
		loginTitle.setFont(Font.font(40));
		HBox title = new HBox();
		title.setPadding(new Insets(10, 20, 20, 20));
		title.getChildren().add(loginTitle);
		title.setAlignment(Pos.CENTER);
		TextField txtUserName = new TextField();
		loginTitle.requestFocus();
		txtUserName.setPromptText("Username"); // TODO syns inte vid start, pga
												// fokus?
		txtUserName.setMaxWidth(200);
		PasswordField txtPassword = new PasswordField();
		txtPassword.setMaxWidth(200);
		txtPassword.setPromptText("Password");
		Text wrongLogin = new Text("Wrong username/password");
		wrongLogin.setFont(Font.font(30));
		wrongLogin.setVisible(false);

		VBox loginColumn = new VBox(5);
		loginColumn.setPadding(new Insets(5, 10, 5, 10)); // TODO Insets i
		// variabel?
		loginColumn.setStyle(frameStyle); // sets style for loginframe

		loginColumn.setAlignment(Pos.CENTER);
		loginColumn.getChildren().addAll(title, txtUserName, txtPassword, wrongLogin);
		logInPane.getChildren().addAll(loginColumn);

		txtPassword.setOnAction(e -> {
			if (logIn.login(txtUserName.getText(), txtPassword.getText())) {
				if (logIn.getAccountType().equals("Admin")) {
					stage.setTitle("Logged in as " + logIn.getName() + " - Admin");
					isAdmin.set(true);
				} else {
					stage.setTitle("Logged in as " + logIn.getName() + " - Student");
				}
				wrongLogin.setVisible(false);
				logInStage.close();
				isLoggedIn.set(true);
			} else {
				wrongLogin.setVisible(true);
			}
		});

	}

	// ADD USER VIEW
	public void addUser() {
		ObservableList<String> accountType = FXCollections.observableArrayList();
		accountType.add("Admin");
		accountType.add("Student");

		Stage addUserStage = new Stage();
		StackPane userPane = new StackPane();
		Scene addUserScene = new Scene(userPane, 400, 500);
		addUserStage.setScene(addUserScene);
		addUserStage.initStyle(StageStyle.UNDECORATED);
		addUserStage.toFront();
		addUserStage.show();

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
		txtSSN.setPromptText("SSN");
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
		txtPhoneNumber.setPromptText("Phone number");// TODO kolla att det bara
														// �r siffror i
														// inmatning?
		txtPhoneNumber.setMaxWidth(200);

		TextField txtUserName = new TextField();
		txtUserName.setPromptText("Username"); // TODO syns inte vid start, pga
												// fokus?
		txtUserName.setMaxWidth(200);

		PasswordField txtPassword = new PasswordField();
		txtPassword.setMaxWidth(200);
		txtPassword.setPromptText("Password"); // TODO instruktion f�r
												// format/riktlinjer? Mouseover?
		PasswordField confirmPassword = new PasswordField();
		confirmPassword.setPromptText("Confirm password");
		confirmPassword.setMaxWidth(200);

		ComboBox cmbAccountType = new ComboBox();
		cmbAccountType.setItems(accountType);
		cmbAccountType.setStyle(whiteBGAndGreyBorder); // TODO
														// rundade
														// h�rn?
		cmbAccountType.setCursor(Cursor.HAND);
		cmbAccountType.setPromptText("Account type");

		TextField txtEmail = new TextField();
		txtEmail.setMaxWidth(200);
		txtEmail.setPromptText("E-mail");

		VBox enterBox = new VBox(5);
		enterBox.setPadding(new Insets(5, 10, 5, 10));
		enterBox.getChildren().addAll(txtFirstName, txtLastName, txtSSN, txtStreet, txtZipCode, txtCity, txtPhoneNumber,
				txtEmail, txtUserName, txtPassword, confirmPassword, cmbAccountType);

		Button okButton = new Button("OK");
		Button cancelButton = new Button("Cancel");
		okButton.setStyle(whiteBGAndGreyBorder);
		BorderPane buttons = new BorderPane();
		okButton.requestFocus();
		okButton.setCursor(Cursor.HAND);
		cancelButton.setStyle(whiteBGAndGreyBorder);
		cancelButton.setCursor(Cursor.HAND);

		buttons.setLeft(okButton);
		buttons.setRight(cancelButton);
		buttons.setPadding(new Insets(5, 10, 10, 10));

		cancelButton.setOnAction(cancel -> {
			addUserStage.close();
		});

		String regex = "\\d+";

		okButton.setOnAction(ok -> {
			Alert error = new Alert(AlertType.ERROR);
			if (txtFirstName.getText().equals("")) {
				error.setTitle("No first name was entered");
				error.setHeaderText("You must enter a first name");
				error.showAndWait();
			} else if (txtLastName.getText().equals("")) {
				error.setTitle("No last name was entered");
				error.setHeaderText("You must enter a last name");
				error.showAndWait();
			} else if (txtSSN.getText().equals("")) {
				error.setTitle("No SSN was entered");
				error.setHeaderText("You must enter a SNN");
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
			} else if (txtPhoneNumber.getText().equals("")) {
				error.setTitle("No phone number was entered");
				error.setHeaderText("You must enter a phone number");
				error.showAndWait();
			} else if (txtUserName.getText().equals("")) {
				error.setTitle("No username chosen");
				error.setHeaderText("You must enter a username");
				error.showAndWait();
			} else if (!(confirmPassword.getText().equals(txtPassword.getText()))) {
				error.setTitle("Password mismatch");
				error.setHeaderText("The passwords must be the same");
				error.showAndWait();
			} else if (cmbAccountType.getValue() == null) {
				error.setTitle("No accounttype chosen");
				error.setHeaderText("You must choose an account type");
				error.showAndWait();
			} else if (!(txtEmail.getText().contains("@"))) {
				error.setTitle("Incorrect e-mail");
				error.setHeaderText("You have not entered a valid e-mail");
				error.showAndWait();
			} else {
				if (addUser.addUser(txtUserName.getText(), txtPassword.getText(), cmbAccountType.getValue().toString(),
						txtEmail.getText())) {
					error.setTitle("User added");
					error.setHeaderText("User " + txtUserName.getText() + " added");
					error.showAndWait();
				} else {
					error.setTitle("User exists");
					error.setHeaderText("User " + txtUserName.getText() + " already exists");
					error.showAndWait();
					addUser();
				}
				addUserStage.close();
			}
		});

		VBox addUserColumn = new VBox(5);
		addUserColumn.setPadding(new Insets(0));
		addUserColumn.getChildren().addAll(titleBox, enterBox, buttons);
		addUserColumn.setStyle(frameStyle);// sets style for addframe
		userPane.getChildren().add(addUserColumn);
	}
}
