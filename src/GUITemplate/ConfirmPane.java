package GUITemplate;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmPane {
	
	static boolean answer;

	public static boolean display (String title, String message) {
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL);
		//stage.getIcons().add(new Image("/Newton.png"));
		stage.setTitle(title);
		stage.setMinHeight(250);
		stage.setMinWidth(250);
		Label label = new Label();
		label.setText(message);
		
		
		Button exitBtn = new Button("Yes");
		Button cancelBtn = new Button("No");
		
		exitBtn.setOnAction(e -> {
			answer = true;
			stage.close();
		});
		cancelBtn.setOnAction(e -> {
			answer = false;
			stage.close();
		});
		
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, exitBtn, cancelBtn);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		stage.setScene(scene);
		stage.showAndWait();
		
		return answer;
		
	}
}

