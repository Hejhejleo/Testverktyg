package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
	
	public TestTime(){}

	public TestTime(User user, Test test, String testStart, String testEnd) {
		this.user = user;
		this.test = test;
		this.testStart = testStart;
		this.testEnd = testEnd;
	}

	public int getTestTimeId() {
		return testTimeId;
	}

	public void setTestTimeId(int testTimeId) {
		this.testTimeId = testTimeId;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public String getTestStart() {
		return testStart;
	}

	public void setTestStart(String testStart) {
		this.testStart = testStart;
	}

	public String getTestEnd() {
		return testEnd;
	}

	public void setTestEnd(String testEnd) {
		this.testEnd = testEnd;
	}
}
