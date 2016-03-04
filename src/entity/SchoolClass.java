package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class SchoolClass {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int classId;
	private String className;
	@OneToMany
	private List<User> studentList;
	
	
	public SchoolClass() {}
	
	public SchoolClass(String className) {
		studentList = new ArrayList<>();
		this.className = className;
		
	}
	
	public void addStudent(User student) {
		this.studentList.add(student);
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassName() {
		return className;
	}
}