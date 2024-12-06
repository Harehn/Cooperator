package ca.mcgill.ecse321.cooperator9.dao;
import ca.mcgill.ecse321.cooperator9.model.StudentEvaluationForm;
import ca.mcgill.ecse321.cooperator9.model.EmployeeProfile;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 
 * @author Yizhong
 * @editor Edwin
 * 	Most recent edit made on 2019-03-02-17:43 where the RepositoryRestResource annotation was added.
 *
 */
@RepositoryRestResource( collectionResourceRel = "studentevaluationforms", path = "studentevaluationforms" )
public interface StudentEvaluationFormRepository extends CrudRepository<StudentEvaluationForm, Long>{
	
	//Methods based on Domain Model
	StudentEvaluationForm findStudentEvaluationFormByDocid( long id );
	List<StudentEvaluationForm> findStudentEvaluationFormsByUri( String uri );
	List<StudentEvaluationForm> findStudentEvaluationFormsByDescription( String description );
	List<StudentEvaluationForm> findStudentEvaluationFormsByInstructions( String instructions );
	List<StudentEvaluationForm> findStudentEvaluationFormsByName( String name );
	
	
	//Extra functionalities
	boolean existsByDocid( long id );
	boolean existsByUri( String uri  );
	boolean existsByDescription( String description );
	boolean existsByInstructions( String instructions );
	boolean existsByName( String name );

		
	
}

