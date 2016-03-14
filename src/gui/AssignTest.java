package gui;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import entity.SchoolClass;
import entity.Test;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AssignTest {
	ScrollPane scrollPane;
	VBox container;
	Scene popUpScene;
	Stage popUpStage;
	List<CheckBox> checkBoxes;
	List<SchoolClass> allClasses;
	Button saveChanges;
	List<SchoolClass> assignedClasses;
	CheckBox tempBox;

	public void doAssign(Test currentTest, EntityManager em) {
		allClasses = new ArrayList<>();
		allClasses = (List<SchoolClass>) em.createQuery("Select sc from SchoolClass sc").getResultList();
		assignedClasses = new ArrayList<>();
		assignedClasses = currentTest.getClasses();
		checkBoxes = new ArrayList<>();
		scrollPane = new ScrollPane();
		scrollPane.setPrefSize(300, 500);
		container = new VBox();
		popUpScene = new Scene(scrollPane, 300, 500);
		popUpStage = new Stage();
		saveChanges = new Button("Save Changes");

		for (SchoolClass sc : allClasses) {
			CheckBox checkBox = new CheckBox();
			checkBox.setText(sc.getClassName());
			checkBoxes.add(checkBox);
			tempBox=checkBox;
			for (SchoolClass asc : assignedClasses) {
				if (sc.getClassName().equals(asc.getClassName())) {
					tempBox.setSelected(true);
				} 
			}

		}

		container.getChildren().addAll(checkBoxes);
		container.getChildren().add(saveChanges);
		scrollPane.setContent(container);
		popUpStage.setScene(popUpScene);

		popUpStage.show();

		
		saveChanges.setOnAction(event ->{

			assignedClasses.clear();
			for(int i = 0; i<checkBoxes.size();i++){
				if(checkBoxes.get(i).isSelected()){
					currentTest.addClass(allClasses.get(i));
				}
			}
			em.getTransaction().begin();
			em.persist(currentTest);
			em.getTransaction().commit();
			popUpStage.close();
		});
	}
	

}
