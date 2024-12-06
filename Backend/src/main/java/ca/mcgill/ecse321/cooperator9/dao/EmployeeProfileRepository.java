package ca.mcgill.ecse321.cooperator9.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.cooperator9.model.EmployeeProfile;
import ca.mcgill.ecse321.cooperator9.model.CoOperatorUser;
import ca.mcgill.ecse321.cooperator9.model.Employer;
import ca.mcgill.ecse321.cooperator9.model.Student;
import ca.mcgill.ecse321.cooperator9.model.EmploymentContract;
import ca.mcgill.ecse321.cooperator9.model.StudentEvaluationForm;

/**
 * 
 * @author Agent-Bennette (Edwin Pan)
 * Last updated on 2019-03-02-17:38 to have the RepositoryRestResource annotation added.
 *
 */
@RepositoryRestResource( collectionResourceRel = "employeeprofiles", path = "employeeprofiles" )
public interface EmployeeProfileRepository extends CrudRepository<EmployeeProfile,Long> {

	//Methods based on Domain Model
	
	EmployeeProfile 		findEmployeeProfilesById( long id );
	
	List<EmployeeProfile> 	findEmployeeProfilesByStudent( Student student );
	
	List<EmployeeProfile> 	findEmployeeProfilesByEmployer( Employer employer );
	
	List<EmployeeProfile> 	findEmployeeProfilesByStudentAndEmployer( Student student, Employer employer );
	
	EmployeeProfile 		findEmployeeProfileByContract( EmploymentContract contract );
	
	EmployeeProfile 		findEmployeeProfileByEvaluationOfStudent ( StudentEvaluationForm evaluationOfStudent );
	
	//Extra functionalities
	
	boolean existsById( long id );
	boolean existsByStudent( Student student );
	boolean existsByEmployer( Employer employer );
	boolean existsByStudentAndEmployer( Student student, Employer employer );
	//no boolean related function for findByEmploymentContract since the contract exists only in context of a profile.
	//no boolean related function for findByStudenEvaluationForm since the eval exists only in context of a profile.
	
}