package ca.mcgill.ecse321.cooperator9.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.cooperator9.model.Employer;
import ca.mcgill.ecse321.cooperator9.model.Invitation;
import ca.mcgill.ecse321.cooperator9.model.SocialEvent;

/**
 * 
 * @author Edwin
 * 	Last updated on 2019-03-02-17:36 to have the RepositoryRestResource annotation.
 *
 */
@RepositoryRestResource( collectionResourceRel = " invitations", path = "invitations" )
public interface InvitationRepository extends CrudRepository<Invitation,Long> {

	Invitation findById(long id);
	List<Invitation> findByEmployer(Employer employer);
	List<Invitation> findBySocialEvent(SocialEvent socialEvent);
	
	boolean existsById(long id);
	boolean existsByEmployer(Employer employer);
	boolean existsBySocialEvent(SocialEvent socialEvent);
	
}
