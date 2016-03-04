package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class StudentAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studentAnswerId;
	@OneToOne
	private int studentId;
	@OneToOne
	private int questionId;
	@OneToOne
	private int testId;
	private String answer;
	
	public StudentAnswer() {}
	
	public StudentAnswer (int studentId, int questionId, int testId, String answer) {
		this.studentId = studentId;
		this.questionId = questionId;
		this.testId = testId;
		this.answer = answer;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
	
	
}
