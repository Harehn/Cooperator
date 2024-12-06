package ca.mcgill.ecse321.cooperator9.dao;

import ca.mcgill.ecse321.cooperator9.model.TaxForm;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 
 * @author Yizhong
 * @editor Edwin
 * 	Most recently added the RepositoryRestResource on 2019-03-02-17:45
 *
 */
@RepositoryRestResource( collectionResourceRel = "taxforms", path = "taxforms" )
public interface TaxFormRepository extends CrudRepository<TaxForm,Long>{
	
	//Methods based on Domain Model
	List<TaxForm> findByYear (short year);
	TaxForm findByDocid( long id );
	List<TaxForm> findByUri( String uri );
	List<TaxForm> findByDescription( String descsription );
	List<TaxForm> findByInstructions( String instructions );
	List<TaxForm> findByName( String name );
	
	//Extra functionalities
	boolean existsByYear(short year);
	boolean existsByDocid( long id );
	boolean existsByUri( String uri  );
	boolean existsByDescription( String description );
	boolean existsByInstructions( String instructions );
	boolean existsByName( String name );
	
	
}

