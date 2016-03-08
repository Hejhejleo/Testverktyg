package gui;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entity.SchoolClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/** Administrates users
 * 
 * @author Mattias Larsson
 *
 */
public class AdminUser {
	
	private ObservableList<String> userList = FXCollections.observableArrayList();
	private ObservableList<String> classList = FXCollections.observableArrayList();
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public AdminUser() {
		emf = Persistence.createEntityManagerFactory("Testverktyg");
		em = emf.createEntityManager();
	}
	
	public GridPane showPane() {
		GridPane adminUserPane = new GridPane();
		
		ComboBox<String> classCombo = new ComboBox<>();
		classCombo.setPromptText("Choose class");
		classCombo.setItems(classList);
		classList.add("Create new class");
		
		List<SchoolClass> tempClassList = em.createNamedQuery("listSchoolClasses").getResultList();
		tempClassList.forEach(sc -> {
			classList.add(sc.getClassName());
		});
		
		adminUserPane.add(classCombo, 0, 0);
		
		ListView<String> studentListView = new ListView<String>();
		studentListView.setItems(userList);
		
		classCombo.setOnAction(chooseClass -> {
			if (classCombo.getValue().equals("Create new class")) {
				showNewClass();
			} else {
				userList.clear();
				SchoolClass tempClass = (SchoolClass) em.createQuery("select c from SchoolClass c where c.className = '" + classCombo.getValue() + "'").getSingleResult();
				for (int i = 0; i<tempClass.getStudents().size(); i++) {
					userList.add(tempClass.getStudents().get(i).getfName() + "\t" + tempClass.getStudents().get(i).getlName());
				}
			}
		});
		
		adminUserPane.add(studentListView, 1, 0, 3, 3);
		
		Button addStudent = new Button("Add student");
		addStudent.setOnAction(addAction -> {
			showNewStudent();
		});
		
		
		return adminUserPane;
	}
	
	public void showNewStudent() {
		
	}
	
	public void showNewClass() {
		Dialog<String> newClassDialog = new Dialog<String>();
		newClassDialog.setTitle("Create new class");
		newClassDialog.setHeaderText("Enter a name for the new class");
		ButtonType okButtonType = new ButtonType("Done", ButtonData.OK_DONE);
		newClassDialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
		TextField className = new TextField();
		className.setPromptText("Classname");
		Node okButton = newClassDialog.getDialogPane().lookupButton(okButtonType);
		newClassDialog.getDialogPane().setContent(className);
		newClassDialog.setResultConverter(dialogButton -> {
			if (dialogButton == okButtonType) {
				return className.getText();
			}
			return null;
		});
		Optional<String> result = newClassDialog.showAndWait();
		result.ifPresent(name -> {
			if (classList.contains(name)) {
				Alert newClassError = new Alert(AlertType.ERROR);
				newClassError.setTitle("This class exists");
				newClassError.setHeaderText("This class already exists. Please choose another name");
				newClassError.showAndWait();
			} else {
				SchoolClass newClass = new SchoolClass();
				newClass.setClassName(name);
				em.getTransaction().begin();
				em.persist(newClass);
				em.getTransaction().commit();
				classList.add(newClass.getClassName());
			}
		});
		
	}
}
