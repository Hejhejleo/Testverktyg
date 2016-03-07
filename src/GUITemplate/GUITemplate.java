package GUITemplate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GUITemplate extends Application{

                
    	Button btn1, btn2, btn3,btn7, btn8;
    	Label lbl;

    	AnchorPane rectCenter;
    	boolean writeLine = false;
    	
    	double clickX1;
    	double clickY1;

    	
    	public static void main(String[] args) {
    		launch(args);
    	}
    	
    	@Override
    	public void start(Stage primaryStage) {
    		primaryStage.getIcons().add(new Image("/1up.png"));	
    		primaryStage.setTitle("SomeThingUndefined 1.0");
    		
    		//Låter dig utföra ex. en savefunktion innan programmet stängs
    		
    		primaryStage.setOnCloseRequest(e -> {
    			e.consume();
    			Platfom();
    		});	

  		
    		//Menyer topp
    		
    		Menu fileMenu = new Menu("_File");
    		
    		fileMenu.getItems().add(new MenuItem("Ny"));
    		fileMenu.getItems().add(new MenuItem("Öppna"));
    		fileMenu.getItems().add(new MenuItem("_Spara"));
    		fileMenu.getItems().add(new SeparatorMenuItem());
    		MenuItem avsluta = new MenuItem("Avsluta");
    		avsluta.setOnAction(e -> {
    			boolean result4 = ConfirmPane.display("Avsluta Programmet", "Vill du avsluta programmet?");
    			if (result4 == true) {
    				System.exit(0);
    			}
    		});
    		fileMenu.getItems().add(avsluta);
    		
    		Menu editMenu = new Menu("_Edit");	
    		
    		editMenu.getItems().add(new MenuItem("Kopiera"));
    		editMenu.getItems().add(new MenuItem("Klipp ut"));
    		editMenu.getItems().add(new MenuItem("Klistra in"));
    		  		
    		Menu hjaMenu = new Menu("_Hjälp");
    		
    		hjaMenu.getItems().add(new MenuItem("Hjälp"));
    		hjaMenu.getItems().add(new MenuItem("Om"));
    		
    		MenuBar menuBar = new MenuBar();
    		menuBar.getMenus().addAll(fileMenu, editMenu, hjaMenu);
    		
    		//Vertikal box till vänster med knappar och funktioner
    		
    		VBox leftMenu = new VBox(20);
    		leftMenu.setPadding(new Insets(20));
    		
    			btn1 = new Button();
    			btn1.setLayoutX(22.0);
    			btn1.setLayoutY(50.0);
    			//btn1.setText("Oval");
    			double b = 15;
    			btn1.setShape(new Circle(b));
    			btn1.setMinSize(5*b, 3*b);
    			btn1.setMaxSize(5*b, 3*b);
    			//btn1.setOnAction(this);
    			VBox.setMargin(btn1, new Insets(0,0,0,3));
    			
    			btn2 = new Button();
    			btn2.setLayoutX(20.0);
    			btn2.setLayoutY(110.0);
    			//btn2.setText("Rektangel");
    			double a = 10;
    			btn2.setShape(new Rectangle(a, a));
    			btn2.setMinSize(8*a, 3*a);
    			btn2.setMaxSize(8*a, 3*a);
    			//btn2.setOnAction(this);
    			
    					
    			Image img = new Image(this.getClass().getResourceAsStream("/Chart.png"));
    			btn7 = new Button("New", new ImageView(img));
    			btn7.setLayoutX(20.0);
    			btn7.setLayoutY(500.0);
    			btn7.setText("New User");
    			btn7.setOnAction(e -> {
    				boolean result = ConfirmPane.display("Skapa ny chart", "   Vill du skapa en ny chart? \n Tidigare arbete sparas inte!   ");
    				if (result == true) {
    					
    					lbl.setText("Du har valt att börja om på nytt");
    				}
    			});
    			
    			leftMenu.getChildren().addAll(btn1, btn2, btn7);
    			
    			//Som center-area en rektangel
    			
    			rectCenter = new AnchorPane();
    			
    			
    			//Horizontal box i nedre kant
    			
    			HBox bottomMenu = new HBox();
    			Image img1 = new Image(this.getClass().getResourceAsStream("/exit.png"));
    			btn8 = new Button("Avsluta", new ImageView(img1));
    			btn8.setLayoutX(20.0);
    			btn8.setLayoutY(550.0);
    			HBox.setMargin(btn8, new Insets(0,0,5,5));
    			btn8.setOnAction(e -> {
    				boolean result1 = ConfirmPane.display("Avsluta Programmet", "Vill du avsluta programmet?");
    				if (result1 == true) {
    					System.exit(0);
    				}
    			});
    			lbl = new Label("Status");
    			lbl.setPrefSize(630, 30);
    			lbl.setStyle("-fx-border-color: #7CAFC2;" +  
    					   "-fx-background-color: #7CAFC2;" + 
    					   "-fx-text-fill: #ffffff");
    			lbl.setAlignment(Pos.CENTER);
    			lbl.setFont(Font.font("Arial", 12));
    			HBox.setMargin(lbl, new Insets(2,0,0,63));
    			
    			bottomMenu.getChildren().addAll(btn8, lbl);
    			
    			//Scen och Pane inställningar
    			
    			BorderPane borderPane = new BorderPane();
    			borderPane.setTop(menuBar);
    			borderPane.setLeft(leftMenu);
    			borderPane.setCenter(rectCenter);
    			borderPane.setBottom(bottomMenu);
    			//borderPane.setPrefSize(800,600);
    			
    			Scene scene = new Scene(borderPane, 800, 600, Color.rgb(56, 56, 56));
    			primaryStage.setScene(scene);
    			primaryStage.show();
    			
    	}
    	private void Platfom() {
    		Boolean answer = ConfirmPane.display("Avsluta Programmet", "Vill du avsluta programmet?");
    		if(answer)
    				Platform.exit();
    	}
}	
    			