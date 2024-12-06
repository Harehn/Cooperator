package ca.mcgill.ecse321.cooperator9.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.cooperator9.model.Employer;
import ca.mcgill.ecse321.cooperator9.model.Invitation;
import ca.mcgill.ecse321.cooperator9.model.EmployeeProfile;

/**
 * 
 * @author Agent-Bennette (Edwin Pan)
 * Last updated on 2019-03-02-17:38 to have RepositoryRestResource annotation added.
 *
 */
@RepositoryRestResource( collectionResourceRel = "employers", path = "employers" )
public interface EmployerRepository extends CrudRepository<Employer,Long> {

	//Methods based on Domain Model
	
	List<Employer> findEmployersByCompany( String company );
	
	List<Employer> findEmployersByFirstName( String firstName );
	
	List<Employer> findEmployersByLastName( String lastName );
	
	Employer findEmployerEmployerById( long id );
	
	Employer findEmployerByEmployees( EmployeeProfile employee );
	
	Employer findEmployerByInvitations( Invitation invitations );
	
	//Extra functionalities
	
	boolean existsByCompany( String company );
	boolean existsByFirstName( String firstName );
	boolean existsByLastName( String lastName );
	boolean existsById( long id );
	//no boolean related function for findByEmployeeProfile since an employeeProfile cannot exist without an employer.
	boolean existsByInvitations( Invitation invitation );
	
}
