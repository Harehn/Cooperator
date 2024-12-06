package ca.mcgill.ecse321.cooperator9;

import static org.junit.Assert.*;

import org.junit.Test;
import java.time.LocalTime;
import java.util.HashSet;
import java.time.LocalDate;

import ca.mcgill.ecse321.cooperator9.model.AttendanceStatus;
import ca.mcgill.ecse321.cooperator9.model.Employer;
import ca.mcgill.ecse321.cooperator9.model.Invitation;
import ca.mcgill.ecse321.cooperator9.model.SocialEvent;
import ca.mcgill.ecse321.cooperator9.model.StudentEvaluationForm;

public class SocialEventTest {

	@Test
	public void test() {
		SocialEvent event=createSocialEvent();
		assertEquals( event.getName()		, "My Event"				);
		assertEquals( event.getDescription(), "Desc"					);
		assertEquals( event.getLocation()	, "Location"				);
		assertEquals( event.getId()			, 19						);
		assertEquals( event.getStartDate() 	, LocalDate.of(2018,1,29) 	);
		assertEquals( event.getEndDate() 	, LocalDate.of(2018,2,14)	);
		assertEquals( event.getStartTime() 	, LocalTime.of(0,20) 		);
		assertEquals( event.getEndTime() 	, LocalTime.of(23,30) 		);
		
		testInvitation((Invitation)event.getInvitations().toArray()[0]);
	}

	public SocialEvent createSocialEvent() {
		SocialEvent event=new SocialEvent();
		event.setName("My Event");
		event.setDescription("Desc");		
		event.setStartDate(LocalDate.of(2018,1,29));
		event.setEndDate(LocalDate.of(2018,2,14));
		event.setStartTime(LocalTime.of(0,20) 	);
		event.setEndTime(LocalTime.of(23,30));
		event.setLocation("Location");
		event.setId(19);
		
		HashSet<Invitation> theseInvitations= new HashSet<Invitation>();
		theseInvitations.add(createInvitation());
		event.setInvitations(theseInvitations);
		
		return event;
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
	
}
