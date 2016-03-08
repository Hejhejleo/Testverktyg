package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/** Creates a schoolclass
 * 
 * @author Mattias Larsson
 *
 */

@Entity
@NamedQuery (name = "listSchoolClasses", query = "select schoolclass from SchoolClass schoolclass")
public class SchoolClass {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int classId;
	private String className;
	@OneToMany(cascade = CascadeType.ALL)
	private List<User> studentList;
	
	/** Constructor
	 * 
	 */
	public SchoolClass() {}
	
	/** Constructor
	 * 
	 * @param className - a String that contains the name of this schoolclass
	 */
	public SchoolClass(String className) {
		studentList = new ArrayList<>();
		this.className = className;
		
	}
	
	/** Add a student to this schoolclass
	 * 
	 * @param student - An instance of User
	 */
	public void addStudent(User student) {
		this.studentList.add(student);
	}
	
	/** Returns the students in this schoolclass
	 * 
	 * @return a List<User> of students in this schoolclass
	 */
	public List<User> getStudents() {
		return this.studentList;
	}
	
	/** Sets the name of this schoolclass
	 * 
	 * @param className - a String that names this schoolclass
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	/** Returns the name of this schoolclass
	 * 
	 * @return a String with the name of this schoolclass
	 */
	public String getClassName() {
		return className;
	}
}