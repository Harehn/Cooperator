package ca.mcgill.ecse321.cooperator9;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import ca.mcgill.ecse321.cooperator9.model.EmployeeProfile;
import ca.mcgill.ecse321.cooperator9.model.Employer;
import ca.mcgill.ecse321.cooperator9.model.EmploymentContract;
import ca.mcgill.ecse321.cooperator9.model.EmploymentStatus;
import ca.mcgill.ecse321.cooperator9.model.StudentEvaluationForm;
import ca.mcgill.ecse321.cooperator9.model.TaxForm;
import ca.mcgill.ecse321.cooperator9.model.VerificationState;

public class EmployeeProfileTest {

	@Test
	public void test() {
		EmployeeProfile employeeProfile= createEmployeeProfile();
		assertEquals(LocalDate.of(2018, 1, 7),employeeProfile.getStartDate());
		assertEquals(LocalDate.of(2019, 2, 25),employeeProfile.getEndDate());
		assertEquals(19,employeeProfile.getId());
		assertEquals(EmploymentStatus.ACTIVE,employeeProfile.getStatus());
		
		testEmployer(employeeProfile.getEmployer()); 
		testEvaluationForm(employeeProfile.getEvaluationOfStudent());
		testEmploymentContract(employeeProfile.getContract());
	}
	
	
	
	public EmployeeProfile createEmployeeProfile() {
		EmployeeProfile employeeProfile= new EmployeeProfile();
		
		employeeProfile.setStartDate(LocalDate.of(2018, 1, 7));
		employeeProfile.setEndDate(LocalDate.of(2019, 2, 25));
		employeeProfile.setId(19);
		employeeProfile.setStatus(EmploymentStatus.ACTIVE);
		
		employeeProfile.setEmployer(createEmployer());
		employeeProfile.setEvaluationOfStudent(createStudentEvaluationForm());
		employeeProfile.setContract(createEmploymentContract());
		
		return employeeProfile;
	}
	
	public void testEmployer(Employer employer) {
		assertEquals(employer.getCompany(),"This Company");
		assertEquals(employer.getFirstName(),"Emp");
		assertEquals(employer.getLastName(),"ployer");
		assertEquals(19,employer.getId());
	}

	public Employer createEmployer() {
		Employer employer = new Employer();
		employer.setCompany("This Company");
		employer.setId(19);
		employer.setFirstName("Emp");
		employer.setLastName("ployer");
		return employer;
	}
	
	public void testEvaluationForm(StudentEvaluationForm studentEvaluationForm) {
		assertEquals(studentEvaluationForm.getDescription(),"Description");
		assertEquals(studentEvaluationForm.getInstructions(),"Instructions");
		assertEquals(studentEvaluationForm.getName(),"Name");
		assertEquals(studentEvaluationForm.getUri(),"C://Users/Candy/ecse321-group-project-09/Backend/");
		for( int i = 0 ; i < 4 ; i++ )
			assertTrue(studentEvaluationForm.getData()[i] == (byte)i);
		//assertEquals(studentEvaluationForm.getDocID(),19);
		
	}
	
	public StudentEvaluationForm createStudentEvaluationForm() {
		byte[] byteArray = {(byte)0,(byte)1,(byte)2,(byte)3};
		StudentEvaluationForm studentEvaluationForm = 
				new StudentEvaluationForm("Name","C://Users/Candy/ecse321-group-project-09/Backend/",byteArray);
		studentEvaluationForm.setDescription("Description");
		studentEvaluationForm.setInstructions("Instructions");
		return studentEvaluationForm;
	}
	
	public void testEmploymentContract(EmploymentContract employmentContract ) {
		assertEquals("Description",employmentContract.getDescription());
		assertEquals("Instructions",employmentContract.getInstructions());
		assertEquals("Name",employmentContract.getName());
		assertEquals(employmentContract.getVerified(),VerificationState.VERIFIED);
		assertEquals("C://Users/Candy/ecse321-group-project-09/Backend/",employmentContract.getUri());
		for( int i = 0 ; i < 4 ; i++ )
			assertTrue(employmentContract.getData()[i] == (byte)i);
	}
	
	public EmploymentContract createEmploymentContract() {
		byte[] byteArray = {(byte)0,(byte)1,(byte)2,(byte)3};
		EmploymentContract employmentContract = 
				new EmploymentContract("Name","C://Users/Candy/ecse321-group-project-09/Backend/",byteArray);
		employmentContract.setDescription("Description");
		employmentContract.setInstructions("Instructions");
		employmentContract.setVerified(VerificationState.VERIFIED);
		return employmentContract;
	}
}
