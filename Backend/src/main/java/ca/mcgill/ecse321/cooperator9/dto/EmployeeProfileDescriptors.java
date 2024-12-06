package ca.mcgill.ecse321.cooperator9.dto;

import java.time.LocalDate;

import ca.mcgill.ecse321.cooperator9.model.EmployeeProfile;
import ca.mcgill.ecse321.cooperator9.model.EmploymentContract;
import ca.mcgill.ecse321.cooperator9.model.EmploymentStatus;
import ca.mcgill.ecse321.cooperator9.model.StudentEvaluationForm;

/**
 * 
 * @author Edwin
 *
 *	A helper class with the EmployerDTO object, this class aims to provide descriptive data of the employee profile
 *	without causing any recursive stack overflows when being returned to the client.
 *
 */
public class EmployeeProfileDescriptors {

	private long id;
	private long studentId;
	private String studentFirstName;
	private String studentLastName;
	private long employerId;
	private String companyName;
	private String employerFirstName;
	private String employerLastName;
	private LocalDate startDate;
	private LocalDate endDate;
	private EmploymentStatus status;
	private EmploymentContractDescriptors contract;
	private StudentEvaluationFormDescriptors evaluationOfStudent;
	
	public EmployeeProfileDescriptors() {}
	public EmployeeProfileDescriptors(EmployeeProfile profile) {
		this.id = profile.getId();
		this.studentId = profile.getStudent().getId();
		this.studentFirstName = profile.getStudent().getFirstName();
		this.studentLastName = profile.getStudent().getLastName();
		this.employerId = profile.getEmployer().getId();
		this.companyName = profile.getEmployer().getCompany();
		this.employerFirstName = profile.getEmployer().getFirstName();
		this.employerLastName = profile.getEmployer().getLastName();
		this.startDate = profile.getStartDate();
		this.endDate = profile.getEndDate();
		this.status = profile.getStatus();
		this.contract = new EmploymentContractDescriptors(profile.getContract());
		if(profile.getEvaluationOfStudent()!=null)
			this.evaluationOfStudent = new StudentEvaluationFormDescriptors(profile.getEvaluationOfStudent());
	}
	
	public long getId() {
		return id; }
	public long getStudentId() {
		return studentId; }
	public String getStudentFirstName() {
		return studentFirstName; }
	public String getStudentLastName() {
		return studentLastName; }
	public long getEmployerId() {
		return employerId; }
	public String getCompanyName() {
		return companyName; }
	public String getEmployerFirstName() {
		return employerFirstName; }
	public String getEmployerLastName() {
		return employerLastName; }
	public LocalDate getStartDate() {
		return startDate;}
	public LocalDate getEndDate() {
		return endDate;}
	public EmploymentStatus getStatus() {
		return status; }
	public EmploymentContractDescriptors getContract() {
		return contract; }
	public StudentEvaluationFormDescriptors getEvaluationOfStudent() {
		return evaluationOfStudent; }
	
}
