package ca.mcgill.ecse321.cooperator9.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class StudentEvaluationForm extends Document{
	
	/**
	 * If you're coding a method which uses this type of object, do not use this.
	 * This exists to silence Spring's complaints of no default constructors for this entity.
	 */
	public StudentEvaluationForm() {	super();	}
	
	public StudentEvaluationForm(String name, String uri, byte[] fileData) {
		super();
		this.construct(name, uri, fileData);
	}
	
	public boolean equals( Object obj ) {
		if( !this.getClass().equals(obj.getClass()) )
			return false;
		if( !super.equals(obj) )
			return false;
		return true;
	}

}
