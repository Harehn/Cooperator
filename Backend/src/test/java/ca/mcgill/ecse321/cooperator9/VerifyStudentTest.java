package ca.mcgill.ecse321.cooperator9;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.cooperator9.dao.DocumentRepository;
import ca.mcgill.ecse321.cooperator9.dao.EmployeeProfileRepository;
import ca.mcgill.ecse321.cooperator9.dao.EmployerRepository;
import ca.mcgill.ecse321.cooperator9.dao.LoginCredentialsRepository;
import ca.mcgill.ecse321.cooperator9.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator9.model.Document;
import ca.mcgill.ecse321.cooperator9.model.EmployeeProfile;
import ca.mcgill.ecse321.cooperator9.model.Employer;
import ca.mcgill.ecse321.cooperator9.model.EmploymentContract;
import ca.mcgill.ecse321.cooperator9.model.EmploymentStatus;
import ca.mcgill.ecse321.cooperator9.model.LoginCredentials;
import ca.mcgill.ecse321.cooperator9.model.TaxForm;
import ca.mcgill.ecse321.cooperator9.model.VerificationState;
import ca.mcgill.ecse321.cooperator9.service.CoOperatorService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class VerifyStudentTest {
	
	//@Test
	public void contextLoads() {

	}
	
	//@ Autowired
	private LoginCredentialsRepository loginCredentialsRepository=mock(LoginCredentialsRepository.class);
	@ Autowired
	private CoOperatorService CS=mock(CoOperatorService.class);
	
	private Document doc;
	
	private EmployeeProfile employeeProfile=mock(EmployeeProfile.class);
	private EmploymentContract employmentContract=mock(EmploymentContract.class); 
	private Employer employer=mock(Employer.class);
	private LoginCredentials loginCredentials=mock(LoginCredentials.class);
	
	private static final String RANDOM_EMPLOYER_FIRSTNAME= "Random employer";
	private static final String CORRECT_PASSWORD= "The most secure Password Ever!!";
	private static final String INCORRECT_PASSWORD= "1234";
	private static final String USERNAME="Admin";
	private VerificationState contractState=VerificationState.UNVERIFIED;
	
	
	@Before
	public void setMockOutput() {
		
		//Obselete for new EmploymentContract
		/*
		when(CS.verifyEmploymentContract(any(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation)->{
			if( !((EmploymentContract)invocation.getArgument(0)).getProfile().getEmployer().equals( 
					loginCredentialsRepository.findByUsername(invocation.getArgument(1)).attemptAccess(invocation.getArgument(1), invocation.getArgument(2)) ) )
				return false;
			contractState=VerificationState.VERIFIED;
			((EmploymentContract)invocation.getArgument(0)).setVerified(VerificationState.VERIFIED);
			return true;
		});
		
		when(CS.denyEmploymentContract(any(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation)->{
			if( !((EmploymentContract)invocation.getArgument(0)).getProfile().getEmployer().equals( 
					loginCredentialsRepository.findByUsername(invocation.getArgument(1)).attemptAccess(invocation.getArgument(1), invocation.getArgument(2)) ) )
				return false;
			((EmploymentContract)invocation.getArgument(0)).setVerified(VerificationState.DENIED);
			contractState=VerificationState.DENIED;
			((EmploymentContract)invocation.getArgument(0)).getProfile().setStatus(EmploymentStatus.DENIED);
			return true;
		});
		*/
		
		when(employmentContract.getVerified()).thenAnswer((InvocationOnMock invocation)->{
			return contractState;
		}
				);
		
		
		//when(employmentContract.getProfile()).thenReturn(employeeProfile); getProfile no longer exists.
		when(employeeProfile.getEmployer()).thenReturn(employer);
		when(employer.getFirstName()).thenReturn(RANDOM_EMPLOYER_FIRSTNAME);
		when(loginCredentialsRepository.findByUsername(USERNAME)).thenReturn((LoginCredentials) loginCredentials);
		when(loginCredentials.attemptAccess(USERNAME, CORRECT_PASSWORD)).thenReturn(employer);
		when(loginCredentials.attemptAccess(USERNAME, INCORRECT_PASSWORD)).thenReturn(null);
		
		
		
		
	}
	
	//@Test
	//public void testVerifyWhenWrongAccess() {
	//	assertTrue("Expected verify to fail",!CS.verifyEmploymentContract(employmentContract, USERNAME, INCORRECT_PASSWORD));
	//}
	
	//@Test
	//public void testVerifyWhenGoodAccess() {
	//	assertTrue("Expected verify to succeed",CS.verifyEmploymentContract(employmentContract, USERNAME, CORRECT_PASSWORD));
	//	assertEquals(VerificationState.VERIFIED,employmentContract.getVerified());
	//}
	
	//@Test
	//public void testDenyWhenWrongAccess() {
	//	assertTrue("Expected verify to fail",!CS.denyEmploymentContract(employmentContract, USERNAME, INCORRECT_PASSWORD));
	//}
	//
	//@Test
	//public void testDenyWhenGoodAccess() {
	//	assertTrue("Expected verify to succeed",CS.denyEmploymentContract(employmentContract, USERNAME, CORRECT_PASSWORD));
	//	assertEquals(VerificationState.DENIED,employmentContract.getVerified());
	//}
	
	
	
}
