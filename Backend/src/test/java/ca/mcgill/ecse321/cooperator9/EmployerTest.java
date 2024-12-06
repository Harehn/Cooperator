package ca.mcgill.ecse321.cooperator9;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import ca.mcgill.ecse321.cooperator9.model.AttendanceStatus;
import ca.mcgill.ecse321.cooperator9.model.EmployeeProfile;
import ca.mcgill.ecse321.cooperator9.model.Employer;
import ca.mcgill.ecse321.cooperator9.model.EmploymentStatus;
import ca.mcgill.ecse321.cooperator9.model.Invitation;
import ca.mcgill.ecse321.cooperator9.model.StudentEvaluationForm;
import ca.mcgill.ecse321.cooperator9.model.TaxForm;

public class EmployerTest {

	@Test
	public void test() {
		Employer employer= createEmployer();
		
		assertEquals(employer.getCompany(),"This Company");
		assertEquals(employer.getFirstName(),"Emp");
		assertEquals(employer.getLastName(),"ployer");
		assertEquals(19,employer.getId());
		
		//testTaxForms(employer.getTaxForms());
		//testEvaluationForm(employer.getEmptyEval());
		testInvitation((Invitation)employer.getInvitations().toArray()[0]);
		testEmployeeProfile((EmployeeProfile)employer.getEmployees().toArray()[0]);
	}

	public Employer createEmployer() {
		Employer employer = new Employer();
		employer.setCompany("This Company");
		employer.setId(19);
		employer.setFirstName("Emp");
		employer.setLastName("ployer");
		
		//employer.setTaxForms(createTaxForms());
		//employer.setEmptyEval(createStudentEvaluationForm());
		
		HashSet<EmployeeProfile> theseEmployees=new HashSet<EmployeeProfile>();
		theseEmployees.add(createEmployeeProfile());
		employer.setEmployees(theseEmployees);
		
		//setting invitations
		HashSet<Invitation> theseInvitations= new HashSet<Invitation>();
		theseInvitations.add(createInvitation());
		employer.setInvitations(theseInvitations);
		
		return employer;
	}
	
	public void testTaxForms(Set<TaxForm> taxForms) {
		assertEquals(1,taxForms.size());
		TaxForm taxForm = taxForms.iterator().next();
		assertEquals("Description",taxForm.getDescription());
		assertEquals((short)1998,taxForm.getYear());
		assertEquals("Instructions",taxForm.getInstructions());
		assertEquals("Name",taxForm.getName());
		assertEquals("C://Users/Candy/ecse321-group-project-09/Backend/",taxForm.getUri());
		for( int i = 0 ; i < 4 ; i++ )
			assertTrue(taxForm.getData()[i] == (byte)i);
		//assertEquals(19,taxForm.getDocID());
	}
	
	public Set<TaxForm> createTaxForms() {
		byte[] byteArray = {(byte)0,(byte)1,(byte)2,(byte)3};
		TaxForm taxForm = 
				new TaxForm("Name","C://Users/Candy/ecse321-group-project-09/Backend/",byteArray,(short)1998);
		taxForm.setDescription("Description");
		taxForm.setInstructions("Instructions");
		Set<TaxForm> taxForms = new HashSet<TaxForm>();
		taxForms.add(taxForm);
		return taxForms;

 }
	public void testEvaluationForm(StudentEvaluationForm studentEvaluationForm) {
		assertEquals(studentEvaluationForm.getDescription(),"Description");
		assertEquals(studentEvaluationForm.getInstructions(),"Instructions");
		assertEquals(studentEvaluationForm.getName(),"Name");
		assertEquals(studentEvaluationForm.getUri(),"C://Users/Candy/ecse321-group-project-09/Backend/");
		for( int i = 0 ; i < 4 ; i++ )
			assertTrue(studentEvaluationForm.getData()[i] == (byte)i);
		
	}
	
	public StudentEvaluationForm createStudentEvaluationForm() {
		byte[] byteArray = {(byte)0,(byte)1,(byte)2,(byte)3};
		StudentEvaluationForm studentEvaluationForm= 
				new StudentEvaluationForm("Name","C://Users/Candy/ecse321-group-project-09/Backend/",byteArray);
		studentEvaluationForm.setDescription("Description");
		studentEvaluationForm.setInstructions("Instructions");
		return studentEvaluationForm;
	}
	
	public void testInvitation(Invitation invitation) {
		assertEquals(19,invitation.getId());
		assertEquals(AttendanceStatus.UNKNOWN,invitation.getStatus());
	}
	
	public Invitation createInvitation() {
		Invitation invitation=new Invitation();
		invitation.setId(19);
		invitation.setStatus(AttendanceStatus.UNKNOWN);
		return invitation;
	}
	
	public void testEmployeeProfile(EmployeeProfile employeeProfile) {
		assertEquals(LocalDate.of(2018, 1, 7),employeeProfile.getStartDate());
		assertEquals(LocalDate.of(2019, 2, 25),employeeProfile.getEndDate());
		assertEquals(19,employeeProfile.getId());
		assertEquals(EmploymentStatus.ACTIVE,employeeProfile.getStatus());
		
	}
	
	
	
	public EmployeeProfile createEmployeeProfile() {
		EmployeeProfile employeeProfile= new EmployeeProfile();		
		employeeProfile.setStartDate(LocalDate.of(2018, 1, 7));
		employeeProfile.setEndDate(LocalDate.of(2019, 2, 25));
		employeeProfile.setId(19);
		employeeProfile.setStatus(EmploymentStatus.ACTIVE);
		return employeeProfile;
	}
	
	
	
}
