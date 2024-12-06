package ca.mcgill.ecse321.cooperator9.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.cooperator9.model.LoginCredentials;

@RepositoryRestResource( collectionResourceRel = "logins" , path = "logins" )
public interface LoginCredentialsRepository extends CrudRepository<LoginCredentials,String> {

	/**
	 * DO NOT PASS THIS METHOD DIRECTLY TO A SYSTEM USER!
	 * This method passes over LoginCredentials as an object, which contains the Username, Password, and the Key
	 * 	(Key being the CoOperatorUser object, which is necessary for accessing the database related to that user)
	 * 	to a user's account! As such, this should only be used inside a Service method which keeps it temporarily for
	 * 	credential validation purposes alone on a per-account basis.
	 * THERE SHOULD BE NO SERVICE METHODS FOR OBTAINING ALL CREDENTIALS IN THIS REPOSITORY.
	 */
	LoginCredentials findByUsername( String username );
	boolean existsByUsername( String username );

}
