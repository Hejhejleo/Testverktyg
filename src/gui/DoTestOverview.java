package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DoTestOverview extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setMaximized(true);
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 600, 800);

		TextArea overview = new TextArea();

		HBox rightContainer = new HBox(12);
		
		Button yes = new Button("Yes");
		Button no = new Button("No");
		rightContainer.getChildren().addAll(yes, no);

		root.setLeft(overview);
		root.setRight(rightContainer);

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
