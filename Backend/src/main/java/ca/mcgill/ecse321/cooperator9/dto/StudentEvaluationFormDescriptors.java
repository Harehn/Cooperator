package ca.mcgill.ecse321.cooperator9.dto;

import ca.mcgill.ecse321.cooperator9.model.StudentEvaluationForm;

public class StudentEvaluationFormDescriptors extends DocumentDescriptors {

	public StudentEvaluationFormDescriptors() {super();}
	public StudentEvaluationFormDescriptors(StudentEvaluationForm evalform) {
		super();
		this.docid 			= evalform.getDocid();
		this.name 			= evalform.getName();
		this.description 	= evalform.getDescription();
		this.instructions 	= evalform.getInstructions();
		this.uri 			= evalform.getUri();
	}
	public StudentEvaluationFormDescriptors(long docid, String name, String description, 
			String instructions, String uri, byte[] data) {
		super(docid,name,description,instructions,uri,data);
		//The purpose of this descriptor is to not contain byte data.
	}
	
}
