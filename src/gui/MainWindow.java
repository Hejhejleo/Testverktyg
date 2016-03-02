package gui;

import dao.DAO;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainWindow extends Application {
	private DAO dao;
	private BooleanProperty isLoggedIn = new SimpleBooleanProperty(false);
	private BooleanProperty isAdmin = new SimpleBooleanProperty(false);
	private Stage stage;
	
	public void start(Stage stage) {
		dao = new DAO();
		this.stage = stage;
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 800, 600);  
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
		
		mnuFile.getItems().addAll(mnuLogIn, mnuLogOut, mnuAddUser);
		stage.setTitle("Guest");
		mnuLogIn.setOnAction(logInAction -> {
			if (!canAdd.get()) {
				showLogin();
			}
		});
		
		mnuLogOut.setOnAction(logOutAction -> {
			isLoggedIn.set(false);
			isAdmin.set(false);
		});
		
		menu.getMenus().add(mnuFile);
		
		
		return menu;
	}
	
	public void showLogin() {
		Stage logInStage = new Stage();
		StackPane logInPane = new StackPane();
		Scene logInScene = new Scene(logInPane, 250, 150);
		logInStage.setScene(logInScene);
		logInStage.initStyle(StageStyle.UNDECORATED);
		logInStage.show();
		
		
		Text loginTitle = new Text("Log in");
		loginTitle.setFont(Font.font(50));
		HBox title = new HBox();
		title.getChildren().add(loginTitle);
		title.setAlignment(Pos.CENTER);
		TextField txtUserName = new TextField();
		loginTitle.requestFocus();
		txtUserName.setPromptText("Username");
		txtUserName.setMaxWidth(200);
		PasswordField txtPassword = new PasswordField();
		txtPassword.setMaxWidth(200);
		txtPassword.setPromptText("Password");
		VBox loginColumn = new VBox();
		loginColumn.setAlignment(Pos.CENTER);
		loginColumn.getChildren().addAll(title,txtUserName, txtPassword);
		logInPane.getChildren().addAll(loginColumn);
		txtPassword.setOnAction(e -> {
			isLoggedIn.set(true);
			isAdmin.set(true);
			stage.setTitle("Logged in as admin");
			logInStage.close();
		});
		
	}
	
	public void addUser() {
		ObservableList<String> accountType = FXCollections.observableArrayList();
		accountType.add("Admin");
		accountType.add("Student");
		
		Stage addUserStage = new Stage();
		StackPane userPane = new StackPane();
		Scene addUserScene = new Scene(userPane, 400, 200);
		addUserStage.setScene(addUserScene);
		addUserStage.initStyle(StageStyle.UNDECORATED);
		addUserStage.toFront();
		addUserStage.show();
		
		Text titleText = new Text("Add user account");
		titleText.setFont(Font.font(30));
		HBox titleBox = new HBox();
		titleBox.setAlignment(Pos.CENTER);
		titleBox.getChildren().add(titleText);
		
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
		cmbAccountType.setPromptText("Account type");
		
		VBox enterBox = new VBox();
		enterBox.getChildren().addAll(txtUserName, txtPassword, confirmPassword, cmbAccountType);
		
		Button okButton = new Button("OK");
		Button cancelButton = new Button("Cancel");
		HBox buttons = new HBox();
		buttons.getChildren().addAll(okButton, cancelButton);
		
		cancelButton.setOnAction(cancel -> {
			addUserStage.close();
		});
		
		okButton.setOnAction(ok -> {
			Alert error = new Alert(AlertType.ERROR);
			if (!(confirmPassword.getText().equals(txtPassword.getText()))) {
				error.setTitle("Password mismatch");
				error.setHeaderText("The passwords must be the same");
				error.showAndWait();
			} 
			else if (cmbAccountType.getValue()==null) {
				error.setTitle("No accounttype chosen");
				error.setHeaderText("You must choose an account type");
				error.showAndWait();
			} else {
				dao.addUser(txtUserName.getText(), txtPassword.getText(), cmbAccountType.getValue().toString());
				addUserStage.close();
			}
			
			
		});
		
		VBox addUserColumn = new VBox();
		addUserColumn.getChildren().addAll(titleBox, enterBox, buttons);
		userPane.getChildren().add(addUserColumn);
	}
}
