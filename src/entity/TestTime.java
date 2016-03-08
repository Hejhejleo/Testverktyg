package entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/** Stores when a student starts and ends a test
 * 
 * @author Mattias Larsson
 *
 */

@Entity
public class TestTime {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int testTimeId;
	@OneToOne(cascade = CascadeType.ALL)
	private User user;
	@OneToOne(cascade = CascadeType.ALL)
	private Test test;
	private String testStart;
	private String testEnd;
	
	/** Constructor
	 * 
	 */
	public TestTime(){}

	/** Constructor
	 * 
	 * @param user - an instance of User
	 * @param test - an instance of Test
	 * @param testStart - a String with the student's starting time
	 * @param testEnd - a String with the student's ending time
	 */
	public TestTime(User user, Test test, String testStart, String testEnd) {
		this.user = user;
		this.test = test;
		this.testStart = testStart;
		this.testEnd = testEnd;
	}

	/** Sets the user taking the test
	 * 
	 * @param user - an Instance of User
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/** Returns the user who took the test
	 * 
	 * @return an instance of User
	 */
	public User getUser() {
		return user;
	}
	/** Returns the test
	 * 
	 * @return an instance of Test
	 */
	public Test getTest() {
		return test;
	}

	/** Sets the test
	 * 
	 * @param test - an instance of Test
	 */
	public void setTest(Test test) {
		this.test = test;
	}

	/** Returns the student's starting time
	 * 
	 * @return a String with the student's starting time of this test
	 */
	public String getTestStart() {
		return testStart;
	}

	/** Sets the student's starting time
	 * 
	 * @param testStart - a String with the student's starting time
	 */
	public void setTestStart(String testStart) {
		this.testStart = testStart;
	}

	/** Returns the student's ending time
	 * 
	 * @return a String with the student's ending time
	 */
	public String getTestEnd() {
		return testEnd;
	}

	/** Sets the student's ending time
	 * 
	 * @param testEnd - a String with the student's ending time
	 */
	public void setTestEnd(String testEnd) {
		this.testEnd = testEnd;
	}
}
