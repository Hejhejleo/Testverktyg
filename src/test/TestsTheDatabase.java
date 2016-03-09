package test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entity.Answers;
import entity.Question;
import entity.QuestionType;
import entity.SchoolClass;
import entity.StudentAnswer;
import entity.Test;
import entity.TestTime;
import entity.User;

public class TestsTheDatabase {
	
	public static void main(String[] args) {
		
		// Add a user
		User user = new User();
		user.setAccountType("Student");
		user.setCity("Mölndal");
		user.setEmail("a@b.c");
		user.setfName("Mattias");
		user.setlName("Larsson");
		user.setPassword("hemligt");
		user.setPhone("0735908926");
		user.setSSN("12345678-9012"); 
		user.setStreet("Glasbergsgatan 188");
		user.setUserName("kultomten");
		user.setZip(43134);
		
		// Add a schoolclass
		SchoolClass class1 = new SchoolClass("JAVAG1");
		// Add a student to the class
		class1.addStudent(user);
		
		// Add a test
		Test test = new Test("Test i JPA", "20160307-09:00", "20160307-12:00");
		// Set the class to take this test
		test.addClass(class1);
		
		// Add a questiontype
		QuestionType qtype1 = new QuestionType("Radiobuttons");
		
		// Add a question
		Question q1 = new Question(1, false, "Fråga 1", "Vad betyder JPA?", test, qtype1);
		// Add this question to the QuestionType
		qtype1.addQuestion(q1);
		// Add this question the the test
		test.addQuestion(q1);
		
		// Sets the correct answer
		Answers answers = new Answers(q1);
		answers.addAnswer("Java Persistence API");
		answers.addAnswer("Java Parental Accident");
		answers.addAnswer("JUnit Practice Apple");
		answers.setCorrectAnswer(0);
		answers.setQuestion(q1);
		
		// The student starts the test
		TestTime testTime = new TestTime();
		testTime.setUser(user);
		testTime.setTest(test);
		testTime.setTestStart("20160307-09:01");
		
		// The student answers
		StudentAnswer studentAnswer = new StudentAnswer();
		studentAnswer.setUser(user);
		studentAnswer.setQuestion(q1);
		studentAnswer.setAnswer("JUnit Practice Apple");
		
		// The student ends the test
		testTime.setTestEnd("20160307-09:04");
		
		// Store everything in the database
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		em.persist(user);
		em.persist(class1);
		em.persist(test);
		em.persist(qtype1);
		em.persist(q1);
		em.persist(answers);
		em.persist(testTime);
		em.persist(studentAnswer);
		
		em.getTransaction().commit();
		
		
		// List all students in the class JAVAG1
		List<SchoolClass> classList = new ArrayList<>();
		classList = em.createQuery("select c from SchoolClass c where c.className = 'JAVAG1'").getResultList();
		SchoolClass firstClass = classList.get(0);
		firstClass.getStudents().forEach(student -> {
			System.out.println(student.getfName());
		});
		
		// List all tests that are connected to the class JAVAG1
		List<Test> testList = new ArrayList<>();
		testList = em.createQuery("select t from Test t").getResultList();
		Test test1 = testList.get(0);
		test1.getClasses().forEach(schoolclass -> {
			if (schoolclass.getClassName().equals("JAVAG1")) {
				System.out.println(test1.getTestName());
			}
		});
		
		// List the student's testtime
		List<TestTime> timeList = new ArrayList<>();
		timeList = em.createQuery("select t from TestTime t").getResultList();
		timeList.forEach(time -> {
			System.out.println("Student: " + time.getUser().getfName() + "\nTest: " + time.getTest().getTestName() + "\tStart: " + time.getTestStart() + "\tEnd: " + time.getTestEnd());
		});
		
		// List all the questions in the test, the correct answer
		// and the student's answer
		List<Test> testList1 = new ArrayList<>();
		testList1 = em.createQuery("select t from Test t").getResultList();
		Test test2 = testList1.get(0);
		test2.getQuestions().forEach(q -> {
			System.out.println(q.getQuestionText());
			Answers a = (Answers)em.createQuery("select a from Answers a").getSingleResult();
			if (a.getQuestion()==q) {
				System.out.println("Correct answer is: " + a.getCorrectAnswer());
			}
			StudentAnswer sa = (StudentAnswer)em.createQuery("select s from StudentAnswer s").getSingleResult();
			if (sa.getQuestion()==q) {
				System.out.println("The student answered: " + sa.getAnswer());
			}
		});
		
		
		
		
		
		
		
		
	}
}
