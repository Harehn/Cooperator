package ca.mcgill.ecse321.cooperator9;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import ca.mcgill.ecse321.cooperator9.model.EmployeeProfile;
import ca.mcgill.ecse321.cooperator9.model.EmploymentStatus;
import ca.mcgill.ecse321.cooperator9.model.StudentEvaluationForm;

public class StudentEvaluationFormTest {

	@Test
	public void test() {
		StudentEvaluationForm studentEvaluationForm= createStudentEvaluationForm();
		assertEquals(studentEvaluationForm.getDescription(),"Description");
		assertEquals(studentEvaluationForm.getInstructions(),"Instructions");
		assertEquals(studentEvaluationForm.getName(),"Name");
		assertEquals(studentEvaluationForm.getUri(),"C://Users/Candy/ecse321-group-project-09/Backend/");
		//assertEquals(studentEvaluationForm.getDocID(),19);
		for( int i = 0 ; i < 4 ; i++ )
			assertTrue(studentEvaluationForm.getData()[i] == (byte)i);
	}
	
	public StudentEvaluationForm createStudentEvaluationForm() {
		byte[] byteArray = {(byte)0,(byte)1,(byte)2,(byte)3};
		StudentEvaluationForm studentEvaluationForm = 
				new StudentEvaluationForm("Name","C://Users/Candy/ecse321-group-project-09/Backend/", byteArray);
		//studentEvaluationForm.setDocID(19);
		//studentEvaluationForm.setFilePath("C://Users/Candy/ecse321-group-project-09/Backend/");
		studentEvaluationForm.setDescription("Description");
		studentEvaluationForm.setInstructions("Instructions");
		//studentEvaluationForm.setName("Name");
		return studentEvaluationForm;
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
