package GUITemplate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import connectivity.AddUser;
import connectivity.Login;
import connectivity.StartUp;
import connectivity.Timer;
import entity.Answers;
import entity.Question;
import entity.SchoolClass;
import entity.StudentAnswer;
import entity.Test;
import entity.User;
import gui.AddNewUser;
import gui.AdminUser;
import gui.ChangeUserInfo;
import gui.QuizmakerGUI;
import gui.StudentHome;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
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
import javafx.stage.StageStyle;

public class GUITemplate extends Application {
	static Timer timer = new Timer();
	private StartUp startUp;
	private Test noTest;
	private AddUser addUser;
	private Login logIn = new Login();
	private BooleanProperty isLoggedIn = new SimpleBooleanProperty(true);
	private BooleanProperty isAdmin = new SimpleBooleanProperty(true);
	private BorderPane root;
	private ChangeUserInfo userInfo;
	private QuizmakerGUI qMakerGUI;
	private StudentHome studentHome = new StudentHome();
	AddNewUser addNewUser = new AddNewUser();
	List<SchoolClass> allClasses = new ArrayList<>();
	List<User> allUsers = new ArrayList<>();
	List<Test> allTests = new ArrayList<>();
	private User user = null;
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
	List<Object> questionAnswersDT = new ArrayList<>();
	
	// List<ToggleGroup> toggleGroupsDT = new ArrayList<>();
	// List<TextArea> textAreasDT = new ArrayList<>();

	Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
	static Button endTest;
	static Button yes;
	static Label lbl;
	Label lbl2;

	AnchorPane centerPane;
	boolean writeLine = false;

	double clickX1;
	double clickY1;

	Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		startUp= new StartUp();
		allClasses = startUp.getClasses();
		allTests = startUp.getTests();
		allUsers = startUp.getUsers();
		
		primaryStage.setTitle("Newton test tool 0.5 Alpha");
		this.primaryStage = primaryStage;

		primaryStage.setOnCloseRequest(e -> {
			e.consume();
			Platfom();
		});

		// Menyer topp

		Menu viewMenu = new Menu("_View");

		CheckMenuItem cssMenuItem = new CheckMenuItem("Show remaining time");
		cssMenuItem.setSelected(true);
		viewMenu.getItems().add(cssMenuItem);

		// Vertikal box till v�nster med knappar och funktioner

		VBox leftMenu = new VBox(20);
		leftMenu.setStyle("-fx-background-color: #F47920;");
		leftMenu.setPadding(new Insets(10, 10, 10, 10));

		btn2 = new Button();
		btn2.setLayoutX(20.0);
		btn2.setLayoutY(110.0);
		btn2.setText(" Login ");
		double a = 10;
		btn2.setShape(new Rectangle(a, a));
		btn2.setMinSize(9 * a, 3 * a);
		btn2.setMaxSize(9 * a, 3 * a);
		btn2.setStyle("-fx-font: 12 Myriad; -fx-base: #F47920;");
		DropShadow shadow = new DropShadow();
		VBox.setMargin(btn2, new Insets(100, 0, 0, 10));

		// Adding the shadow when the mouse cursor is on
		btn2.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn2.setEffect(shadow);
			}
		});
		// Removing the shadow when the mouse cursor is off
		btn2.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn2.setEffect(null);
			}
		});

		btn2.setOnAction(event -> {
			toggleLogin();
		});

		btn3 = new Button();
		btn3.setLayoutX(20.0);
		btn3.setLayoutY(110.0);
		btn3.setText(" Home ");
		double ae = 10;
		btn3.setShape(new Rectangle(ae, ae));
		btn3.setMinSize(9 * ae, 3 * ae);
		btn3.setMaxSize(9 * ae, 3 * ae);
		btn3.setStyle("-fx-font: 12 Myriad; -fx-base: #F47920;");
		DropShadow shadow1 = new DropShadow();
		// btn2.setOnAction(this);
		VBox.setMargin(btn3, new Insets(0, 0, 100, 10));

		btn3.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn3.setEffect(shadow1);
			}
		});
		btn3.setOnAction(event -> {
			centerPane.getChildren().clear();
			centerPane.getChildren().add(studentHome.showPane(user, allTests));

		});

		btn3.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn3.setEffect(null);
			}
		});

		btn4 = new Button();
		btn4.setLayoutX(30.0);
		btn4.setLayoutY(235.0);
		btn4.setText(" START ");
		double r = 30;
		btn4.setShape(new Circle(r));
		btn4.setMinSize(2 * r, 2 * r);
		btn4.setMaxSize(2 * r, 2 * r);
		btn4.setStyle("-fx-font: 12 Myriad; -fx-base: #F47920; -fx-font-weight: bold");

		DropShadow shadow2 = new DropShadow();
		btn4.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn4.setEffect(shadow2);
			}
		});
		btn4.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn4.setEffect(null);
			}
		});
		// START BUTTON
		btn4.setOnAction(event -> {
			Test temp=studentHome.getTest();
			centerPane.getChildren().clear();
			timer.setTest(temp);
			timer.setLabel(lbl);
			System.out.println("\nStart\n");
			timer.setRun(true);
			timer.start();
			lbl.setText(timer.timerText);
			questionAnswersDT.clear();
			canvasesDT.clear();
			questionNumberDT = 0;
			questionsDT.clear();
			testOverviewADT.clear();
			testOverviewQDT.clear();

			startTest(user, temp.getTestName());
			setQuestionNumber();
		});

		VBox.setMargin(btn4, new Insets(0, 0, 0, 25));

		leftMenu.getChildren().addAll(btn2, btn3, btn4);

		// CenterPane

		centerPane = new AnchorPane();
		centerPane.setStyle("-fx-border-color: #000000;");
		centerPane.getChildren().addAll();

		// Horizontal box i nedre kant

		HBox bottomMenu = new HBox();
		bottomMenu.setStyle("-fx-background-color: #F47920;");
		bottomMenu.setPadding(new Insets(10, 10, 10, 10));

		lbl = new Label("Time remaining: 00:00:00");
		lbl.setLayoutX(20.0);
		lbl.setLayoutY(100.0);
		double ad = 10;
		lbl.setShape(new Rectangle(ad, ad));
		lbl.setMinSize(14 * ad, 3 * ad);
		lbl.setStyle("-fx-border-color: #000000;" + "-fx-background-color: #F47920;" + "-fx-text-fill: #000000");
		lbl.setAlignment(Pos.CENTER);
		lbl.setFont(Font.font("Myriad", 12));
		HBox.setMargin(lbl, new Insets(2, 0, 0, 90));

		lbl2 = new Label(" Question 0/0 ");
		lbl2.setLayoutX(20.0);
		lbl2.setLayoutY(110.0);
		double ac = 10;
		lbl2.setShape(new Rectangle(ac, ac));
		lbl2.setMinSize(10 * ac, 3 * ac);
		lbl2.setMaxSize(10 * ac, 3 * ac);
		lbl2.setStyle("-fx-border-color: #000000;" + "-fx-background-color: #F47920;" + "-fx-text-fill: #000000");
		lbl2.setAlignment(Pos.CENTER);
		lbl2.setFont(Font.font("Myriad", 12));
		HBox.setMargin(lbl2, new Insets(2, 0, 0, 3));

		btn5 = new Button();
		btn5.setLayoutX(20.0);
		btn5.setLayoutY(110.0);
		btn5.setText(" Back ");
		double aa = 10;
		btn5.setShape(new Rectangle(aa, aa));
		btn5.setMinSize(8 * aa, 3 * aa);
		btn5.setMaxSize(8 * aa, 3 * aa);
		btn5.setStyle("-fx-font: 12 Myriad; -fx-base: #F47920; -fx-font-weight: bold");
		HBox.setMargin(btn5, new Insets(2, 0, 0, 160));
		DropShadow shadow3 = new DropShadow();
		btn5.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn5.setEffect(shadow3);
			}
		});
		btn5.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn5.setEffect(null);
			}
		});
		// btn5.setOnAction(this)

		btn6 = new Button();
		btn6.setLayoutX(20.0);
		btn6.setLayoutY(110.0);
		btn6.setText(" Next ");
		double ab = 10;
		btn6.setShape(new Rectangle(ab, ab));
		btn6.setMinSize(8 * ab, 3 * ab);
		btn6.setMaxSize(8 * ab, 3 * ab);
		btn6.setStyle("-fx-font: 12 Myriad; -fx-base: #F47920; -fx-font-weight: bold");
		HBox.setMargin(btn6, new Insets(2, 0, 0, 90));
		DropShadow shadow4 = new DropShadow();
		btn6.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn6.setEffect(shadow4);
			}
		});
		btn6.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn6.setEffect(null);
			}
		});
		// btn6.setOnAction(this)

		bottomMenu.getChildren().addAll(btn5, lbl, lbl2, btn6);

		// Scen och Pane inställningar

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(initMenu());
		borderPane.setLeft(leftMenu);
		borderPane.setCenter(centerPane);
		borderPane.setBottom(bottomMenu);

		Scene scene = new Scene(borderPane, 800, 600, Color.rgb(56, 56, 56));
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void Platfom() {
		Boolean answer = ConfirmPane.display("Exit program", "Do you want to exit the program?");
		if (answer)
			Platform.exit();
	}

	public MenuBar initMenu() {
		BooleanBinding canAdd = isAdmin.and(isLoggedIn);
		MenuBar menu = new MenuBar();
		Menu mnuFile = new Menu("File");
		MenuItem mnuLogIn = new MenuItem("Log in");
		MenuItem mnuLogOut = new MenuItem("Log out");
		menu.setStyle("-fx-background-color: #F47920;");
		MenuItem mnuExit = new MenuItem("Exit");
		mnuExit.setOnAction(exitAction -> {
			System.exit(0);
		});

		MenuItem createQuest = new MenuItem("Create Test");
		createQuest.setOnAction(createQ -> {
			qMakerGUI = new QuizmakerGUI();
			centerPane.getChildren().clear();
			centerPane.getChildren().addAll(qMakerGUI.showPane());
		});

		mnuFile.getItems().addAll(mnuLogIn, mnuLogOut, new SeparatorMenuItem(), mnuExit);
		try {
			primaryStage.setTitle("Guest");
		} catch (Exception e) {
			System.out.println(e);
		}
		mnuLogIn.setOnAction(logInAction -> {
			if (!isLoggedIn.get()) {
				toggleLogin();
			}
		});
		mnuLogIn.disableProperty().bind(isLoggedIn);

		mnuLogOut.setOnAction(logOutAction -> {
			toggleLogin();
		});
		mnuLogOut.disableProperty().bind(isLoggedIn.not());

		Menu mnuAdmin = new Menu("Admin");
		MenuItem adminUsers = new MenuItem("Administer Users");
		MenuItem addUser = new MenuItem("Add user");
		addUser.setOnAction(event -> {
			centerPane.getChildren().add(addNewUser.showPane());
		});
		adminUsers.setOnAction(action -> {
			AdminUser userAdmin = new AdminUser();
			centerPane.getChildren().clear();
			centerPane.getChildren().addAll(userAdmin.showPane(root));
		});
		mnuAdmin.disableProperty().bind(canAdd.not());
		mnuAdmin.getItems().addAll(adminUsers, addUser, createQuest);

		Menu mnuStudent = new Menu("Student");
		mnuStudent.disableProperty().bind(isLoggedIn.not());
		MenuItem mnuChangeUser = new MenuItem("Change user info");
		mnuChangeUser.setOnAction(change -> {
			centerPane.getChildren().clear();
			userInfo = new ChangeUserInfo(user);
			centerPane.getChildren().addAll(userInfo.showPane());
		});
		mnuStudent.getItems().add(mnuChangeUser);

		menu.getMenus().addAll(mnuFile, mnuAdmin, mnuStudent);

		return menu;
	}

	public void showLogin() {
		Stage logInStage = new Stage();
		StackPane logInPane = new StackPane();
		Scene logInScene = new Scene(logInPane, 400, 200);
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
		Text wrongLogin = new Text("Wrong username/password");
		wrongLogin.setFont(Font.font(30));
		wrongLogin.setVisible(false);

		VBox loginColumn = new VBox();
		loginColumn.setAlignment(Pos.CENTER);
		loginColumn.getChildren().addAll(title, txtUserName, txtPassword, wrongLogin);
		logInPane.getChildren().addAll(loginColumn);

		// After Login
		txtPassword.setOnAction(e -> {
			user = logIn.login(txtUserName.getText(), txtPassword.getText());
			if (user != null) {
				if (logIn.getAccountType().equals("Admin")) {
					primaryStage.setTitle("Logged in as " + logIn.getName() + " - Admin");
					isAdmin.set(true);
				} else {
					primaryStage.setTitle("Logged in as " + logIn.getName() + " - Student");
				}
				wrongLogin.setVisible(false);
				logInStage.close();
				isLoggedIn.set(true);
				btn2.setText("Log out");
				centerPane.getChildren().add(studentHome.showPane(user,allTests));

			} else {
				wrongLogin.setVisible(true);
			}
		});

	}

	public void addUser() {
		ObservableList<String> accountType = FXCollections.observableArrayList();
		accountType.add("Admin");
		accountType.add("Student");

		Stage addUserStage = new Stage();
		StackPane userPane = new StackPane();
		Scene addUserScene = new Scene(userPane, 400, 250);
		addUserStage.setScene(addUserScene);
		addUserStage.initStyle(StageStyle.UNDECORATED);
		addUserStage.toFront();
		centerPane.getChildren().add(userPane);

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

		TextField txtEmail = new TextField();
		txtEmail.setMaxWidth(200);
		txtEmail.setPromptText("E-mail");

		VBox enterBox = new VBox();
		enterBox.getChildren().addAll(txtUserName, txtPassword, confirmPassword, txtEmail, cmbAccountType);

		Button okButton = new Button("OK");
		Button cancelButton = new Button("Cancel");
		HBox buttons = new HBox();
		okButton.requestFocus();
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
				}
				addUserStage.close();
			}
		});

		VBox addUserColumn = new VBox();
		addUserColumn.getChildren().addAll(titleBox, enterBox, buttons);
		userPane.getChildren().add(addUserColumn);
	}

	// TODO----------------------------START LEO
	// KOD!--------------------------------------
	public void startTest(User user, String testName) {
		this.centerPaneDT = centerPane;

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();
		Query test = em.createNamedQuery("get tests");
		testNameDT = "Test i JPA";
		test.setParameter("name", testName);
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

		// Make array-size to fit test
		for (int i = 0; i < questionsDT.size(); i++) {
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

			Scene sceneDT = new Scene(root, 800, 350);

			// TopLeft Corner
			VBox topLeft = new VBox();
			Text titleText = new Text(questionsDT.get(i).getQuestionTitle());
			Text questionText = new Text(questionsDT.get(i).getQuestionText());
			questionText.setWrappingWidth(300);

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
//			HBox topRight = new HBox(10);
//			topRight.spacingProperty().set(10);
//
//			top.setTop(topRight);
//			topRight.setAlignment(Pos.TOP_RIGHT);
//			StackPane spoilerHolder = new StackPane();
//			Rectangle spoilerTimer = new Rectangle(40, 15, Color.BLACK);
//			Rectangle spoilerDetection = new Rectangle(40, 15, Color.TRANSPARENT);
//			Text timerText = new Text("time");
//			timerText.setFill(Color.WHITE);
//			timerText.setStroke(Color.WHITE);
//			Label timer = new Label("18:45");
//			Label questionNr = new Label((i + 1) + "/" + (questionsDT.size()));
//			spoilerHolder.getChildren().addAll(timer, spoilerTimer, timerText);
//			spoilerHolder.getChildren().add(spoilerDetection);
//			topRight.getChildren().addAll(spoilerHolder, questionNr);
//			GridPane.setConstraints(top, 2, 0);
			root.getChildren().addAll(top);

			// BottomRight
			GridPane bottomRight = new GridPane();
			root.getChildren().addAll(bottomRight);

			// BottomLeft / BottomCenter
			BorderPane questionHolder = new BorderPane();
			BorderPane answerHolder = new BorderPane();
			answerHolder.setPrefSize(500, 250);

			// v---------------------Mall för text fråga
			if (questionsDT.get(i).getquestionType().getQuestionType().equals("Fritext")) {
				answerHolder.setCenter(questionTextArea);
				GridPane.setConstraints(answerHolder, 0, 1, 2, 1);
				root.getChildren().addAll(answerHolder);
				questionAnswersDT.add(questionTextArea);
			}
			// ^---------------------Mall för text fråga

			// v---------------------Mall för radiobutton fråga
			if (questionsDT.get(i).getquestionType().getQuestionType().equals("Radiobuttons")) {
				VBox toggleButtons = new VBox(10);
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
				questionAnswersDT.add(group);

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

//			root.setGridLinesVisible(true);
//			bottomRight.setGridLinesVisible(true);
			canvasesDT.add(root);

//			spoilerDetection.setOnMouseEntered(event -> {
//				spoilerTimer.setFill(Color.TRANSPARENT);
//				timerText.setFill(Color.TRANSPARENT);
//				timerText.setStroke(Color.TRANSPARENT);
//
//			});
//			spoilerDetection.setOnMouseExited(event -> {
//				spoilerTimer.setFill(Color.BLACK);
//				timerText.setFill(Color.WHITE);
//				timerText.setStroke(Color.WHITE);
//
//			});

		}

		// LastCanvas /START

		BorderPane rootLC = new BorderPane();
		Scene lastCanvas = new Scene(rootLC, 800, 350);
		endTest = new Button("End Test");
		rootLC.setCenter(endTest);
		canvasesDT.add(rootLC);

		// LastCanvas /SLUT

		// Overview för provets svar /START
		BorderPane rootOV = new BorderPane();
		Scene sceneOV = new Scene(rootOV, 800, 350);

		Text overviewTitle = new Text("Overview");
		overviewTitle.setFont(Font.font(18));
		TextArea overview = new TextArea();
		overview.setEditable(false);

		HBox rightContainer = new HBox(12);
		BorderPane leftContainer = new BorderPane();
		rightContainer.setPadding(new Insets(0, 3000, 20, 30));
		rightContainer.setAlignment(Pos.BOTTOM_LEFT);

		yes = new Button("Skicka in");
		Button cancel = new Button("Cancel");
		rightContainer.getChildren().addAll(yes, cancel);
		leftContainer.setTop(overviewTitle);
		leftContainer.setCenter(overview);

		rootOV.setLeft(leftContainer);
		rootOV.setRight(rightContainer);

		centerPaneDT.getChildren().add(canvasesDT.get(0));

		cancel.setOnAction(event -> {
			centerPane.getChildren().clear();
			centerPaneDT.getChildren().add(canvasesDT.get(0));
			questionNumberDT = 0;
			overview.setText("");
			setQuestionNumber();

		});
		endTest.setOnAction(event -> {
			centerPane.getChildren().clear();
			overview.appendText(getAnswersForOverview());
			centerPaneDT.getChildren().add(rootOV);
		});

		yes.setOnAction(event -> {
			
			for (int i = 0; i < questionsDT.size(); i++) {
				try{
				sendToDatabase(user, questionsDT.get(i), testOverviewADT.get(i).toString(), em);
				}catch(NullPointerException e){
					System.out.println(e);
				}
			}
			centerPaneDT.getChildren().clear();
			centerPane.getChildren().clear();
		});

		// Overview för provets svar /SLUT

		btn5.setOnMouseClicked(event -> {
			testOverviewQDT.set(questionNumberDT, questionsDT.get(questionNumberDT).getQuestionText());

			if (questionsDT.get(questionNumberDT).getquestionType().getQuestionType().equals("Fritext")) {
				testOverviewADT.set(questionNumberDT, ((TextArea) (questionAnswersDT.get(questionNumberDT))).getText());
			}
			if (questionsDT.get(questionNumberDT).getquestionType().getQuestionType().equals("Radiobuttons")) {
				try {
					testOverviewADT.set(questionNumberDT,
							((RadioButton) (((ToggleGroup) questionAnswersDT.get(questionNumberDT))
									.getSelectedToggle())).getText());

				} catch (Exception e) {
					System.out.println(e);
				}
			}
			changeQuestion("-");
			System.out.println(questionNumberDT);
		});

		btn6.setOnMouseClicked(event -> {
			testOverviewQDT.set(questionNumberDT, questionsDT.get(questionNumberDT).getQuestionText());

			if (questionsDT.get(questionNumberDT).getquestionType().getQuestionType().equals("Fritext")) {
				testOverviewADT.set(questionNumberDT, ((TextArea) (questionAnswersDT.get(questionNumberDT))).getText());
			}

			if (questionsDT.get(questionNumberDT).getquestionType().getQuestionType().equals("Radiobuttons")) {
				try {
					testOverviewADT.set(questionNumberDT,
							((RadioButton) (((ToggleGroup) questionAnswersDT.get(questionNumberDT))
									.getSelectedToggle())).getText());

				} catch (Exception e) {
					System.out.println(e);
				}
			}

			changeQuestion("+");
			System.out.println(questionNumberDT);
		});

	}

	public void showDoneButton() {
		for (int i = 0; i <= canvasesDT.size() - 1; i++) {

		}
	}

	public String getAnswersForOverview() {
		String overview = "";
		for (int i = 0; i < testOverviewADT.size(); i++) {
			overview += testOverviewQDT.get(i) + ": " + testOverviewADT.get(i) + "\n";

		}
		return overview;

	}

	public void changeQuestion(String cond) {
		if (questionNumberDT < 1 & cond == "-") {
			centerPane.getChildren().clear();
			this.questionNumberDT = canvasesDT.size() - 1;
			centerPaneDT.getChildren().add(canvasesDT.get(questionNumberDT));
			setQuestionNumber();
		} else if (questionNumberDT >= (canvasesDT.size() - 1) & cond == "+") {
			centerPane.getChildren().clear();
			this.questionNumberDT = 0;
			centerPaneDT.getChildren().add(canvasesDT.get(questionNumberDT));
			setQuestionNumber();
			goneThroughDT = true;

		} else {
			switch (cond) {
			case "+":
				centerPane.getChildren().clear();
				questionNumberDT++;
				centerPaneDT.getChildren().add(canvasesDT.get(questionNumberDT));
				setQuestionNumber();
				break;
			case "-":
				centerPane.getChildren().clear();
				questionNumberDT--;
				centerPaneDT.getChildren().add(canvasesDT.get(questionNumberDT));
				setQuestionNumber();
				break;
			}
		}
	}

	public void setQuestionNumber() {
		lbl2.setText(" Question " + (questionNumberDT + 1) + "/" + (questionsDT.size()) + " ");
	}

	public void toggleLogin() {
		if (isLoggedIn.get()) {
			primaryStage.setTitle("Not logged in");
			centerPane.getChildren().clear();
			isLoggedIn.set(false);
			isAdmin.set(false);
			btn2.setText("Login");
			studentHome.setTest(noTest);
		} else if (!isLoggedIn.get()) {
			showLogin();

		}
	}

	public void sendToDatabase(User user, Question question, String answer, EntityManager em) {
		StudentAnswer studentAnswer = new StudentAnswer();
		studentAnswer.setUser(user);
		studentAnswer.setQuestion(question);
		studentAnswer.setAnswer(answer);
		em.getTransaction().begin();
		em.persist(studentAnswer);
		em.getTransaction().commit();
	}
	
	public static void setLabelTime(long days, long hours, long minutes, long seconds){
		lbl.setText("Time remaining: "+days+":"+ hours+":"+ minutes+":"+ seconds);
		checkTime(days,hours,minutes,seconds);
	}
	
	public static void checkTime(long days, long hours, long minutes, long seconds){
		if(seconds<1){
			if(minutes<1){
				if(hours<1){
					if(days<1){
						endTest.fire();
						yes.fire();
						timer.setRun(false);
					}
				}
			}
		}
		
	}
	

	// TODO----------------------------SLUT LEO

}