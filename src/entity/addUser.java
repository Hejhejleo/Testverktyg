package entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class addUser {
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		User user1 = new User("Mattias", "Larsson", "mattias", "hejsan", "Student", "a@b.c","123");
		SchoolClass class1 = new SchoolClass("Java");
		
		class1.addStudent(user1);
		em.persist(user1);
		em.persist(class1);
		em.getTransaction().commit();
	}
}
