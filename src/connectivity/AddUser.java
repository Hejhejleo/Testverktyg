package connectivity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entity.User;

public class AddUser {
	
	public AddUser() {
		
	}
	
	public void addUser(String name, String password, String accountType, String email) {
		User user = new User();
		user.setAccountType(accountType);
		user.setPassword(password);
		user.setUname(name);
		user.setEmail(email);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		em.persist(user);
		
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
