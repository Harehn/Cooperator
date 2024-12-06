package ca.mcgill.ecse321.cooperator9;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AttendanceStatusTest.class, CoOperatorRepositoryTest.class, EmployeeProfileTest.class,
		EmployerTest.class, EmploymentContractTest.class, EmploymentStatusTest.class, InvitationTest.class,
		 SocialEventTest.class, StudentEvaluationFormTest.class, TaxFormTest.class,
		VerificationStateTest.class })
public class AllTests {

}
