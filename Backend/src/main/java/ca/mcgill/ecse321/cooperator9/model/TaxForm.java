package ca.mcgill.ecse321.cooperator9.model;

import javax.persistence.Entity;

@Entity
public class TaxForm extends Document{
	
	/**
	 * If you're coding a method which uses this type of object, do not use this.
	 * This exists to silence Spring's complaints of no default constructors for this entity.
	 */
	public TaxForm() {	super();	}
	
	public TaxForm( String name, String uri, byte[] fileData, short year) {
		super();
		this.construct(name,uri,fileData);
		this.year = year;
	}
	
	private short year;
	public void setYear(short value) {		this.year = value;	}
	public short getYear() {				return this.year;	}
	
	public boolean equals( Object obj ) {
		if( !this.getClass().equals(obj.getClass()))
			return false;
		if( !super.equals(obj) )
			return false;
		if( this.year != ((TaxForm)obj).year )
			return false;
		return true;
	}
	
}
