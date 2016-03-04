package entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(query = "Select user from User user where user.userName = :uname", name="loginByName")

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int uid;
	private String userName;
	private String fName;
	private String lName;
	private String password;
	private String accountType;
	private String email;
	private String phone;
	
	
	public User(String fName, String lName, String userName, String password, String accountType, String email, String phone) {
		this.fName = fName;
		this.lName = lName;
		this.password = password;
		this.accountType = accountType;
		this.email = email;
		this.phone = phone;
		this.userName = userName;
		
	}
	
	public User() {}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public int getUid() {
		return uid;
	}
	

	

	

}
