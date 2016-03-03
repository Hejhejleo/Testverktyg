package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entity.User;

public class DAO {
	
	public DAO() {
		
	}
	
	public void addUser(String name, String password, String accountType) {
		User user = new User();
		user.setAccountType(accountType);
		user.setPassword(password);
		user.setUname(name);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		em.persist(user);
		
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
