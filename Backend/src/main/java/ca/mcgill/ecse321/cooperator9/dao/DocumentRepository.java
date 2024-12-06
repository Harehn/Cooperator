package ca.mcgill.ecse321.cooperator9.dao;

import java.util.List;

import ca.mcgill.ecse321.cooperator9.model.Document;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;



/**
 * 
 * @author Yizhong
 * @editor Edwin
 * 	Last updated on 2019-03-17:36 to have the RepositoryRestResource annotation
 *
 */
@RepositoryRestResource( collectionResourceRel = "documents", path = "documents" )
public interface DocumentRepository extends CrudRepository<Document, Long> {
	
	//Methods based on Domain Model
	Document findDocumentByDocid( long id );
	List<Document> findDocumentsByUri( String uri );
	List<Document> findDocumentsByDescription( String description );
	List<Document> findDocumentsByInstructions( String instructions );
	List<Document> findDocumentsByName( String name );

	//Extra functionalities
	boolean existsByDocid( long id );
	boolean existsByUri( String uri  );
	boolean existsByDescription( String description );
	boolean existsByInstructions( String instructions );
	boolean existsByName( String name );



	
}
