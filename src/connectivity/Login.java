package connectivity;

import java.util.List;

import entity.User;

/** Checks wether the login is successfull or not 
 * 
 * @author Mattias Larsson
 *
 */
public class Login {
	private String accountType;
	private String name;
	
	public Login() {}
	
	/** Checks login credentials
	 * 
	 * @param name
	 * @param password
	 * @param allUsers
	 * @return User
	 */
	public User login(String name, String password, List<User> allUsers) {
		// Obtains all users from the database
		StartUp start = new StartUp();
		allUsers = start.getUsers();
		// Goes through the list of users and checks wether the passwords matches
		for (User u : allUsers) {
			if (u.getUserName().equals(name) && u.getPassword().equals(password)) {
				this.accountType = u.getAccountType();
				this.name = u.getUserName();
				// Returns the user
				return u;
			}
		}
		return null;
	}
	
	/** Gets the accounttype
	 * 
	 * @return String
	 */
	public String getAccountType() {
		return accountType;
	}
	
	/** Gets the username
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
}
