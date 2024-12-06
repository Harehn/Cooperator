package ca.mcgill.ecse321.cooperator9;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.mcgill.ecse321.cooperator9.model.Employer;
import ca.mcgill.ecse321.cooperator9.model.LoginCredentials;

public class LoginCredentialsTest {

	@Test
	public void test() {
		LoginCredentials loginCredentials=new LoginCredentials(new Employer(),"lucifer","qwerty123");
		assertEquals("lucifer",loginCredentials.getUsername());
	}
	
	
}
