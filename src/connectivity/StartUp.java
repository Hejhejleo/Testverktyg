package connectivity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import entity.SchoolClass;
import entity.Test;
import entity.User;

/** Startup-class
 *  Obtains all SchoolClasses, Tests and Users from the database
 *  
 * 
 * @author Mattias Larsson
 *
 */

public class StartUp {
	private EntityManager em;
	
	public StartUp() {
		em = Persistence.createEntityManagerFactory("Testverktyg").createEntityManager();
	}
	
	/** Fetch the classes from the database 
	 * 
	 * @return List<SchoolClass>
	 */
	public List<SchoolClass> getClasses() {
		List<SchoolClass> allClasses = em.createQuery("select sc from SchoolClass sc").getResultList();
		
		return allClasses;
	}
	
	/** Fetch all users from the database
	 * 
	 * @return List<User>
	 */
	public List<User> getUsers() {
		List<User> allUsers = em.createQuery("select u from User u").getResultList();
		
		return allUsers;
	}
	
	/** Fetch all tests from the database
	 * 
	 * @return List<Test>
	 */
	public List<Test> getTests() {
		List<Test> allTests = em.createQuery("select t from Test t").getResultList();
		
		return allTests;
	}
}
