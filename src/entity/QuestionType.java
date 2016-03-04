package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class QuestionType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int qTypeId;
	private String questionType;
	@OneToMany
	private List<Question> questionList;
	
	public QuestionType() {}
	
	public QuestionType(String questionType) {
		this.questionType = questionType;
		questionList = new ArrayList<>();
	}
	
	public void addQuestion(Question q) {
		questionList.add(q);
	}
	
	public List<Question> getQuestions() {
		return questionList;
	}
	
	public void setQuestionTye(String questionType) {
		this.questionType = questionType;
	}
	
	public String getQuestionType() {
		return this.questionType;
	}
}
