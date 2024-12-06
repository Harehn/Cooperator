package ca.mcgill.ecse321.cooperator9.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.cooperator9.model.Student;
import ca.mcgill.ecse321.cooperator9.model.EmployeeProfile;

/**
 * 
 * @author Agent-Bennette (Edwin Pan)
 * Last updated on 2019-03-02-17:44 for adding the RepositoryRestResource annotation.
 *
 */
@RepositoryRestResource( collectionResourceRel = "students", path = "students")
public interface StudentRepository extends CrudRepository<Student,Long> {

	//Methods based on Domain Model
	
	List<Student> findStudentByFirstName( String firstName );
	
	List<Student> findStudentByLastName( String lastName );
	
	Student findStudentById( long id );
	
	//Extra functionalities
	
	boolean existsByFirstName( String firstName );
	boolean existsByLastName( String lastName );
	boolean existsById( long id );
	//no boolean related function for findByEmployeeProfile since an employeeProfile cannot exist without a student.
	
}
