package ca.mcgill.ecse321.cooperator9.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

//http://tomee.apache.org/examples-trunk/jpa-enumerated/
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class EmploymentContract extends Document{
	
	/**
	 * If you're coding a method which uses this type of object, do not use this.
	 * This exists to silence Spring's complaints of no default constructors for this entity.
	 */
	public EmploymentContract() {	super();	}
	
	public EmploymentContract(String name, String uri, byte[] fileData) {
		super();
		this.construct(name,uri,fileData);
	}
	
	@Enumerated(EnumType.STRING)
	private VerificationState verified;
	//This is not being mapped to any other entity.
	//@ManyToOne(optional=false)
	public VerificationState getVerified() {				return this.verified;		}
	public void setVerified(VerificationState verified) {	this.verified = verified;	}

	public boolean equals( Object obj ) {
		if( !this.getClass().equals(obj.getClass()) )
			return false;
		if( !super.equals(obj) )
			return false;
		if( this.verified != ((EmploymentContract)obj).verified )
			return false;
		return true;
	}
	
}
