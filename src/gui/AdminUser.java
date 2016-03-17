package gui;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entity.SchoolClass;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
		
	}
	
	public GridPane showPane(BorderPane root, List<SchoolClass> allClasses, List<User> allUsers) {
		emf = Persistence.createEntityManagerFactory("Testverktyg");
		em = emf.createEntityManager();
		GridPane adminUserPane = new GridPane();
		
		ComboBox<String> classCombo = new ComboBox<>();
		classCombo.setPromptText("Choose class");
		classCombo.setItems(classList);
		classList.add("Create new class");
		
		
		List<SchoolClass> tempClassList = allClasses;
		tempClassList.forEach(sc -> {
			classList.add(sc.getClassName());
		});
		
		adminUserPane.add(classCombo, 0, 0);
		
		ListView<String> studentListView = new ListView<String>();
		studentListView.setItems(userList);
		studentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		studentListView.setMaxHeight(300);
		classCombo.setOnAction(chooseClass -> {
			if (classCombo.getValue().equals("Create new class")) {
				showNewClass(allClasses);
			} else {
				userList.clear();
				SchoolClass tempClass = (SchoolClass) em.createQuery("select c from SchoolClass c where c.className = '" + classCombo.getValue() + "'").getSingleResult();
				for (int i = 0; i<tempClass.getStudents().size(); i++) {
					userList.add(tempClass.getStudents().get(i).getfName() + "\t" + tempClass.getStudents().get(i).getlName());
				}
			}
		});
		
		adminUserPane.add(studentListView, 1, 0, 3, 2);
		
		Button removeStudent = new Button("Remove student");
		removeStudent.prefWidthProperty().bind(classCombo.widthProperty());
		removeStudent.setOnAction(removeAction -> {
			userList.remove(showRemoveStudent(studentListView.getSelectionModel().getSelectedItem(), classCombo.getValue()));
		});
		
		Button moveStudent = new Button("Move student");
		moveStudent.prefWidthProperty().bind(classCombo.widthProperty());
		moveStudent.setOnAction(moveAction -> {
			showMoveStudent(studentListView.getSelectionModel().getSelectedItem(), userList, classCombo);
		});
		
		Button removeClass = new Button("Remove class");
		removeClass.prefWidthProperty().bind(classCombo.widthProperty());
		removeClass.setOnAction(removeClassAction -> {
			classList.remove(showRemoveClass(classCombo.getSelectionModel().getSelectedItem(), allClasses));
		});
		
		VBox buttons = new VBox();
		buttons.getChildren().addAll(classCombo, removeStudent, 
				moveStudent, removeClass);
		adminUserPane.add(buttons, 0, 1);
		
		return adminUserPane;
	}
	
	public String showRemoveClass(String className, List<SchoolClass> allClasses) {
		SchoolClass sc = (SchoolClass) em.createQuery("select c from SchoolClass c where c.className = '" + className + "'").getSingleResult();
		em.getTransaction().begin();
		em.remove(sc);
		em.getTransaction().commit();
		allClasses.remove(sc);
		return className;
	}
	
	public void showMoveStudent(String studentName, ObservableList<String> userList, ComboBox<String> classCombo) {
		ComboBox<String> newClassCombo = new ComboBox<String>();
		Dialog<String> moveStudentDialog = new Dialog<String>();
		moveStudentDialog.setTitle("Move student");
		moveStudentDialog.setHeaderText("Select class to move " + studentName + " to");
		ButtonType okButtonType = new ButtonType("Done", ButtonData.OK_DONE);
		moveStudentDialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
		newClassCombo.setItems(classList);
		Node okButton = moveStudentDialog.getDialogPane().lookupButton(okButtonType);
		moveStudentDialog.getDialogPane().setContent(newClassCombo);
		moveStudentDialog.setResultConverter(dialogButton -> {
			if (dialogButton == okButtonType) {
				return (String) newClassCombo.getSelectionModel().getSelectedItem();
			}
			return null;
		});
		Optional<String> result = moveStudentDialog.showAndWait();
		result.ifPresent(className -> {
			int indexToRemove = 0;
			User userToMove = null;
			em.getTransaction().begin();
			SchoolClass oldSc = (SchoolClass) em.createQuery("select c from SchoolClass c where c.className = '" + classCombo.getSelectionModel().getSelectedItem() +"'").getSingleResult();
			String fName = studentName.substring(0, studentName.indexOf("\t"));
			String lName = studentName.substring(studentName.indexOf("\t")+1, studentName.length());
			for (User user : oldSc.getStudents()) {
				if (user.getfName().equals(fName) && user.getlName().equals(lName)) {
					userToMove = user;
				}
			}
			oldSc.getStudents().remove(userToMove);
			SchoolClass newSc = (SchoolClass) em.createQuery("select c from SchoolClass c where c.className = '" + newClassCombo.getSelectionModel().getSelectedItem() + "'").getSingleResult();
			newSc.addStudent(userToMove);
			em.persist(newSc);
			em.persist(oldSc);
			em.getTransaction().commit();
			classCombo.getSelectionModel().select(newSc.getClassName());
			classCombo.getSelectionModel().select(oldSc.getClassName());
		});
	}
	
		
	
	
	public String showRemoveStudent(String student, String className) {
		String fName = student.substring(0, student.indexOf("\t"));
		String lName = student.substring(student.indexOf("\t")+1, student.length());
		System.out.println("Fname: " + fName);
		System.out.println("Lname: " + lName);
		
		em.getTransaction().begin();
		SchoolClass sc = (SchoolClass) em.createQuery("select c from SchoolClass c where c.className = '" + className + "'").getSingleResult();
		int indexToRemove = 0;
		for (User user : sc.getStudents()) {
			if (user.getfName().equals(fName) && user.getlName().equals(lName)) {
				indexToRemove = sc.getStudents().indexOf(user);
			}
		}
		
		sc.getStudents().remove(indexToRemove);
		em.persist(sc);
		em.getTransaction().commit();
			
		return student;
	}
	
	public boolean checkDoubles(String userName) {
		List<User> tempUserList = em.createQuery("select u from User u").getResultList();
		for (int i = 0; i < tempUserList.size(); i++) {
			if (tempUserList.get(i).getUserName().equals(userName)) {return true;}
		}
		return false;
	}
	
	
	public void showNewClass(List<SchoolClass> allClasses) {
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
				allClasses.add(newClass);
				classList.add(newClass.getClassName());
			}
		});
	}
}
