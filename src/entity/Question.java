package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity

public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int questionId;
	private double points;
	private boolean directFeedback;
	private String questionTitle;
	private String questionText;
	@ManyToOne
	private Test test;
	@ManyToOne
	private QuestionType qType;
	
	public Question() {}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public boolean isDirectFeedback() {
		return directFeedback;
	}

	public void setDirectFeedback(boolean directFeedback) {
		this.directFeedback = directFeedback;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public void setQuestionType(QuestionType qType) {
		this.qType = qType;
	}
	
	public QuestionType getquestionType() {
		return qType;
	}
	
	public Question(double points, boolean directFeedback, String questionTitle, String questionText, Test test, QuestionType questionType) {
		this.points = points;
		this.directFeedback = directFeedback;
		this.questionTitle = questionTitle;
		this.questionText = questionText;
		this.test = test;
		this.qType = questionType;
	}
	
	
}
