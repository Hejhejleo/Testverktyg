package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/** Stores a student's answer to a specific question
 * 
 * @author Mattias Larsson
 *
 */
@Entity
@NamedQuery(query="select sa from StudentAnswer sa where sa.question= :question and sa.user= :user", name="getStudentAnswer")
public class StudentAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studentAnswerId;
	@OneToOne
	private User user;
	@OneToOne
	private Question question;
	private String answer;
	private String teacherComment;
	private String note;
	private Double givenPoints;
	
	
	/** Constructor
	 * 
	 */
	public StudentAnswer() {}
	
	/** Constructor
	 * 
	 * @param user - an instance of User
	 * @param question - an instance of Question
	 * @param answer - a String with the answer
	 * @param teacherComment - a String with the feedback
	 * @param note - a String with a note
	 * @param givenPoints - a Double with given points
	 */
	public StudentAnswer (User user, Question question, String answer, String teacherComment, String note, Double givenPoints) { 
		this.user = user;
		this.question = question;
		this.answer = answer;
		this.teacherComment = teacherComment; // feedback to the student
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
	
	/** Returns the feedback from the teacher to the student
	 * 
	 * @return a String with the teacher's comment
	 */
	public String getTeacherComment() {
		return teacherComment;
	}

	/** Sets the feedback from the teacher to the student
	 * 
	 * @param teacherComment - a String with the teacher's comment
	 */
	public void setTeacherComment(String teacherComment) {
		this.teacherComment = teacherComment;
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
	
	/** Returns the points the student was awarded for the question
	 * 
	 * @return a double with the points awarded on the question
	 */
	public Double getGivenPoints() {
		return givenPoints;
	}

	/** Sets the points awarded for the question
	 * 
	 * @param givenPoints - a double with the points awarded
	 */
	public void setGivenPoints(Double givenPoints) {
		this.givenPoints = givenPoints;
	}
	
	
	
	
}