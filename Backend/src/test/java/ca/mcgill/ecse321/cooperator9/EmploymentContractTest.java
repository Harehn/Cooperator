package ca.mcgill.ecse321.cooperator9;

import static org.junit.Assert.*;

import java.time.LocalDate;

import ca.mcgill.ecse321.cooperator9.model.VerificationState;

import org.junit.Test;

import ca.mcgill.ecse321.cooperator9.model.EmployeeProfile;
import ca.mcgill.ecse321.cooperator9.model.EmploymentContract;
import ca.mcgill.ecse321.cooperator9.model.EmploymentStatus;

public class EmploymentContractTest {

	@Test
	public void test() {
		EmploymentContract employmentContract =createEmploymentContract();
		assertEquals("Description",employmentContract.getDescription());
		assertEquals("Instructions",employmentContract.getInstructions());
		assertEquals("Name",employmentContract.getName());
		assertEquals(employmentContract.getVerified(),VerificationState.VERIFIED);
		//assertEquals(employmentContract.getDocID(),19);
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
	
	public EmployeeProfile createEmployeeProfile() {
		EmployeeProfile employeeProfile= new EmployeeProfile();
		
		employeeProfile.setStartDate(LocalDate.of(2018, 1, 7));
		employeeProfile.setEndDate(LocalDate.of(2019, 2, 25));
		employeeProfile.setId(19);
		employeeProfile.setStatus(EmploymentStatus.ACTIVE);
		return employeeProfile;
	}
	
	public void testEmployeeProfile(EmployeeProfile employeeProfile) {
		assertEquals(LocalDate.of(2018, 1, 7),employeeProfile.getStartDate());
		assertEquals(LocalDate.of(2019, 2, 25),employeeProfile.getEndDate());
		assertEquals(19,employeeProfile.getId());
		assertEquals(EmploymentStatus.ACTIVE,employeeProfile.getStatus());		
	}
}
