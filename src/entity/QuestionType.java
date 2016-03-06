package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/** Defines a type of question
 *  for example text, multiple choices etc.
 * 
 * @author Mattias Larsson
 *
 */

@Entity
public class QuestionType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int qTypeId;
	private String questionType;
	@OneToMany
	private List<Question> questionList;
	
	/** Constructor
	 * 
	 */
	public QuestionType() {}
	
	/** Constructor
	 * 
	 * @param questionType a String defining this questiontype's type
	 */
	public QuestionType(String questionType) {
		this.questionType = questionType;
		questionList = new ArrayList<>();
	}
	
	/** Adds a question to this questiontype
	 * 
	 * @param q - an instance of Question
	 */
	public void addQuestion(Question q) {
		questionList.add(q);
	}
	
	/** Returns a List<Question> of questions
	 * which are of this type
	 * 
	 * @return List<Question> - A list that contains instances of Question
	 */
	public List<Question> getQuestions() {
		return questionList;
	}
	
	/** Sets the questiontype tp this instance
	 * 
	 * @param questionType - a String that defines this questiontype
	 */
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	
	/** Returns this instance's questiontype
	 * 
	 * @return a String that defines this questiontype
	 */
	public String getQuestionType() {
		return this.questionType;
	}
}
