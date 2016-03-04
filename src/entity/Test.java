package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity

public class Test {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int testId;
	private String testName;
	private String testStart;
	private String testEnd;
	@OneToMany
	private List<Question> questionList;
	
	public Test() {}
	
	public Test(String testName, String testStart, String testEnd) {
		this.testName = testName;
		this.testStart = testStart;
		this.testEnd = testEnd;
		this.questionList = new ArrayList<>();
	}
	
	public void addQuestion(Question question) {
		questionList.add(question);
	}
	
	public List<Question> getQuestions() {
		return questionList;
	}
	
	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
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
