package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/** Creates a new test 
 * 
 * @author Mattias Larsson
 *
 */

@Entity
public class Test {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int testId;
	private String testName;
	private String testStart;
	private String testEnd;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Question> questionList;
	@OneToMany(cascade = CascadeType.ALL)
	private List<SchoolClass> classList;
	
	/** Constructor
	 * 
	 */
	public Test() {}
	
	/** Constructor
	 * 
	 * @param testName - a String with the name of this test
	 * @param testStart - a String containing the date and time when the test starts
	 * @param testEnd - a String containing the date and time when the test ends
	 */
	public Test(String testName, String testStart, String testEnd) {
		this.testName = testName;
		this.testStart = testStart;
		this.testEnd = testEnd;
		this.questionList = new ArrayList<>();
		this.classList = new ArrayList<>();
	}
	
	/** Adds a question to this test
	 * 
	 * @param question - an instance of Question
	 */
	public void addQuestion(Question question) {
		questionList.add(question);
	}
	
	/** Returns the questions in this test
	 * 
	 * @return a List<Question> with the questions in this test
	 */
	public List<Question> getQuestions() {
		return questionList;
	}
	
	/** Returns the name of this test
	 * 
	 * @return a String with the name of this test
	 */
	public String getTestName() {
		return testName;
	}

	/** Sets the name of this test
	 * 
	 * @param testName - a String with the name of this test
	 */
	public void setTestName(String testName) {
		this.testName = testName;
	}

	/** Returns the starting time of this test
	 * 
	 * @return a String with the starting time of this test
	 */
	public String getTestStart() {
		return testStart;
	}

	/** Sets the starting time of this test
	 * 
	 * @param testStart - a String with the starting time of this test
	 */
	public void setTestStart(String testStart) {
		this.testStart = testStart;
	}

	/** Returns the ending time of this test
	 * 
	 * @return a String with the ending time of this test
	 */
	public String getTestEnd() {
		return testEnd;
	}

	/** Sets the ending time of this test
	 * 
	 * @param testEnd - a String with the ending time of this test
	 */
	public void setTestEnd(String testEnd) {
		this.testEnd = testEnd;
	}

	/** Adds a schoolclass to this test
	 * 
	 * @param schoolClass - an instance of SchoolClass
	 */
	public void addClass(SchoolClass schoolClass) {
		this.classList.add(schoolClass);
	}
	
	/** Returns a list of schoolclasses that can take this test
	 * 
	 * @return a List<SchoolClass> of schoolclasses that can take this test
	 */
	public List<SchoolClass> getClasses() {
		return this.classList;
	}
}
