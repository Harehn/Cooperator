package ca.mcgill.ecse321.cooperator9;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import ca.mcgill.ecse321.cooperator9.model.AttendanceStatus;
import ca.mcgill.ecse321.cooperator9.model.Employer;
import ca.mcgill.ecse321.cooperator9.model.Invitation;
import ca.mcgill.ecse321.cooperator9.model.SocialEvent;

public class InvitationTest {

	@Test
	public void test() {
		Invitation invitation = createInvitation();
		assertEquals(19,invitation.getId());
		assertEquals(AttendanceStatus.UNKNOWN,invitation.getStatus());
		testEmployer(invitation.getEmployer());
		testSocialEvent(invitation.getSocialEvent());
	}
	
	public Invitation createInvitation() {
		Invitation invitation=new Invitation();
		invitation.setId(19);
		invitation.setStatus(AttendanceStatus.UNKNOWN);
		invitation.setSocialEvent(createSocialEvent(invitation));
		invitation.setEmployer(createEmployer());
		return invitation;
	}

	public void testSocialEvent(SocialEvent event) {
		assertEquals( event.getName()		, "My Event"				);
		assertEquals( event.getDescription(), "Desc"					);
		assertEquals( event.getLocation()	, "Location"				);
		assertEquals( event.getId()			, 19						);
		assertEquals( event.getStartDate() 	, LocalDate.of(2018,1,29) 	);
		assertEquals( event.getEndDate() 	, LocalDate.of(2018,2,14)	);
		assertEquals( event.getStartTime() 	, LocalTime.of(0,20) 		);
		assertEquals( event.getEndTime() 	, LocalTime.of(23,30) 		);
	}

	public SocialEvent createSocialEvent(Invitation invitation) {
		SocialEvent event=new SocialEvent();
		event.setName("My Event");
		event.setDescription("Desc");		
		event.setStartDate(LocalDate.of(2018,1,29));
		event.setEndDate(LocalDate.of(2018,2,14));
		event.setStartTime(LocalTime.of(0,20) 	);
		event.setEndTime(LocalTime.of(23,30));
		event.setLocation("Location");
		event.setId(19);
		Set<Invitation>  mySet= new HashSet <Invitation>();
		mySet.add(invitation);
		event.setInvitations(mySet);
		return event;
	}
	
	public void testEmployer(Employer employer) {
		assertEquals(employer.getCompany(),"This Company");
		assertEquals(employer.getFirstName(),"Emp");
		assertEquals(employer.getLastName(),"ployer");
	}

	public Employer createEmployer() {
		Employer employer = new Employer();
		employer.setCompany("This Company");
		employer.setFirstName("Emp");
		employer.setLastName("ployer");
		return employer;
	}


}
