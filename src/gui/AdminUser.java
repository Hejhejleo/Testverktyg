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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Administrates users
 * 
 * @author Mattias Larsson
 *
 */
public class AdminUser {
	AddNewUser addNewUser = new AddNewUser();
	private String whiteBGAndGreyBorder = "-fx-background-color: #FFFFFF; " + "-fx-border-color: #D3D3D3";
	private String frameStyle = "-fx-border-color: #FFA500; " + "-fx-border-width: 2px; "
			+ "-fx-background-color: #FFFFFF";
	private ObservableList<String> userList = FXCollections.observableArrayList();
	private ObservableList<String> classList = FXCollections.observableArrayList();
	private EntityManagerFactory emf;
	private EntityManager em;
	
	
	public AdminUser() {
		
	}
	
	public GridPane showPane(BorderPane root) {
		emf = Persistence.createEntityManagerFactory("Testverktyg");
		em = emf.createEntityManager();
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
		studentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		studentListView.setOnMouseClicked(e -> {
			if (e.getClickCount()>1 && studentListView.getSelectionModel().getSelectedIndex()>-1) {
				SchoolClass c = (SchoolClass) em.createQuery("select c from SchoolClass c where c.className = '" + classCombo.getSelectionModel().getSelectedItem()+"'").getSingleResult();
				String name = studentListView.getSelectionModel().getSelectedItem();
				String fName = name.substring(0, name.indexOf("\t"));
				String lName = name.substring(name.indexOf("\t")+1, name.length());
				for (User user : c.getStudents()) {
					if (user.getfName().equals(fName) && user.getlName().equals(lName)) {
						root.setCenter(new ChangeUserInfo(user).showPane());
						break;
					}
				}
			}
		});
		studentListView.setMaxHeight(300);
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
		
		adminUserPane.add(studentListView, 1, 0, 3, 2);
		
		//TODO
		Button addStudent = new Button("Add student");
		addStudent.prefWidthProperty().bind(classCombo.widthProperty());
		addStudent.setOnAction(addAction -> {
		showNewStudent(classCombo.getValue(), userList);
			
			
			
			
		});
		
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
			classList.remove(showRemoveClass(classCombo.getSelectionModel().getSelectedItem()));
		});
		
		VBox buttons = new VBox();
		buttons.getChildren().addAll(classCombo, addStudent, removeStudent, 
				moveStudent, removeClass);
		adminUserPane.add(buttons, 0, 1);
		
		return adminUserPane;
	}
	
	public String showRemoveClass(String className) {
		SchoolClass sc = (SchoolClass) em.createQuery("select c from SchoolClass c where c.className = '" + className + "'").getSingleResult();
		em.getTransaction().begin();
		em.remove(sc);
		em.getTransaction().commit();
		
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
	
	public void showNewStudent(String className, ObservableList<String> userList) {
		User newUser = new User();
		Stage newStudentStage = new Stage();
		GridPane root = new GridPane();
		Scene newStudentScene = new Scene(root, 400, 400);
		newStudentStage.setScene(newStudentScene);
		newStudentStage.show();
		
		TextField txtFname = new TextField();
		txtFname.setPromptText("First name");
		root.add(txtFname, 0, 0);
		
		TextField txtLname = new TextField();
		txtLname.setPromptText("Last name");
		root.add(txtLname, 1, 0);
		
		TextField txtEmail = new TextField();
		txtEmail.setPromptText("E-mail");
		root.add(txtEmail, 0, 1);
		
		TextField txtPhone = new TextField();
		txtPhone.setPromptText("Phonenumber");
		root.add(txtPhone, 1, 1);
		
		TextField txtAddress = new TextField();
		txtAddress.setPromptText("Street Address");
		root.add(txtAddress, 0, 2);
		
		TextField txtZip = new TextField();
		txtZip.setPromptText("Zip");
		root.add(txtZip, 1, 2);
		
		TextField txtCity = new TextField();
		txtCity.setPromptText("City");
		root.add(txtCity, 0, 3);
		
		TextField txtUserName = new TextField();
		txtUserName.setPromptText("Username");
		root.add(txtUserName, 0, 4);
		
		PasswordField txtPassword = new PasswordField();
		txtPassword.setPromptText("Password");
		root.add(txtPassword, 1, 4);
		
		Button okButton = new Button("OK");
		Button cancelButton = new Button("Cancel");
		root.add(okButton, 0, 5);
		root.add(cancelButton, 1, 5);
		
		cancelButton.setOnAction(c -> {
			newStudentStage.close();
		});
		
		okButton.setOnAction(ok -> {
			
			if (!className.equals("Create new class")) {
				if(!checkDoubles(txtUserName.getText())) {
					newUser.setAccountType("Student");
					newUser.setCity(txtCity.getText());
					newUser.setEmail(txtEmail.getText());
					newUser.setfName(txtFname.getText());
					newUser.setlName(txtLname.getText());
					newUser.setPassword(txtPassword.getText());
					newUser.setPhone(txtPhone.getText());
					newUser.setStreet(txtAddress.getText());
					newUser.setUserName(txtUserName.getText());
					newUser.setZip(Integer.parseInt(txtZip.getText()));
					//newUser.setSSN("19960805-7874");
					//TODO
					
					SchoolClass sc = (SchoolClass)em.createQuery("select c from SchoolClass c where c.className = '" + className + "'").getSingleResult();
					sc.addStudent(newUser);
					
					em.getTransaction().begin();
					em.persist(newUser);
					em.persist(sc);
					em.getTransaction().commit();
					
					newStudentStage.close();
				} else {
					Alert doubleUser = new Alert(AlertType.ERROR);
					doubleUser.setTitle("Username already exists");
					doubleUser.setHeaderText("The username already exists");
					doubleUser.showAndWait();
				}
			}
			userList.add(newUser.getfName()+"\t"+newUser.getlName());
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
	public StackPane getAddUserPane(){
		
		return addNewUser.showPane();
		
	}
}
