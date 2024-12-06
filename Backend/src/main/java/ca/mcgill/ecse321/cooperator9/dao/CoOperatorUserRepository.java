package ca.mcgill.ecse321.cooperator9.dao;

import java.util.List;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.cooperator9.model.CoOperatorUser;

/**
 * 
 * @author Agent-Bennette (Edwin Pan)
 * 	Last updated on 2019-03-02-17:37 to have RepositoryRestResource annotation added.
 *
 */

@RepositoryRestResource( collectionResourceRel = "cooperatorusers", path = "cooperatorusers" )
public interface CoOperatorUserRepository extends CrudRepository<CoOperatorUser,Long> {

	//Methods based on Domain Model
	
	List<CoOperatorUser> findCoOperatorUsersByFirstName( String firstName );
	
	List<CoOperatorUser> findCoOperatorUsersByLastName( String lastName );
	
	CoOperatorUser findCoOperatorUserById( long id );
	
	//Extra functionalities
	
	boolean existsByFirstName( String firstName );
	boolean existsByLastName( String lastName );
	boolean existsById( long id );
	
}
