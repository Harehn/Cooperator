package ca.mcgill.ecse321.cooperator9.dao;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.cooperator9.model.SocialEvent;
import ca.mcgill.ecse321.cooperator9.model.Invitation;

/**
 * 
 * @author Agent-Bennette (Edwin Pan)
 * Last updated on 2019-03-02-17:42 where the RepositoryRestResource annotation was added.
 *
 */
@RepositoryRestResource( collectionResourceRel = "socialevents", path = "socialevents" )
public interface SocialEventRepository extends CrudRepository<SocialEvent,Long> {

	//Methods based on Domain Model
	
	SocialEvent findSocialEventById( long id );
	
	SocialEvent findSocialEventByInvitations( Invitation invitation );
	
	List<SocialEvent> findSocialEventsByName( String name );
	
	List<SocialEvent> findSocialEventsByDescription( String description );
	
	List<SocialEvent> findSocialEventsByLocation( String location );
	
	List<SocialEvent> findSocialEventsByStartDate( LocalDate startDate );
	
	List<SocialEvent> findSocialEventsByEndDate( LocalDate endDate );
	
	List<SocialEvent> findSocialEventsByStartTime( LocalTime startTime );
	
	List<SocialEvent> findSocialEventsByEndTime( LocalTime endTime );
	
	//Extra functionalities
	
	boolean existsById( long id );
	boolean existsByInvitations( Invitation invitation );
	boolean existsByName( String name );
	boolean existsByDescription( String description );
	boolean existsByLocation( String location );
	boolean existsByStartDate( LocalDate startDate );
	boolean existsByEndDate( LocalDate endDate ) ;
	boolean existsByStartTime( LocalTime startTime );
	boolean existsByEndTime( LocalTime endTime );
	
}