package test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entity.Answers;
import entity.Question;
import entity.QuestionType;
import entity.SchoolClass;
import entity.Test;

public class addTest {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		// Skapar ett nytt test och s�tter vilken klass provet g�ller f�r
		Test test1 = new Test("Test i Java", "20160222-12:00", "20160222-13:00");
		test1.addClass(new SchoolClass("dotNet"));
		
		// Skapar 2 nya fr�gekategorier
		QuestionType qType1 = new QuestionType("Fritext");
		QuestionType qType2 = new QuestionType("Radiobutton");
		
		// Skapar ny fr�ga och nytt svar
		Question q1 = new Question(1, false, "Fr�ga 1", "Vad heter jag", test1, qType1);
		// Konstruktorn tar ett fr�geobjekt
		Answers answers1 = new Answers(q1);
		answers1.addAnswer("Mattias");
		// addAnswer l�gger till ett svar i en lista med Str�ngar 
		// och setCorrectAnswer anger vilket index i listan som�r korrekt
		answers1.setCorrectAnswer(0);
		
		Question q2 = new Question(1, false, "Fr�ga 2", "Vad heter Robin?", test1, qType2);
		Answers answers2 = new Answers(q2);
		answers2.addAnswer("Robin");
		answers2.addAnswer("Kenneth");
		answers2.addAnswer("Lollo");
		answers2.setCorrectAnswer(0);
		
		// Lagrar allt
		em.persist(answers1);
		em.persist(answers2);
		em.persist(test1);
		em.persist(qType1);
		em.persist(qType2);
		em.persist(q1);
		em.persist(q2);
		
		em.getTransaction().commit();
		
		
		
	}
}
