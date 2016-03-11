package connectivity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.User;

public class AddUser2 {
	
	public AddUser2() {
		
	}
	
	public boolean addUser (String fName, String lName, String userName, String password, 
			String accountType, 
				String ssn, String email, String phone, String street, int zip, String city) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();
		if (!checkDouble(userName, em)) {
			User user = new User();
			user.setfName(fName);
			user.setlName(lName);
			user.setUserName(userName);
			user.setPassword(password);
			user.setAccountType(accountType);
			user.setSSN(ssn);
			user.setEmail(email);
			user.setPhone(phone);
			user.setStreet(street);
			user.setZip(zip);
			user.setCity(city);
			
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
	
	public boolean checkDouble(String userName, EntityManager em) {
		Query query = em.createNamedQuery("loginByName");
		query.setParameter("uname", userName);
		List<User> userList = query.getResultList();
		for (User user : userList) {
			if (userName.equals(user.getUserName()))
				return true;
		}
		
		return false;
	}
}
