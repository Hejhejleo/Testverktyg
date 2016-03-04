package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

@Entity
public class TestTime {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int testTimeId;
	private int studentId;
	private int testId;
	private String testStart;
	private String testEnd;
	
}
