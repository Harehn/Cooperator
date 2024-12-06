package ca.mcgill.ecse321.cooperator9.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Id;
import java.util.Set;

/**
 * Implemented by Student Team (Group 1)
 */
@Entity
public class Student{

	private long id;
	private String firstName;
	private String lastName;
	private Set<EmployeeProfile> employeeProfiles;

	public void 	setId(long id) {	this.id = id;	}
	@Id
	public long 	getId() {			return this.id;	}
	
	public void 	setFirstName( String firstName ) {	this.firstName = firstName;	}
	public String 	getFirstName() {					return this.firstName;		}
	
	public void 	setLastName( String lastName )	{	this.lastName = lastName;	}
	public String 	getLastName() {						return this.lastName;		}
	
	public void 	setEmployeeProfiles(Set<EmployeeProfile> employeeProfiles) {
		this.employeeProfiles = employeeProfiles;
	}
	@ManyToMany
	public Set<EmployeeProfile> getEmployeeProfiles() {	return this.employeeProfiles;}	

	public boolean equals( Object obj ) {
		if( obj.getClass() == this.getClass() )
			return false;
		if( ((Student)obj).getId() != this.getId() )
			return false;
		if( !((Student)obj).getFirstName().equals(this.getFirstName()) )
			return false;
		if( !((Student)obj).getLastName().equals(this.getLastName()) )
			return false;
		return true;
	}
   
}
