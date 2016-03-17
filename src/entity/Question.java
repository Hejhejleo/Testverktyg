package entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/** Stores a question
 * 
 * @author Mattias Larsson
 *
 */

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int questionId;
	private double points;
	private boolean directFeedback;
	@Column(unique=true)
	private String questionTitle;
	private String questionText;
	@ManyToOne(cascade = CascadeType.ALL)
	private Test test;
	@ManyToOne(cascade = CascadeType.ALL)
	private QuestionType qType;
	@OneToOne (cascade = CascadeType.ALL)
	private Answers answer;
	
	/** Constructor
	 * 
	 */
	public Question() {}
	
	/** Constructor
	 * 
	 * @param points - double, How many points is the question worth
	 * @param directFeedback - boolean, Is direct feedback to the student possible 
	 * @param questionTitle - String, The title of the question
	 * @param questionText - String, The actual question
	 * @param test - An instance of Test, which test contains the question
	 * @param questionType - An instance of QuestionType, which type of question is it
	 */
	public Question(double points, boolean directFeedback, String questionTitle, String questionText, Test test, QuestionType questionType, Answers answer) {
		this.points = points;
		this.directFeedback = directFeedback;
		this.questionTitle = questionTitle;
		this.questionText = questionText;
		this.test = test;
		this.qType = questionType;
		this.answer = answer;
	}
	
	/** Returns the value of the question
	 * 
	 * @return the value of the question
	 */
	public double getPoints() {
		return points;
	}
	public int getId(){
		return questionId;		
	}
	public void setAnswer(Answers answer){
		this.answer = answer;
	}

	/** Sets the value of this question
	 * 
	 * @param points Double
	 */
	public void setPoints(double points) {
		this.points = points;
	}

	/** Reurns if direct feedback is possible
	 * 
	 * @return Boolean
	 */
	public boolean isDirectFeedback() {
		return directFeedback;
	}

	/** Sets if direct feedback is possible
	 * 
	 * @param directFeedback - Boolean
	 */
	public void setDirectFeedback(boolean directFeedback) {
		this.directFeedback = directFeedback;
	}
	
	/** Returns the title of this question
	 * 
	 * @return a String containing the title of this question
	 */
	public String getQuestionTitle() {
		return questionTitle;
	}

	/** Sets the title of this question
	 * 
	 * @param questionTitle - String
	 */
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	/** Returns the actual question
	 * 
	 * @return a String containing the actual question
	 */
	public String getQuestionText() {
		return questionText;
	}

	/** Sets this question's actual question
	 * 
	 * @param questionText - a String containing the actual question
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	/** Sets this question's questiontype
	 * 
	 * @param qType - an instance of QuestionType
	 */
	public void setQuestionType(QuestionType qType) {
		this.qType = qType;
	}
	
	/** Returns this question's questiontype
	 * 
	 * @return an instance of QuestionType
	 */
	public QuestionType getquestionType() {
		return qType;
	}
	
	public void setTest(Test test) {
		this.test = test;
	}
}
