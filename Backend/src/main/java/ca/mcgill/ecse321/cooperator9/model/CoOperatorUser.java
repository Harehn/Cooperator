package ca.mcgill.ecse321.cooperator9.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Out of date. Used to be used to integrate the Student object as a subclass of this abstract class for all users. May be removed soon to integrate all functionality directly into Employer class.
 */
@Entity
@Table(name="CoOperatorUsers")
public abstract class CoOperatorUser{
	
	private long id;
	public void setId(long value) {		this.id = value;	}
	@Id
	public long getId() {				return this.id;		}
	
	private String firstName;
	public void setFirstName(String value) {	this.firstName = value;	}
	public String getFirstName() {				return this.firstName;	}
	
	private String lastName;
	public void setLastName(String value) {		this.lastName = value;	}
	public String getLastName() {				return this.lastName;	}
	
	public boolean equals(Object obj) {
		if( !this.getClass().equals(obj.getClass()) )
			return false;
		if( this.id != ((CoOperatorUser)obj).getId() )
			return false;
		if( !this.getFirstName().equals( ((CoOperatorUser)obj).getFirstName() ) )
			return false;
		if( !this.getLastName().equals( ((CoOperatorUser)obj).getLastName() ) )
			return false;
		return true;
	}
	
}
