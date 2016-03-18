/* GUITemplate build upon a boarderPane, top, left, center and bottom.
* top Pane has all the top menu and menuItems, Left has all general used nodes like buttons
* centerPane displays all the different input and output panes generated from external classes,
* bottomPane are used for displaying information and actions controls for specific tasks like
* buttons for next page or go back. CSS are used to do effects on buttons but coded in the
* GUITemplate. Main purpose for GUITemplate to give a common set of components to give 
* one form and view to fit all.
* 
* As of today each input and output pane has its own set of components not generated from the
* GUITemplate. Those could if wanted to be integrated and a part of the general view to allow
* less differences in graphical looks.
* 
* In this class you also have the methods and logic for loading and sends student answers witch
* sending Date to Timer class. Timer class sends remain time for the test. When time runs out
* and test is not finished, it will automatically stop test show the result at that point and
* send it to the database.
* 
* 
*/

package GUITemplate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
import gui.GradingTest;
import gui.QuizmakerGUI;
import gui.StudentHome;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
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
	static Timer timer;
	private StartUp startUp;
	private Test noTest;
	private Login logIn = new Login();
	private BooleanProperty isLoggedIn = new SimpleBooleanProperty(true);
	private BooleanProperty isAdmin = new SimpleBooleanProperty(true);
	private static BooleanProperty isTestPicked = new SimpleBooleanProperty(false);
	private BorderPane root;
	private ChangeUserInfo userInfo;
	private QuizmakerGUI qMakerGUI;
	private GradingTest gTest;
	private StudentHome studentHome = new StudentHome();
	private AddNewUser addNewUser = new AddNewUser();
	private List<SchoolClass> allClasses = new ArrayList<>();
	private List<User> allUsers = new ArrayList<>();
	private List<Test> allTests = new ArrayList<>();
	private User user = null;
	private AnchorPane centerPaneDT;
	private String testNameDT;
	private int questionNumberDT = 0;
	private Answers answerDT;
	private Boolean goneThroughDT = false;
	private List<Pane> canvasesDT = new ArrayList<>();
	private List<Question> questionsDT = new ArrayList<>();
	private List<String> testOverviewQDT = new ArrayList<>();
	private List<String> testOverviewADT = new ArrayList<>();
	private User userDT;
	private List<Object> questionAnswersDT = new ArrayList<>();

	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
	private static Button endTest;
	private static Button yes;
	private static Label lbl1;
	private Label lbl2;

	private AnchorPane centerPane;
	private boolean writeLine = false;

	private double clickX1;
	private double clickY1;

	Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		startUp = new StartUp();
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

		// Vertikal box till v�nster med knappar och funktioner

		VBox leftMenu = new VBox(20);
		leftMenu.setPadding(new Insets(10, 10, 10, 10));

		btn2 = new Button();
		btn2.setLayoutX(20.0);
		btn2.setLayoutY(110.0);
		btn2.setText(" Login ");
		double a = 10;
		btn2.setShape(new Rectangle(a, a));
		btn2.setMinSize(9 * a, 3 * a);
		btn2.setMaxSize(9 * a, 3 * a);
		btn2.setStyle("-fx-font: 12 Myriad; -fx-base: #f69651;");
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
		btn3.setStyle("-fx-font: 12 Myriad; -fx-base: #f69651;");
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

		BooleanBinding canStart = isTestPicked.and(isLoggedIn);
		btn4 = new Button();
		btn4.setLayoutX(30.0);
		btn4.setLayoutY(235.0);
		btn4.setText(" START ");
		double r = 35;
		btn4.setShape(new Circle(r));
		btn4.setMinSize(2 * r, 2 * r);
		btn4.setMaxSize(2 * r, 2 * r);
		btn4.setStyle("-fx-font: 12 Myriad; -fx-base: #f69651; -fx-font-weight: bold");
		btn4.disableProperty().bind(canStart.not());

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
			Test temp = studentHome.getTest();
			centerPane.getChildren().clear();
			timer = new Timer();
			timer.setTest(temp);
			timer.setLabel(lbl1);
			System.out.println("\nStart\n");
			timer.setRun(true);
			timer.start();
			lbl1.setText(timer.timerText);
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
		// centerPane.setStyle("-fx-border-color: #000000;");
		centerPane.getChildren().addAll();

		// Horizontal box i nedre kant

		HBox bottomMenu = new HBox();
		bottomMenu.setStyle("-fx-background-color: #f69651;");
		bottomMenu.setPadding(new Insets(10, 10, 10, 10));

		lbl1 = new Label("Time remaining: 00:00:00");
		lbl1.setLayoutX(20.0);
		lbl1.setLayoutY(100.0);
		double ad = 10;
		lbl1.setShape(new Rectangle(ad, ad));
		lbl1.setMinSize(14 * ad, 3 * ad);
		lbl1.setStyle("-fx-border-color: #000000;" + "-fx-background-color: #f69651;" + "-fx-text-fill: #000000");
		lbl1.setAlignment(Pos.CENTER);
		lbl1.setFont(Font.font("Myriad", 12));
		HBox.setMargin(lbl1, new Insets(2, 0, 0, 90));

		lbl2 = new Label(" Question 0/0 ");
		lbl2.setLayoutX(20.0);
		lbl2.setLayoutY(110.0);
		double ac = 10;
		lbl2.setShape(new Rectangle(ac, ac));
		lbl2.setMinSize(10 * ac, 3 * ac);
		lbl2.setMaxSize(10 * ac, 3 * ac);
		lbl2.setStyle("-fx-border-color: #000000;" + "-fx-background-color: #f69651;" + "-fx-text-fill: #000000");
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
		btn5.setStyle("-fx-font: 12 Myriad; -fx-base: #f69651; -fx-font-weight: bold");
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
		btn6.setStyle("-fx-font: 12 Myriad; -fx-base: #f69651; -fx-font-weight: bold");
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

		bottomMenu.getChildren().addAll(btn5, lbl1, lbl2, btn6);

		// Scen och Pane inställningar

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(initMenu());
		borderPane.setLeft(leftMenu);
		borderPane.setCenter(centerPane);
		borderPane.setBottom(bottomMenu);

		Scene scene = new Scene(borderPane, 1000, 800, Color.rgb(56, 56, 56));
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
		menu.setStyle("-fx-background-color: #f69651;");
		MenuItem mnuExit = new MenuItem("Exit");
		mnuExit.setOnAction(exitAction -> {
			System.exit(0);
		});

		Menu mnuAdmin = new Menu("Admin");
		MenuItem adminUsers = new MenuItem("Administer Users");
		MenuItem addUser = new MenuItem("Add user");
		addUser.setOnAction(event -> {
			centerPane.getChildren().clear();
			centerPane.getChildren().add(addNewUser.showPane(allClasses, allUsers, centerPane));
		});
		adminUsers.setOnAction(action -> {
			AdminUser userAdmin = new AdminUser();
			centerPane.getChildren().clear();
			centerPane.getChildren().addAll(userAdmin.showPane(root, allClasses, allUsers));
		});
		mnuAdmin.disableProperty().bind(canAdd.not());

		MenuItem createQuest = new MenuItem("Manage Tests");
		createQuest.setOnAction(createQ -> {
			qMakerGUI = new QuizmakerGUI();
			centerPane.getChildren().clear();
			centerPane.getChildren().addAll(qMakerGUI.showPane());
		});

		Menu gradingTest = new Menu("Grade Test"); // TODO
		gradingTest.setOnAction(gradeEvent -> {
			gTest = new GradingTest(gradingTest);
			centerPane.getChildren().clear();
			centerPane.getChildren().addAll(gTest.showGradingPane(centerPane));
		});
		mnuAdmin.getItems().addAll(adminUsers, addUser, createQuest, gradingTest);

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

		Menu mnuStudent = new Menu("Student");
		mnuStudent.disableProperty().bind(isLoggedIn.not());
		MenuItem mnuChangeUser = new MenuItem("Change user info");
		mnuChangeUser.setOnAction(change -> {
			if (user != null) {
				centerPane.getChildren().clear();
				userInfo = new ChangeUserInfo(user);
				centerPane.getChildren().addAll(userInfo.showPane());
			}
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
			user = logIn.login(txtUserName.getText(), txtPassword.getText(), allUsers);
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
				centerPane.getChildren().add(studentHome.showPane(user, allTests));

			} else {
				wrongLogin.setVisible(true);
			}
		});

	}

	// TODO----------------------------START LEO
	// Här är koden som hämtar alla frågor och parsar om dem till borderpanes
	// med "info"

	public void startTest(User user, String testName) {
		this.centerPaneDT = centerPane;

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();
		Query test = em.createNamedQuery("get tests");
		test.setParameter("name", testName);
		// get questions from DB
		List<Test> testsList = new ArrayList<>(test.getResultList());
		List<Answers> answersList = new ArrayList<>(em.createQuery("select a from Answers a").getResultList());

		Test currentTest = new Test();
		currentTest = testsList.get(0);

		for (int i = 0; i < currentTest.getQuestions().size(); i++) {
			questionsDT.add(currentTest.getQuestions().get(i));
			System.out.println(questionsDT.get(i).getQuestionText());
		}

		// Make array-size to have empty places for all the questions
		for (int i = 0; i < questionsDT.size(); i++) {
			testOverviewADT.add(null);
			testOverviewQDT.add(null);
			System.out.println(testOverviewADT.get(i));
		}
		// RootGrid

		// Make scenes
		for (int i = 0; i <= (questionsDT.size() - 1); i++) {
			for (int j = 0; j <= (answersList.size() - 1); j++) {
				if (answersList.get(j).getQuestion() == questionsDT.get(i)) {
					answerDT = answersList.get(j);
				}

			}
			TextArea questionTextArea = new TextArea();
			ToggleGroup group = new ToggleGroup();
			GridPane root = new GridPane();

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

			canvasesDT.add(root);
		}

		// LastCanvas /START (End test knappen)

		BorderPane rootLC = new BorderPane();
		Scene lastCanvas = new Scene(rootLC, 800, 350);
		endTest = new Button("Check answers");
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

		yes = new Button("Send in");
		Button cancel = new Button("Change answers");
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
				try {
					sendToDatabase(user, questionsDT.get(i), testOverviewADT.get(i).toString(), em);
				} catch (NullPointerException e) {
					System.out.println(e);
				}
			}
			timer.stop();
			studentHome.setTest(noTest);
			centerPaneDT.getChildren().clear();
			centerPane.getChildren().clear();
			timer.setRun(false);
			lbl1.setText("Time remaining: 00:00:00");
			// TODO
		});

		// Overview för provets svar /SLUT

		// Question--
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
		// Question++
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

	public static void setLabelTime(long days, long hours, long minutes, long seconds) {
		lbl1.setText("Time remaining: " + days + ":" + hours + ":" + minutes + ":" + seconds);
		checkTime(days, hours, minutes, seconds);
	}

	public static void checkTime(long days, long hours, long minutes, long seconds) {
		if (seconds < 1) {
			if (minutes < 1) {
				if (hours < 1) {
					if (days < 1) {
						endTest.fire();
						yes.fire();
						timer.setRun(false);
					}
				}
			}
		}

	}

	public static void setPicked(Boolean value) {
		isTestPicked.set(value);
	}

}
