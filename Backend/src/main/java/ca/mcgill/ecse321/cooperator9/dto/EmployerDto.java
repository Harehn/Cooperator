package ca.mcgill.ecse321.cooperator9.dto;

import java.util.LinkedHashSet;
import java.util.Set;

import ca.mcgill.ecse321.cooperator9.model.EmployeeProfile;
import ca.mcgill.ecse321.cooperator9.model.Employer;
import ca.mcgill.ecse321.cooperator9.model.Invitation;

/**
 * 
 * @author Edwin
 * 
 * DTO object made because returning Employer objects to a REST request causes recursive stack overflow due to
 * internal references between itself and its employeeprofiles and invitations referenced.
 * This DTO aims to be something that can be called and returned to a client without error.
 * 
 */
public class EmployerDto {

	private long		id;
	private String 		company;
	private String 		firstName;
	private String 		lastName;
	private Set<EmployeeProfileDescriptors>	employees;
	private Set<InvitationDescriptors>		invitations;
	
	public EmployerDto() {}
	public EmployerDto(Employer employer) {
		this.id = employer.getId();
		this.company = employer.getCompany();
		this.firstName = employer.getFirstName();
		this.lastName = employer.getLastName();
		employees = new LinkedHashSet<EmployeeProfileDescriptors>();
		for(EmployeeProfile profile : employer.getEmployees())
			employees.add( new EmployeeProfileDescriptors(profile) );
		invitations = new LinkedHashSet<InvitationDescriptors>();
		for(Invitation invitation : employer.getInvitations())
			invitations.add( new InvitationDescriptors(invitation) );
	}
	public static EmployerDto convertToDto(Employer employer) {
		return new EmployerDto(employer);
	}
	
	public long getId() {
		return this.id; }
	public String getCompany() {	
		return this.company;	}
	public String getFirstName() {	
		return this.firstName;	}
	public String getLastName() {	
		return this.lastName;	}
	public Set<EmployeeProfileDescriptors> getEmployees() {	
		return this.employees;	}
	public Set<InvitationDescriptors> getInvitations() {	
		return this.invitations;}
	
}
