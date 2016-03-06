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
	private User user;
	@OneToOne
	private Question question;
	private String answer;
	
	public StudentAnswer() {}
	
	public StudentAnswer (User user, Question question, String answer) {
		this.user = user;
		this.question = question;
		this.answer = answer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestionId(Question question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
	
	
}
