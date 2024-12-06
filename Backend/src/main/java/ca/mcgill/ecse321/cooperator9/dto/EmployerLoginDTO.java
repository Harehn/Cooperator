package ca.mcgill.ecse321.cooperator9.dto;

import ca.mcgill.ecse321.cooperator9.model.Employer;

public class EmployerLoginDTO {

	private String company;
	private String firstName;
	private String lastName;
	private String sessionId;
	
	public String getCompany() {
		return company;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getSessionId() {
		return sessionId;
	}
	
	public EmployerLoginDTO( Employer employer , String sessionId ) {
		this.company = employer.getCompany();
		this.firstName = employer.getFirstName();
		this.lastName = employer.getLastName();
		this.sessionId = sessionId;
	}
	
}
