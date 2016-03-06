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
	
	/** Constructor
	 * 
	 */
	public StudentAnswer() {}
	
	/** Constructor
	 * 
	 * @param user - an instance of User
	 * @param question - an instance of Question
	 * @param answer - a String with the answer
	 */
	public StudentAnswer (User user, Question question, String answer) {
		this.user = user;
		this.question = question;
		this.answer = answer;
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
	
	
	
	
}
