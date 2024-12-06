package ca.mcgill.ecse321.cooperator9.dto;

import ca.mcgill.ecse321.cooperator9.model.EmploymentContract;
import ca.mcgill.ecse321.cooperator9.model.VerificationState;

public class EmploymentContractDescriptors extends DocumentDescriptors {

	private VerificationState verified;
	
	public EmploymentContractDescriptors() {	super();				}
	public EmploymentContractDescriptors( EmploymentContract contract ) {
		super();
		this.docid 			= contract.getDocid();
		this.name 			= contract.getName();
		this.description 	= contract.getDescription();
		this.instructions 	= contract.getInstructions();
		this.uri 			= contract.getUri();
		this.verified	=	contract.getVerified();
	}
	public EmploymentContractDescriptors(long docid, String name, String description, 
			String instructions, String uri, byte[] data, VerificationState verified) {
		super(docid,name,description,instructions,uri,data);
		this.verified	=	verified;
		//The purpose of this descriptor is to not contain byte data.
	}
	
	public VerificationState getVerified() {	return this.verified;	}
	
}
