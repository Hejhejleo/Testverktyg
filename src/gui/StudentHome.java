package gui;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entity.SchoolClass;
import entity.Test;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;

public class StudentHome {
	private EntityManagerFactory emf;
	private EntityManager em;
	BorderPane root = new BorderPane();
	Test currentChosenTest;
	List<Test> tests = new ArrayList<>();
	List<Test> testsForStudent = new ArrayList<>();
	List<SchoolClass> classes = new ArrayList<>();
	ComboBox<String> testsComboBox = new ComboBox<String>();
	private ObservableList<String> obsTestsList = FXCollections.observableArrayList();

	
	public BorderPane showPane(User user){
		emf = Persistence.createEntityManagerFactory("Testverktyg");
		em = emf.createEntityManager();
		tests = (List<Test>) em.createQuery("select t from Test t").getResultList();
		classes = (List<SchoolClass>) em.createQuery("select sc from SchoolClass sc").getResultList();
		
		for(Test t:tests){
			for(SchoolClass s:t.getClasses()){
				for(User u : s.getStudents()){
					if(u.getUserName()==user.getUserName()){
						obsTestsList.add(t.getTestName());
						System.out.println(t.getTestName());
					}
				}
			}
		}

		
		testsComboBox.setPromptText("Select test");
		testsComboBox.setItems(obsTestsList);
		root.setPadding(new Insets(30));
		root.setCenter(testsComboBox);
		
		testsComboBox.setOnAction(event ->{
			setTest(tests.get(testsComboBox.getSelectionModel().getSelectedIndex()));
		});
		
		
		return root;
	}
	
	
	
	public void setTest(Test test){
		currentChosenTest = test;
	}
	
	public Test getTest(){
		return currentChosenTest;
	}
}
