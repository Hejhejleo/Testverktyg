package gui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import GUITemplate.GUITemplate;
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

	public BorderPane showPane(User user, List<Test> allTests) {
		obsTestsList.clear();

		GUITemplate.setPicked(false);
		Calendar today = Calendar.getInstance();
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH) + 1; // 0-11 so 1 less
		int day = today.get(Calendar.DAY_OF_MONTH);
		int hour = today.get(Calendar.HOUR);
		int min = today.get(Calendar.MINUTE);

		for (Test t : allTests) {
			String testEnd = t.getTestEnd();
			int yearE = Integer.parseInt("20" + testEnd.substring(0, 2));
			int monthE = Integer.parseInt(testEnd.substring(3, 5));
			int dayE = Integer.parseInt(testEnd.substring(6, 8));
			int hourE = Integer.parseInt(testEnd.substring(9, 11));
			int minE = Integer.parseInt(testEnd.substring(12, 14));

			String testStart = t.getTestStart();
			int yearS = Integer.parseInt("20" + testStart.substring(0, 2));
			int monthS = Integer.parseInt(testStart.substring(3, 5));
			int dayS = Integer.parseInt(testStart.substring(6, 8));
			int hourS = Integer.parseInt(testStart.substring(9, 11));
			int minS = Integer.parseInt(testStart.substring(12, 14));

			start.setTime(new Date(0));
			start.set(Calendar.DAY_OF_MONTH, dayS);
			start.set(Calendar.MONTH, (monthS - 1)); // 0-11 so 1 less
			start.set(Calendar.YEAR, yearS);
			start.set(Calendar.HOUR, hourS);
			start.set(Calendar.MINUTE, minS);

			end.setTime(new Date(0));
			end.set(Calendar.DAY_OF_MONTH, dayE);
			end.set(Calendar.MONTH, (monthE - 1)); // 0-11 so 1 less
			end.set(Calendar.YEAR, yearE);
			end.set(Calendar.HOUR, hourE);
			end.set(Calendar.MINUTE, minE);

			long startMil = start.getTimeInMillis();
			long endMil = end.getTimeInMillis();
			long todayMil =today.getTimeInMillis();

			for (SchoolClass s : t.getClasses()) {
				for (User u : s.getStudents()) {
					if (u.getUserName() == user.getUserName()) {

						if(startMil<todayMil&& todayMil<=endMil){
											obsTestsList.add(t.getTestName());
											System.out.println(t.getTestName());
						}
					}
				}
			}
		}

		testsComboBox.setPromptText("Select test");
		testsComboBox.setItems(obsTestsList);
		root.setPadding(new Insets(30));
		root.setCenter(testsComboBox);

		testsComboBox.setOnAction(event -> {
			for (Test t : allTests) {
				if (t.getTestName().equals(testsComboBox.getValue())) {
					setTest(t);
					GUITemplate.setPicked(true);
				}
			}
		});

		return root;
	}

	public void setTest(Test test) {
		currentChosenTest = test;
	}

	public Test getTest() {
		return currentChosenTest;
	}
}
