package ca.mcgill.ecse321.cooperator9.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Employer extends CoOperatorUser{
	
	private String company;
	public void setCompany(String value) {	this.company = value;	}
	public String getCompany() {			return this.company;	}
	
	private Set<EmployeeProfile> employees;
	@OneToMany(mappedBy="employer" )
	public Set<EmployeeProfile> getEmployees() {					return this.employees;	}
	public void setEmployees(Set<EmployeeProfile> employeess) {		this.employees = employeess;}

	private Set<Invitation> invitations;
	@OneToMany(mappedBy="employer" )
	public Set<Invitation> getInvitations() {					return this.invitations;	}
	public void setInvitations(Set<Invitation> invitations) {	this.invitations = invitations;}

	public boolean equals( Object obj ) {
		if( !this.getClass().equals(obj.getClass()) )
			return false;
		if( !super.equals(obj) )
			return false;
		if( !this.getCompany().equals(((Employer)obj).getCompany()) )
			return false;
		return true;
	}
	
}
