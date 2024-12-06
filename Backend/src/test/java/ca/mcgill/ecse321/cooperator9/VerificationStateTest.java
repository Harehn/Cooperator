package ca.mcgill.ecse321.cooperator9;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.mcgill.ecse321.cooperator9.model.VerificationState;

public class VerificationStateTest {

	@Test
	public void test() {
		//UNVERIFIED, VERIFIED, DENIED
		assertEquals("UNVERIFIED",VerificationState.UNVERIFIED.toString());
		assertEquals("VERIFIED",VerificationState.VERIFIED.toString());
		assertEquals("DENIED",VerificationState.DENIED.toString());
	}

}
