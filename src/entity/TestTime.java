package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/** Stores when a student starts and ends a test, 
 * the students total score on the test and the teatcher's final
 * comment of the whole test
 * 
 * @author Mattias Larsson
 *
 */

@Entity
public class TestTime {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int testTimeId;
	@OneToOne
	private User user;
	@OneToOne
	private Test test;
	private String testStart;
	private String testEnd;
	private double totalScore;
	private String finalComment;
	
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
	 * @param totalScore - a double with the student's total score
	 * @param finalComment - a String with the final comment 
	 */
	public TestTime(User user, Test test, String testStart, String testEnd, double totalScore, String finalComment) {
		this.user = user;
		this.test = test;
		this.testStart = testStart;
		this.testEnd = testEnd;
		this.totalScore = totalScore;
		this.finalComment = finalComment;
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
	
	/** Returns the student's total score on the test
	 * 
	 * @return a double with the student's total score
	 */
	public double getTotalScore() {
		return totalScore;
	}

	/** Sets the student's total score on the test
	 * 
	 * @param testEnd - a String with the student's ending time
	 */
	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	/** Gets the final comment of the test from the teacher
	 * 
	 * @return a String with the final comment
	 */
	public String getFinalComment() {
		return finalComment;
	}

	/** Sets the final comment of the test from the teacher
	 * 
	 * @param finalComment - a String with the final comment 
	 */
	public void setFinalComment(String finalComment) {
		this.finalComment = finalComment;
	}
	
	
	
}
