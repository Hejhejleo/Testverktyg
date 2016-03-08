package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/** Stores a student's answer to a specific question
 * 
 * @author Mattias Larsson
 *
 */
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
	private String feedback;
	private String note;
	private double givenPoints;
	
	/** Constructor
	 * 
	 */
	public StudentAnswer() {}
	
	/** Constructor
	 * 
	 * @param user - an instance of User
	 * @param question - an instance of Question
	 * @param answer - a String with the answer
	 * @param feedback - a String with the feedback
	 * @param note - a String with a note
	 * @param givenPoints - a double with given points
	 */
	public StudentAnswer (User user, Question question, String answer, String feedback, String note, double givenPoints) { 
		this.user = user;
		this.question = question;
		this.answer = answer;
		this.feedback = feedback; // feedback to the student
		this.note = note; // teacher's own note
		this.givenPoints = givenPoints;
	}

	/** Returns the user
	 * 
	 * @return an instance of User
	 */
	public User getUser() {
		return user;
	}
	
	/** Sets the user
	 * 
	 * @param user - An instance of User
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/** Returns the question
	 * 
	 * @return an instance of Question
	 */
	public Question getQuestion() {
		return question;
	}

	/** Sets the question to this answer
	 * 
	 * @param question - an instance of Question
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/** Returns the answer
	 * 
	 * @return a String with the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/** Sets the answer
	 * 
	 * @param answer - a String with the answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	/** Returns the feedback
	 * 
	 * @return a String with the feedback
	 */
	public String getFeedback() {
		return feedback;
	}

	/** Sets the feedback from the teacher to the student
	 * 
	 * @param feedback - a String with the feedback
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	/** Returns the teacher's own note
	 * 
	 * @return a String with the note
	 */
	public String getNote() {
		return note;
	}

	/** Sets the teacher's own note
	 * 
	 * @param note - a String with the note
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	/** Returns the points the student got on the question
	 * 
	 * @return a double with the points on the question
	 */
	public double getGivenPoints() {
		return givenPoints;
	}

	/** Sets the points awarded for the question
	 * 
	 * @param givenPoints - a double with the points awarded
	 */
	public void setGivenPoints(double givenPoints) {
		this.givenPoints = givenPoints;
	}
	
	
	
	
}
