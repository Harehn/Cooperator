package ca.mcgill.ecse321.cooperator9;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.mcgill.ecse321.cooperator9.model.AttendanceStatus;

public class AttendanceStatusTest {

	@Test
	public void test() {
		//UNKNOWN, ATTENDING, REFUSED
		assertEquals("UNKNOWN",AttendanceStatus.UNKNOWN.toString());
		assertEquals("REFUSED",AttendanceStatus.REFUSED.toString());
		assertEquals("ATTENDING",AttendanceStatus.ATTENDING.toString());
	}

}
