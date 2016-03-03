package connectivity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.User;

public class AddUser {
	
	public AddUser() {
		
	}
	
	public boolean addUser(String name, String password, String accountType, String email) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();
		if (!checkDouble(name, em)) {
			User user = new User();
			user.setAccountType(accountType);
			user.setPassword(password);
			user.setUname(name);
			user.setEmail(email);
			
			em.getTransaction().begin();
			
			em.persist(user);
			
			em.getTransaction().commit();
			
			em.close();
			emf.close();
			
			return true;
		} 
		em.close();
		emf.close();
		
		return false;
	}
	
	public boolean checkDouble(String name, EntityManager em) {
		Query query = em.createNamedQuery("loginByName");
		query.setParameter("uname", name);
		List<User> userList = query.getResultList();
		for (User user : userList) {
			if (name.equals(user.getUname()))
				return true;
		}
		
		return false;
	}
}
