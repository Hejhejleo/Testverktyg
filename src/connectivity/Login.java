package connectivity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.User;

public class Login {
	private String accountType;
	private String name;
	
	public Login() {}
	
	public User login(String name, String password) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createNamedQuery("loginByName");
		query.setParameter("uname", name);
		List<User> result = query.getResultList();
		
		if (result.size()>1 || result.size()<1) {
			return null;
		} else {
			this.accountType = result.get(0).getAccountType();
			this.name = result.get(0).getUserName();
			if (name.equals(result.get(0).getUserName()) && password.equals(result.get(0).getPassword())) {
				return result.get(0);
			}
		}
		return null;
	}
	
	public String getAccountType() {
		return accountType;
	}
	
	public String getName() {
		return name;
	}
}
