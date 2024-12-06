package ca.mcgill.ecse321.cooperator9.dto;

import ca.mcgill.ecse321.cooperator9.model.TaxForm;
import ca.mcgill.ecse321.cooperator9.model.VerificationState;

public class TaxFormDescriptors extends DocumentDescriptors {

	private short year;
	
	public TaxFormDescriptors() {	super();			}
	public TaxFormDescriptors(TaxForm taxform) {
		super(taxform);
		this.year	=	taxform.getYear();
	}
	public TaxFormDescriptors(long docid, String name, String description, 
			String instructions, String uri, byte[] data, short year) {
		super(docid,name,description,instructions,uri,data);
		this.year	=	year;
		//The purpose of this descriptor is to not contain byte data.
	}
	
	public short getYear() {		return this.year;	}
	
}
