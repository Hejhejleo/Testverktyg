package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/** Stores a question's answers
 * 
 * 
 * @author Mattias Larsson
 *
 */
@Entity
@NamedQuery(query = "Select a from Answers a where a.question = :questionName", name = "get answers")
public class Answers {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int answerId;
	@OneToOne
	private Question question;
	private List<String> answerList;
	private int correctAnswerIndex;
	
	/** Constructor
	 * 
	 */
	public Answers() {}
	
	/** Constructor
	 * 
	 * @param question - An instance of Question
	 */
	public Answers(Question question) {
		this.question = question;
		answerList = new ArrayList<>();
	}
	
	/** Returns the question
	 * 
	 * @return the Question of this instance
	 */
	public Question getQuestion() {
		return this.question;
	}
	
	/** Sets the question
	 * 
	 * @param question - An instance of Question
	 */
	public void setQuestion(Question question) {
		this.question=question;
	}
	
	/** Returns a list of answers to this question
	 * 
	 * @return a List<String> containing the answers
	 */
	public List<String> getAnswerList() {
		return this.answerList;
	}
	
	/** Adds an answer to to the List<String>
	 * 
	 * @param answer - A String
	 */
	public void addAnswer(String answer) {
		this.answerList.add(answer);
	}
	
	/** Sets the index of the correct answer
	 * 
	 * @param index - An int
	 */
	public void setCorrectAnswer(int index) {
		this.correctAnswerIndex = index;
	}
	
	/** Returns the correct answer
	 * 
	 * @return A String with hte correct answer
	 */
	public String getCorrectAnswer() {
		return answerList.get(correctAnswerIndex);
	}
}
