package ca.mcgill.ecse321.cooperator9.dao;

import ca.mcgill.ecse321.cooperator9.model.EmploymentContract;
import ca.mcgill.ecse321.cooperator9.model.VerificationState;
import ca.mcgill.ecse321.cooperator9.model.EmployeeProfile;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 
 * @author Yizhong
 * @editor Edwin
 * 	Most recent edit comprised of adding RepositoryRestResource annotation on 2019-03-02-17:39
 *
 */
@RepositoryRestResource( collectionResourceRel = "contracts", path = "contracts" )
public interface EmploymentContractRepository extends CrudRepository<EmploymentContract, Long> {
	
	//Methods based on Domain Model
	EmploymentContract findEmploymentContractByDocid( long id );
	List<EmploymentContract> findEmploymentContractsByVerified( VerificationState verified );
	List<EmploymentContract> findEmploymentContractsByUri( String uri );
	List<EmploymentContract> findEmploymentContractsByDescription( String description );
	List<EmploymentContract> findEmploymentContractsByInstructions( String instructions );
	List<EmploymentContract> findEmploymentContractsByName( String name );
	
	//Extra functionalities
	boolean existsByDocid( long id );
	boolean existsByVerified( VerificationState verified );
	boolean existsByUri( String uri  );
	boolean existsByDescription( String description );
	boolean existsByInstructions( String instructions );
	boolean existsByName( String name );
	
	
}
