package ca.mcgill.ecse321.cooperator9;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.mcgill.ecse321.cooperator9.model.EmploymentStatus;

public class EmploymentStatusTest {

	@Test
	public void test() {
		//INACTIVE, ACTIVE, ABANDONED, DENIED
		assertEquals("INACTIVE",EmploymentStatus.INACTIVE.toString());
		assertEquals("ACTIVE",EmploymentStatus.ACTIVE.toString());
		assertEquals("ABANDONED",EmploymentStatus.ABANDONED.toString());
		assertEquals("DENIED",EmploymentStatus.DENIED.toString());
	}

}
