package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Answers {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int answerId;
	@OneToOne
	private Question question;
	private List<String> answerList;
	private int correctAnswerIndex;
	
	public Answers() {}
	
	public Answers(Question question) {
		this.question = question;
		answerList = new ArrayList<>();
	}
	
	public Question getQuestion() {
		return this.question;
	}
	
	public void setQuestion(Question question) {
		this.question=question;
	}
	
	public List<String> getAnswerList() {
		return this.answerList;
	}
	
	public void addAnswer(String answer) {
		this.answerList.add(answer);
	}
	
	public void setCorrectAnswer(int index) {
		this.correctAnswerIndex = index;
	}
	
	public String getCorrectAnswer() {
		return answerList.get(correctAnswerIndex);
	}
}
