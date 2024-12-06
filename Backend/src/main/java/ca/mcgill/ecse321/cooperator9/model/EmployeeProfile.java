package ca.mcgill.ecse321.cooperator9.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.time.LocalDate;

//http://tomee.apache.org/examples-trunk/jpa-enumerated/
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

//https://www.baeldung.com/jpa-attribute-converters
//import ca.mcgill.ecse321.cooperator9.util.LocalDateAttributeConverter;

@Entity
public class EmployeeProfile{
	
	private Employer employer;
	@ManyToOne(optional=false)
	public Employer getEmployer() {					return this.employer;		}
	public void setEmployer(Employer employer) {	this.employer = employer;	}

	private EmploymentContract contract;
	@OneToOne(cascade={CascadeType.ALL}, optional=false)
	public EmploymentContract getContract() {				return this.contract;		}
	public void setContract(EmploymentContract contract) {	this.contract = contract;	}

	//DOES MUST BE ALLOWED TO BE NULL WHEN THE PROFILE IS CREATED
	private StudentEvaluationForm evaluationOfStudent;
	@OneToOne(cascade={CascadeType.ALL},optional=true)
	public StudentEvaluationForm getEvaluationOfStudent() {							return this.evaluationOfStudent;				}
	public void setEvaluationOfStudent(StudentEvaluationForm evaluationOfStudent) {	this.evaluationOfStudent = evaluationOfStudent;	}

	private long id;
	public void setId(long value) {	this.id = value;	}
	@Id
	public long getId() {			return this.id;		}
	
	private LocalDate endDate;
	//LocalDates are not entities in the database.
	//@ManyToOne(optional=false)
	public LocalDate getEndDate() {				return this.endDate;	}
	public void setEndDate(LocalDate endDate) {	this.endDate = endDate;	}

	private LocalDate startDate;
	//LocalDates are not entities in the database.
	//@ManyToOne(optional=false)
	public LocalDate getStartDate() {				return this.startDate;		}
	public void setStartDate(LocalDate startDate) {	this.startDate = startDate;	}

	private Student student;
	//@OneToOne(optional=false)
	@ManyToOne(optional=false)
	public Student getStudent() {				return this.student;	}
	public void setStudent(Student student) {	this.student = student;	}

	@Enumerated(EnumType.STRING)
	private EmploymentStatus status;
	//This is not being mapped to any other entity.
	//@OneToOne(cascade={CascadeType.ALL}, optional=false)
	public EmploymentStatus getStatus() {				return this.status;		}
	public void setStatus(EmploymentStatus status) {	this.status = status;	}
	
	public boolean equals( Object obj ) {
		if( !this.getClass().equals( ((EmployeeProfile)obj).getClass() ) )
			return false;
		if( this.getId() != ((EmployeeProfile)obj).getId() )
			return false;
		if( !this.getStudent().equals( ((EmployeeProfile)obj).getStudent() ) )
			return false;
		if( !this.getEmployer().equals( ((EmployeeProfile)obj).getEmployer() ) )
			return false;
		if( !this.getStartDate().equals( ((EmployeeProfile)obj).getStartDate() ) )
			return false;
		if( !this.getEndDate().equals( ((EmployeeProfile)obj).getEndDate() ) )
			return false;
		if( this.getStatus() != ((EmployeeProfile)obj).getStatus() )
			return false;
		if( !this.getContract().equals( ((EmployeeProfile)obj).getContract() ) )
			return false;
		if( !this.getEvaluationOfStudent().equals( ((EmployeeProfile)obj).getEvaluationOfStudent() ) )
			return false;
		return true;
	}
	
}
