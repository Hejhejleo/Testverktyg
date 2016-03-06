package test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entity.SchoolClass;
import entity.User;

public class addUser {
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		// Skapar 2 studenter
		User user1 = new User("Mattias", "Larsson", "mattias", "hejsan", "Student", "a@b.c","123");
		User user2 = new User("Robin", "Listerberg", "roblist", "passwod", "Student", "robin@mail.com", "123");
		
		// Skapar en admin
		User admin = new User("Ingemo", "Ilander", "ingila", "hemligtpassword", "Admin", "ingemo@newton.se", "344556");
		
		// Skapar en skolklass
		SchoolClass class1 = new SchoolClass("Java");
		// Lägger till elever i klasslistan
		class1.addStudent(user1);
		class1.addStudent(user2);
		
		// lagrar allt
		em.persist(user1);
		em.persist(user2);
		em.persist(class1);
		em.persist(admin);
		
		em.getTransaction().commit();
	}
}
