package ca.mcgill.ecse321.cooperator9.dto;

import java.time.LocalDateTime;

import ca.mcgill.ecse321.cooperator9.model.AttendanceStatus;
import ca.mcgill.ecse321.cooperator9.model.Invitation;

/**
 * 
 * @author Edwin
 *
 *	A helper class with the EmployerDTO object, this class aims to provide descriptive data of the employee profile
 *	without causing any recursive stack overflows when being returned to the client.
 *
 */
public class InvitationDescriptors {

	private long id;
	private long employerId;
	private String companyName;
	private String employerFirstName;
	private String employerLastName;
	private long socialEventId;
	private String socialEventName;
	private String socialEventDescription;
	private String socialEventLocation;
	private LocalDateTime starts;
	private LocalDateTime ends;
	private AttendanceStatus status;
	
	public InvitationDescriptors() {}
	public InvitationDescriptors(Invitation invitation) {
		this.id = invitation.getId();
		this.employerId = invitation.getEmployer().getId();
		this.companyName = invitation.getEmployer().getCompany();
		this.employerFirstName = invitation.getEmployer().getFirstName();
		this.employerLastName = invitation.getEmployer().getLastName();
		this.socialEventId = invitation.getSocialEvent().getId();
		this.socialEventName = invitation.getSocialEvent().getName();
		this.socialEventDescription = invitation.getSocialEvent().getDescription();
		this.socialEventLocation = invitation.getSocialEvent().getLocation();
		this.starts = LocalDateTime.of(invitation.getSocialEvent().getStartDate(),
				invitation.getSocialEvent().getStartTime());
		this.ends = LocalDateTime.of(invitation.getSocialEvent().getEndDate(), 
				invitation.getSocialEvent().getEndTime());
		this.status = invitation.getStatus();
	}
	
	public long getId() {
		return id; }
	public long getEmployerId() {
		return employerId; }
	public String getCompanyName() {
		return companyName; }
	public String getEmployerFirstName() {
		return employerFirstName; }
	public String getEmployerLastName() {
		return employerLastName; }
	public long getSocialEventId() {
		return socialEventId; }
	public String getSocialEventName() {
		return socialEventName; }
	public String getSocialEventDescription() {
		return socialEventDescription; }
	public String getSocialEventLocation() {
		return socialEventLocation; }
	public LocalDateTime getStart() {
		return this.starts; }
	public LocalDateTime getEnd() {
		return this.ends; }
	public AttendanceStatus getStatus() {
		return this.status;
	}
	
}
