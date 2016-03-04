package entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class addTest {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		Test test1 = new Test("Test i Java", "hejsan", "sdsdf");
		Question q1 = new Question(1, false, "Fråga 1", "Vad heter jag", test1);
		QuestionType qtype = new QuestionType("Fritext");
		qtype.addQuestion(q1);
		em.persist(qtype);
		em.persist(q1);
		em.persist(test1);
		
		em.getTransaction().commit();
		
		
		
	}
}
