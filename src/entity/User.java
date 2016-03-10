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
@NamedQuery(query = "Select u from User u where u.userName = :uname", name="loginByName")

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
//	private String ssn; // TODO
	private String email;
	private String phone;
	private String street;
	private int zip;
	private String city;
	
	/** Constructor
	 * 
	 * @param fName - a String with the user's first name
	 * @param lName - a String with the user's last name
	 * @param userName - a String with the user's login name
	 * @param password - a String with the user's password
	 * @param accountType - a String with the user's accounttype
	 * @param email - a String with the user's e-mail
	 * @param phone - a String with the user's phone number
	 * @param SSN - a String with the user's SSN
	 */
	public User(String fName, String lName, String userName, String password, String accountType, String email, String phone, String street, int zip, String city/*, String ssn*/) {
		this.fName = fName;
		this.lName = lName;
		this.password = password;
		this.accountType = accountType;
		//this.ssn = ssn; // TODO 
		this.email = email;
		this.phone = phone;
		this.userName = userName;
		this.street = street;
		this.zip = zip;
		this.city = city;
		
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
	
	/* Returns the user's social security number (personnummer)
	 * 
	 * @return a string with the user's social security number
	 */
//	public String getSSN() { // TODO
//		return ssn;
//	}

	/** Sets the user's social security number (personnummer) 
	 * 19960805-7874
	 * 
	 * @param ssn - a String with the user's social security number
	 */
//	public void setSSN(String ssn) { //TODO
//		this.ssn = ssn;
//	}

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

	/** Sets the user's streetaddress
	 * 
	 * @param street - String
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	/** Returns the user's streetaddress
	 * 
	 * @return String
	 */
	public String getAddress() {
		return this.street;
	}
	
	/** Sets the users zip
	 * 
	 * @param int
	 */
	public void setZip(int zip) {
		this.zip = zip;
	}
	
	/** Returns the user's zip
	 * 
	 * @return int
	 */
	public int getZip() {
		return this.zip;
	}
	
	/** Sets the user's city
	 * 
	 * @param city - String
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/** Returns the user's city
	 * 
	 * @return String
	 */
	public String getCity() {
		return this.city;
	}
}