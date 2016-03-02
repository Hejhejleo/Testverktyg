import java.awt.Toolkit;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

public class Main extends Application {
	
	private BooleanProperty isLoggedIn = new SimpleBooleanProperty(false);
	private BooleanProperty isAdmin = new SimpleBooleanProperty(false);
	private Stage stage;
	
	public void start(Stage stage) {
		this.stage = stage;
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
				Toolkit.getDefaultToolkit().getScreenSize().getHeight());
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
		Menu mnuFile = new Menu("Arkiv");
		MenuItem mnuLogIn = new MenuItem("Logga in");
		MenuItem mnuLogOut = new MenuItem("Logga ut");
		
		MenuItem mnuAddUser = new MenuItem("Lägg till användare");
		mnuAddUser.disableProperty().bind(canAdd.not());
		
		mnuFile.getItems().addAll(mnuLogIn, mnuLogOut, mnuAddUser);
		stage.setTitle("Gäst");
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
		
		
		Text loginTitle = new Text("Logga in");
		loginTitle.setFont(Font.font(50));
		HBox title = new HBox();
		title.getChildren().add(loginTitle);
		title.setAlignment(Pos.CENTER);
		TextField txtUserName = new TextField();
		loginTitle.requestFocus();
		txtUserName.setPromptText("Användarnamn");
		txtUserName.setMaxWidth(200);
		PasswordField txtPassword = new PasswordField();
		txtPassword.setMaxWidth(200);
		txtPassword.setPromptText("Lösenord");
		VBox loginColumn = new VBox();
		loginColumn.setAlignment(Pos.CENTER);
		loginColumn.getChildren().addAll(title,txtUserName, txtPassword);
		logInPane.getChildren().addAll(loginColumn);
		txtPassword.setOnAction(e -> {
			isLoggedIn.set(true);
			isAdmin.set(true);
			stage.setTitle("Inloggad som admin");
			logInStage.close();
		});
		
	}
}
