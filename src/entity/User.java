package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


/** Creates a new user
 * 
 * @author Mattias Larsson
 *
 */


@Entity
@NamedQuery(query = "Select user from User user where user.userName = :uname", name="loginByName")

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int uid;
	@Column(unique = true)
	private String userName;
	private String fName;
	private String lName;
	private String password;
	private String accountType;
	private String email;
	private String phone;
	
	/** Constructor
	 * 
	 * @param fName - a String with the user's first name
	 * @param lName - a String with the user's last name
	 * @param userName - a String with the user's login name
	 * @param password - a String with the user's password
	 * @param accountType - a String with the user's accounttype
	 * @param email - a String with the user's e-mail
	 * @param phone - a String with the user's phone number
	 */
	public User(String fName, String lName, String userName, String password, String accountType, String email, String phone) {
		this.fName = fName;
		this.lName = lName;
		this.password = password;
		this.accountType = accountType;
		this.email = email;
		this.phone = phone;
		this.userName = userName;
		
	}
	
	/** Constructor
	 * 
	 */
	public User() {}

	/** Returns the user's first name
	 * 
	 * @return a String with the user's first name
	 */
	public String getfName() {
		return fName;
	}

	/** Sets the user's first name
	 * 
	 * @param fName - a String with the user's first name
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}

	/** Returns the user's last name
	 * 
	 * @return a String with the user's last name
	 */
	public String getlName() {
		return lName;
	}

	/** Sets the user's last name
	 * 
	 * @param lName - a String with the user's las name
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}

	/** Returns the user's password
	 * 
	 * @return a String with the user's password
	 */
	public String getPassword() {
		return password;
	}

	/** Sets the user's password
	 * 
	 * @param password - a String with the user's password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/** Returns the user's login name
	 * 
	 * @return a String with hte user's login name
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/** Sets the user's login name
	 * 
	 * @param userName - a String with the user's login name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/** Returns the user's accounttype
	 * 
	 * @return a String with the user's accounttype
	 */
	public String getAccountType() {
		return accountType;
	}

	/** Sets the user's accounttype
	 * 
	 * @param accountType - a String with the user's accounttype
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/** Returns the user's e-mail
	 * 
	 * @return a String with the user's e-mail
	 */
	public String getEmail() {
		return email;
	}

	/** Sets the user's e-mail
	 * 
	 * @param email - a String with the user's e-mail
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/** Returns the user's phone number
	 * 
	 * @return a String with the user's phone number
	 */
	public String getPhone() {
		return phone;
	}

	/** Sets the user's phone number
	 * 
	 * @param phone - a String with the user's phone number
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
