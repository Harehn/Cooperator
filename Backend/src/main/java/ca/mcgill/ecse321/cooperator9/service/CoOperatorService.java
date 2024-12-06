package ca.mcgill.ecse321.cooperator9.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ca.mcgill.ecse321.cooperator9.model.AttendanceStatus;
import ca.mcgill.ecse321.cooperator9.model.CoOperatorUser;
import ca.mcgill.ecse321.cooperator9.model.Student;
import ca.mcgill.ecse321.cooperator9.model.Employer;
import ca.mcgill.ecse321.cooperator9.model.EmployeeProfile;
import ca.mcgill.ecse321.cooperator9.model.SocialEvent;
import ca.mcgill.ecse321.cooperator9.model.Document;
import ca.mcgill.ecse321.cooperator9.model.EmploymentContract;
import ca.mcgill.ecse321.cooperator9.model.EmploymentStatus;
import ca.mcgill.ecse321.cooperator9.model.Invitation;
import ca.mcgill.ecse321.cooperator9.model.LoginCredentials;
import ca.mcgill.ecse321.cooperator9.model.StudentEvaluationForm;
import ca.mcgill.ecse321.cooperator9.model.TaxForm;
import ca.mcgill.ecse321.cooperator9.model.VerificationState;
import ca.mcgill.ecse321.cooperator9.dao.CoOperatorUserRepository;
import ca.mcgill.ecse321.cooperator9.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator9.dao.EmployerRepository;
import ca.mcgill.ecse321.cooperator9.dao.EmployeeProfileRepository;
import ca.mcgill.ecse321.cooperator9.dao.SocialEventRepository;
import ca.mcgill.ecse321.cooperator9.dao.DocumentRepository;
import ca.mcgill.ecse321.cooperator9.dao.EmploymentContractRepository;
import ca.mcgill.ecse321.cooperator9.dao.InvitationRepository;
import ca.mcgill.ecse321.cooperator9.dao.LoginCredentialsRepository;
import ca.mcgill.ecse321.cooperator9.dao.StudentEvaluationFormRepository;
import ca.mcgill.ecse321.cooperator9.dao.TaxFormRepository;


/**
 * 
 * @author Agent-Bennette (Edwin Pan)
 * @author Sage
 * @author Yizhong
 * 
 * Implementation of CRUD Repositories to create CRUD database services. A Service class integrated into Spring Boot.
 * 
 * I wonder if anyone will notice that the code written in here and in CoOperatorRepository was manually written...
 *
 */
@Service
public class CoOperatorService {
	
	/*
	 * ==================================================================================================================
	 * ==================================================================================================================
	 * 
	 * 	REPOSITORIES
	 * 		All @Autowired repositories here.
	 * 
	 * ==================================================================================================================
	 * ==================================================================================================================
	 */
	
	@Autowired
	CoOperatorUserRepository coOperatorUserRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	EmployerRepository employerRepository;
	
	@Autowired
	EmployeeProfileRepository employeeProfileRepository;
	
	@Autowired
	SocialEventRepository socialEventRepository;
	
	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	EmploymentContractRepository employmentContractRepository;
	
	@Autowired
	StudentEvaluationFormRepository studentEvaluationFormRepository;
	
	@Autowired
	TaxFormRepository taxFormRepository;
		
	@Autowired
	InvitationRepository invitationRepository;
	
	@Autowired
	LoginCredentialsRepository loginCredentialsRepository;
	
	
	/*
	 * ==================================================================================================================
	 * ==================================================================================================================
	 * 
	 * 	HELPER METHODS
	 * 		These methods are necessary for a lot of filtering functionality.
	 * 
	 * ==================================================================================================================
	 * ==================================================================================================================
	 */
	
	/**
	 * helper toList method: Turns Iterable<T> CRUDRepository output into an ArrayList
	 * @author McGillECSE321W2019 Tutorial
	 * @param 	iterable
	 * @return	arrayList
	 */
	public static <T> List<T> toList( Iterable<T> iterable ){
		List<T> resultList = new ArrayList<T>();
		for( T t : iterable ) resultList.add(t);
		return resultList;
	}

	/**
	 * helper getIntersect method: Takes two samples and returns objects that are pointed to by both lists.
	 * @param 	sampleSpaceA	as a List; and
	 * @param 	sampleSpaceB	as a List.
	 * @return	intersectAB		as a List.
	 */
	public static <T> List<T> getIntersect ( List<T> sampleSpaceA, List<T> sampleSpaceB ){
		ArrayList<T> intersectAB = new ArrayList<T>();
		for( T e1 : sampleSpaceA ) {
			for( T e2 : sampleSpaceB ) {
				//If the firstName and lastName employer objects point to the same object, then we found a match.
				if( e1.equals(e2) )	intersectAB.add(e1);
			}
		}
		return intersectAB;
	}
	
	/**
	 * helper getUnion method: Takes two samples and returns the combined lists without repeating any objects.
	 * @param	sampleSpaceA	as a list;
	 * @param	sampleSpaceB	as a list; and
	 * @return	unionAB			as a list.
	 */
	public static <T> List<T> getUnion ( List<T> sampleSpaceA , List<T> sampleSpaceB ){
		ArrayList<T> unionAB = new ArrayList<T>();
		for( T newElement : sampleSpaceA )
			unionAB.add(newElement);
		for( T newElement : sampleSpaceB ) {
			boolean unique = true;
			for( T oldElement : sampleSpaceA )
				if( oldElement.equals(newElement) )
					unique = false;
			if (unique)
				unionAB.add(newElement);
					
		}
		return unionAB;
	}
	
	
	
	/*
	 * ==================================================================================================================
	 * ==================================================================================================================
	 * 
	 * 	SERVICES
	 * 		All methods which accomplish something through the database are found here.
	 * 
	 * ==================================================================================================================
	 * ==================================================================================================================
	 */
	
	
	
	//===================================================================================================================
	// SERVICES - coOperatorUserRepository Services - user>student>employer>profile>event>doc>contract>eval>tax
	//===================================================================================================================
	
	/* No CoOperatorUsers are created; only its subclasses. */
	
	/**
	 * getCoOperatorUser method: obtains a list of CoOperatorUser's with the name. Returns matches for in both first and last names.
	 * @param 	name				as a String; and
	 * @return	matchedCoOperatorUsers	as a List of CoOperatorUser's.
	 */
	@Transactional
	public List<CoOperatorUser> getCoOperatorUsers( String name ) {
		return getUnion( coOperatorUserRepository.findCoOperatorUsersByFirstName(name) , coOperatorUserRepository.findCoOperatorUsersByLastName(name) );
	}
	/**
	 * getCoOperatorUser method: obtains a list of CoOperatorUser's with the given first and last names.
	 * @param 	firstName				as a String; and
	 * @param 	lastName				as a String.
	 * @return	matchedCoOperatorUsers	as a List of CoOperatorUser's.
	 */
	@Transactional
	public List<CoOperatorUser> getCoOperatorUsers( String firstName, String lastName ){
		List<CoOperatorUser> FirstNameMatches = coOperatorUserRepository.findCoOperatorUsersByFirstName(firstName);
		List<CoOperatorUser> LastNameMatches = coOperatorUserRepository.findCoOperatorUsersByLastName(lastName);
		List<CoOperatorUser> matches = getIntersect(FirstNameMatches, LastNameMatches);
		return matches;
	}
	/**
	 * getAllCoOperatorUsers method: obtains a list of all CoOperatorUser's in the database.
	 * @return	allCoOperatorUsers	as a List of CoOperatorUser's.
	 */
	@Transactional
	public List<CoOperatorUser> getAllCoOperatorUsers() {
		return toList( coOperatorUserRepository.findAll() ) ;
	}
	
	
	//===================================================================================================================
	// SERVICES - studentRepository Services - user>student>employer>profile>event>doc>contract>eval>tax
	//===================================================================================================================
	
	//SETTERS
	/**
	 * createStudent method: Creates a new student object in the repository.
	 * @param 	studentId	as a long,
	 * @param	firstName	as a String, and
	 * @param	lastName	as a String;
	 * @return	student, the generated Student object.
	 */
	@Transactional
	public Student createStudent(long studentId, String firstName, String lastName) {
		//Check if the student object already exists in the repository. Throw an error, if so.
		if( studentRepository.existsById( studentId ) )
			throw new IllegalArgumentException("The provided student already exists in the database.");
		
		//Create.
		Student student = new Student();
		student.setId(studentId);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmployeeProfiles(new LinkedHashSet<EmployeeProfile>());
		studentRepository.save(student);
		return student;
	}
	
	//GETTERS
	/**
	 * getStudent method: returns, if found, the Student with the given student ID number.
	 * @param studentId as a long.
	 * @return student as a Student object.
	 */
	@Transactional
	public Student getStudent( long studentId ) {
		return studentRepository.findStudentById(studentId);
	}
	/**
	 * getStudent method: returns a list of students with either their first or last name matching the provided name.
	 * @param name as a String.
	 * @return students as a List of Student objects object.
	 */
	@Transactional
	public List<Student> getStudent( String name ) {
		return getUnion(studentRepository.findStudentByFirstName(name),studentRepository.findStudentByLastName(name));
	}
	/**
	 * getStudent method: returns, if found, a list of students with the provided first and last names.
	 * @param 	firstName as a String; and
	 * @param 	lastName as a String.
	 * @return	students as a List of Students objects object with the exact match.
	 */
	@Transactional
	public List<Student> getStudent( String firstName, String lastName ){
		List<Student> firstNameMatches = studentRepository.findStudentByFirstName(firstName);
		List<Student> lastNameMatches = studentRepository.findStudentByLastName(lastName);
		List<Student> matches = getIntersect(firstNameMatches, lastNameMatches);
		return matches;
	}
	/**
	 * getAllStudents method: returns a list of all students in the repository.
	 * @return students as a List of Student objects object.
	 */
	@Transactional
	public List<Student> getAllStudents() {
		return toList( studentRepository.findAll() );
	}
	
	//DELETER
	/**
	 * Deletes student and all references to it and parts that require it.
	 * @param student
	 */
	@Transactional
	public void deleteStudent(Student student) {
		//Safety
		if(student==null)
			throw new NullPointerException("Cannot delete a nullpointer student!");
		
		//Get the profiles of the student to be deleted
		List<EmployeeProfile> profiles = employeeProfileRepository.findEmployeeProfilesByStudent(student);

		//Deleting references to the profile and then the profile itself
		for( EmployeeProfile p : profiles ) {
			//Sever external reference to this profile
			Set<EmployeeProfile> studentsProfiles = student.getEmployeeProfiles();
			studentsProfiles.remove(p);
			student.setEmployeeProfiles(studentsProfiles);
			Set<EmployeeProfile> employersProfiles = p.getEmployer().getEmployees();
			employersProfiles.remove(p);
			p.getEmployer().setEmployees(employersProfiles);
			//Delete the profile and its associated contract and student evaluation form simultaneously.
			employmentContractRepository.delete(p.getContract());
			studentEvaluationFormRepository.delete(p.getEvaluationOfStudent());
			employeeProfileRepository.delete(p);
		}
		//Delete the student
		studentRepository.delete(student);
	}
	
	
	//===================================================================================================================
	// SERVICES - employerRepository Services - user>student>employer>profile>event>doc>contract>eval>tax
	//===================================================================================================================
	
	/**
	 * createEmployer method: Creates a persisted Employer object based on the name of his or her company, his or
	 * her first and last names; and then returns the created Employer object. This Employer object can later be 
	 * found in coOperatorUserRepository and employerRepository services.
	 * @param 	company name	as a String;
	 * @param 	firstName		as a String; and
	 * @param 	lastName		as a String.
	 * @return	employer as an Employer object.
	 */
	@Transactional
	public Employer createEmployer( String company, String firstName, String lastName ) 
	{
		
		//Check if the would-be-created employer already exists. If so, throw an error.
		if( this.getEmployer(company,firstName,lastName) != null )
			throw new IllegalArgumentException("This employer already exists in the database!");
		
		//Create
		Employer employer = new Employer();
		employer.setCompany(company);
		employer.setFirstName(firstName);
		employer.setLastName(lastName);
		employer.setId( company.hashCode() + firstName.hashCode() + lastName.hashCode() );
		employer.setEmployees(new LinkedHashSet<EmployeeProfile>());
		employer.setInvitations(new LinkedHashSet<Invitation>());
		employerRepository.save(employer);
		coOperatorUserRepository.save(employer);
		return employer;
	}
	
	//GETTERS
	/**
	 * getEmployer method: Returns a list of employers that work for a given company.
	 * @param company name as a String.
	 * @return List object of matched Employer objects.
	 */
	@Transactional
	public List<Employer> getEmployer( String company ) {
		return toList( employerRepository.findEmployersByCompany(company) );
	}
	/**
	 * getEmployer method: Returns a list of employers who have the given first and last names.
	 * @param 	firstName as a String; and
	 * @param 	lastName as a String.
	 * @return	List object of matched Employer objects.
	 */
	@Transactional
	public List<Employer> getEmployer( String firstName , String lastName ) {
		List<Employer> list1 = toList( employerRepository.findEmployersByFirstName(firstName) );
		List<Employer> list2 = toList( employerRepository.findEmployersByLastName(lastName) );
		List<Employer> matches = getIntersect(list1, list2);
		return matches;
	}
	/**
	 * getEmployer method: Returns a list of employers who have a given first and last names, and work at the
	 * 	given company.
	 * @param 	company name 	as a String;
	 * @param 	firstName		as a String; and
	 * @param 	lastName		as a String.
	 * @return	List object of matched Employer objects.
	 */
	@Transactional
	public Employer getEmployer( String company , String firstName , String lastName ) {
		List<Employer> list1 = getEmployer(company);
		List<Employer> list2 = getEmployer(firstName,lastName);
		List<Employer> matches = getIntersect(list1, list2);
		if(matches.size() > 1)
			throw new IllegalStateException("Repository has more than one copy of this employer.");
		if(matches.size() == 0 )
			return null;
		return matches.get(0);
	}
	/**
	 * getEmployer method: Returns the specific employer of a given employee profile.
	 * @param employeeProfile as an object of class EmployeeProfile.
	 * @return the specific employer in that profile as an Employer object.
	 */
	@Transactional
	public Employer getEmployer( EmployeeProfile employeeProfile ) {
		return employerRepository.findEmployerByEmployees(employeeProfile) ;
	}
	/**
	 * getEmployer method: Returns a list of employers aware of a given social event.
	 * @param socialEvent as an object of class SocialEvent.
	 * @return a List object of Employer Objects of employers that have access to the social event.
	 */
	@Transactional
	public List<Employer> getEmployer( SocialEvent socialEvent ) {
		Set<Invitation> invitations = socialEvent.getInvitations();
		List<Employer> employers = new ArrayList<Employer>();
		for( Invitation invitation : invitations ) {
			employers.add( invitation.getEmployer() );
		}
		return employers;
	}
	/**
	 * getEmployer method: Returns the employer of an invitation.
	 * @param invitation as an object of class Invitation.
	 * @return the employer as an object of class Employer.
	 */
	@Transactional
	public Employer getEmployer( Invitation invitation ) {
		return employerRepository.findEmployerByInvitations(invitation);
	}
	/**
	 * getAllEmployers method: Returns a List of all employers in the database.
	 * @return List object of all Employer objects.
	 */
	@Transactional
	public List<Employer> getAllEmployers( ) {
		return toList( employerRepository.findAll() );
	}
	
	//DELETER
	/**
	 * Deletes an employer and all the cascading related components. Namely:
	 * 	Employer, EmployeeProfile, EmploymentContract, StudentEvaluationForm, Invitation;
	 * 	and all the references to those. Refer to the UML Diagram.
	 * @param employer
	 */
	@Transactional
	public void deleteEmployer(Employer employer) {
		//Safety check
		if(employer==null)
			throw new NullPointerException("Cannot delete nullpointer employer.");
		
		//There are two sets of graphs of things that need to be deleted for this employer delete:
		//The first set of graphs are those which start with the EmployeeProfile's, and extend to
		//	the students' references to those profiles and the contracts and student evaluation forms.
		//The second set of graph are those which start with the invitations, and only need to also
		//	delete all the references to these invitations in socialEvents.
		
		//Delete employeeProfile tree
		for( EmployeeProfile p : employer.getEmployees() ) {
			this.deleteEmployeeProfile(p);
		}
		//Delete invitation tree
		for( Invitation i : employer.getInvitations() ) {
			this.deleteInvitation(i);
		}
		
		//Now we can delete the employer itself.
		employerRepository.delete(employer);
		
	}
	
	//===================================================================================================================
	// SERVICES - employeeProfileRepository Services - user>student>employer>profile>event>doc>contract>eval>tax
	//===================================================================================================================
	
	//SETTERS
	/**
	 * createEmployeeProfile method: Creates and returns a persisted EmployeeProfile based on the student and employer involved, 
	 * 	as well as the required contract for proof.
	 * @param 	student			as a Student object;
	 * @param 	employer		as an Employer object;
	 * @param 	contract		as a EmploymentContract object; and
	 * @return	profile			as the persisted EmployeeProfile object.
	 */
	@Transactional
	public EmployeeProfile createEmployeeProfile(Student student, Employer employer, EmploymentContract contract,
			LocalDate startDate, LocalDate endDate ) {
		
		//Check to make sure that this employeeProfile does not already exist.
		List<EmployeeProfile> previousEmployeeProfiles = this.getEmployeeProfiles(student,employer);
		for( EmployeeProfile profile : previousEmployeeProfiles )
			if( profile.getStartDate().equals(startDate) )
				throw new IllegalArgumentException("This employee profile already exists!");
		
		//Make the employeeProfile
		EmployeeProfile employeeProfile = new EmployeeProfile();
		employeeProfile.setStudent(student);
		employeeProfile.setEmployer(employer);
		employeeProfile.setContract(contract);
		employeeProfile.setId(student.getId()+employer.getId());	//Primary Key!
		employeeProfile.setStartDate(startDate);
		employeeProfile.setEndDate(endDate);
		employeeProfile.setStatus(EmploymentStatus.ACTIVE);
		employeeProfileRepository.save(employeeProfile);
		
		//employer must have this employeeProfile added to its list of employeeProfiles.
		Set<EmployeeProfile> employeesOfEmployer = employer.getEmployees();
		employeesOfEmployer.add(employeeProfile);
		employer.setEmployees(employeesOfEmployer);
		
		//student should be aware that they now have an associated employeeProfile
		Set<EmployeeProfile> profilesOfStudent = student.getEmployeeProfiles();
		profilesOfStudent.add(employeeProfile);
		student.setEmployeeProfiles(profilesOfStudent);
		
		//done.
		return employeeProfile;
	}
	/**
	 * setEmploymentStatus method: Changes the employmentStatus of the provided EmployeeProfile.
	 * @param profile	as an EmployeeProfile object;
	 * @param status	as an EmployementStatus enum; and
	 * @return success	as a boolean.
	 */
	@Transactional
	public boolean setEmploymentStatus( EmployeeProfile profile, EmploymentStatus status ) {
		//First, make sure that this profile can still be found by something that is unique to itself.
		if( profile != employeeProfileRepository.findEmployeeProfileByContract(profile.getContract()) ) {
			return false;
		}
		//Now that it is confirmed to be the same, change the status accordingly.
		profile.setStatus(status);
		//Success.
		return true;
	}
	/**
	 * setEmploymentContract method: Allows the student to re-submit their EmploymentContract.
	 * @param profile	as an EmployeeProfile object;
	 * @param contract	as an EmploymentContract object; and
	 * @return success	as a boolean.
	 */
	@Transactional
	public boolean setEmploymentContract( EmployeeProfile profile, EmploymentContract contract ) {
		//First, make sure that this profile can still be found by something that is unique to itself.
		if( profile != employeeProfileRepository.findEmployeeProfileByContract(profile.getContract()) ) {
			return false;
		}
		//Now that it is confirmed to be the same, re-submit the contract accordingly.
		profile.setContract(contract);
		//Success.
		return true;
	}
	/**
	 * setStudentEvaluationForm method: Sets the studentEvaluationForm of an EmployeeProfile
	 * @param profile	as an EmployeeProfile object;
	 * @param evalForm	as a StudentEvaluationForm object; and
	 * @return success	as a boolean
	 */
	@Transactional
	public boolean setStudentEvaluationForm( EmployeeProfile profile, StudentEvaluationForm evalForm ) {
		//First, make sure that this profile can still be found by something that is unique to itself.
		if( profile != employeeProfileRepository.findEmployeeProfileByContract(profile.getContract()) ) {
			return false;
		}
		//Now that it is confirmed to be the same, set the evalForm.
		profile.setEvaluationOfStudent(evalForm);
		//Success.
		return true;
	}
	
	//GETTERS
	/**
	 * getEmployeeProfile method: Returns the EmployeeProfile associated to a particular contract.
	 * @param 	contract		as an EmploymentContract object; and
	 * @return	EmployeeProfile	as an EmployeeProfile object.
	 */
	@Transactional
	public EmployeeProfile getEmployeeProfile( EmploymentContract contract) {
		return employeeProfileRepository.findEmployeeProfileByContract(contract);
	}
	/**
	 * getEmployeeProfile method: Returns the EmployeeProfile associated to a particular student evaluation.
	 * @param 	evaluation		as a StudentEvaluationForm object; and
	 * @return	EmployeeProfile	as an EmployeeProfile object.
	 */
	@Transactional
	public EmployeeProfile getEmployeeProfile( StudentEvaluationForm evaluation) {
		return employeeProfileRepository.findEmployeeProfileByEvaluationOfStudent(evaluation);
	}
	/**
	 * getEmployeeProfile method: Returns a list of associated employee profiles between a student and employer.
	 * @param 	student			as a Student object;
	 * @param 	employer		as an Employer object; and
	 * @return	EmployeeProfile	as an EmployeeProfile object.
	 */
	@Transactional
	public List<EmployeeProfile> getEmployeeProfiles( Student student , Employer employer ) {
		return employeeProfileRepository.findEmployeeProfilesByStudentAndEmployer(student, employer);
	}
	/**
	 * getEmployeeProfile method: Returns a list of employee profiles of a student.
	 * @param 	student			as a Student object; and
	 * @return	EmployeeProfile	as an EmployeeProfile object.
	 */
	@Transactional
	public List<EmployeeProfile> getEmployeeProfiles( Student student ) {
		return employeeProfileRepository.findEmployeeProfilesByStudent(student);
	}
	/**
	 * getEmployeeProfile method: Returns a list of employee profiles of an employer's student employees.
	 * @param 	employer		as an Employer object; and
	 * @return	EmployeeProfile	as an EmployeeProfile object.
	 */
	@Transactional
	public List<EmployeeProfile> getEmployeeProfiles( Employer employer ) {
		return employeeProfileRepository.findEmployeeProfilesByEmployer(employer);
	}
	/**
	 * getAllEmployeesProfiles method: Returns a list of all employee profiles in the database.
	 * @return	List<EmployeeProfile>
	 */
	@Transactional
	public List<EmployeeProfile> getAllEmployeeProfiles( ) {
		return toList( employeeProfileRepository.findAll() );
	}
	
	//DELETER
	/**
	 * Delete employee profiles along with all references to them and their contained
	 * contract and student evaluation forms.
	 * @param profile
	 */
	@Transactional
	public void deleteEmployeeProfile(EmployeeProfile profile) {
		//Reference to profile in students
		Set<EmployeeProfile> studentsProfiles = profile.getStudent().getEmployeeProfiles();
		studentsProfiles.remove(profile);
		profile.getStudent().setEmployeeProfiles(studentsProfiles);
		//Reference to profile in employer
		Set<EmployeeProfile> employersProfiles = profile.getEmployer().getEmployees();
		employersProfiles.remove(profile);
		profile.getEmployer().setEmployees(employersProfiles);
		//Contracts and Evaluation Forms contained
		this.deleteEmploymentContract(profile.getContract());
		this.deleteStudentEvaluationForm(profile.getEvaluationOfStudent());
		//The profile itself
		employeeProfileRepository.delete(profile);
	}
	
	//===================================================================================================================
	// SERVICES - socialEventRepository Services - user>student>employer>profile>event>doc>contract>eval>tax
	//===================================================================================================================
	
	//SETTERS
	/**
	 * createSocialEvent method: Creates a persisted social event object. Requires all descriptors of the event.
	 * @param 	name		as a String object;
	 * @param 	description	as a String object;
	 * @param 	location	as a String object;
	 * @param 	startDate	as a java.time.LocalDate object;
	 * @param 	startTime	as a java.time.LocalTime object;
	 * @param 	endDate		as a java.time.LocalDate object;
	 * @param 	endTime		as a java.time.LocalTime object; and
	 * @return	newEvent	as a SocialEvent object.
	 */
	@Transactional
	public SocialEvent createSocialEvent(String name, String description, String location, 
			LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime ) {
		
		//Create
		SocialEvent event = new SocialEvent();
		event.setName(name);
		event.setDescription(description);
		event.setLocation(location);
		event.setStartDate(startDate);
		event.setStartTime(startTime);
		event.setEndDate(endDate);
		event.setEndTime(endTime);
		event.setId(  name.hashCode() + location.hashCode() 
				+ startDate.hashCode() + startTime.hashCode()  );	//Primary Key!
		event.setInvitations(new LinkedHashSet<Invitation>());		
		//Check if the ID of this socialEvent matches on in the repository. If so, then it has already been made.
		if( socialEventRepository.existsById(event.getId()))
			throw new IllegalArgumentException("This social event already exists!");
		
		//Persist
		socialEventRepository.save(event);
		return event;
	}
	
	//GETTERS
	/**
	 * getSocialEvent method: Gets the social event of an associated numeric id.
	 * @param id as a long; and
	 * @return event as a SocialEvent object.
	 */
	@Transactional
	public SocialEvent getSocialEvent( long id ) {
		return socialEventRepository.findSocialEventById(id); 
	}
	/**
	 * getSocialEvent method: Returns the socialEvent associated with the following date:
	 * @param name		as a String
	 * @param location	as a String
	 * @param startDate	as a LocalDate object
	 * @param startTime	as a LocalTime object
	 * @return the SocialEvent object if it exists, or null if it does not; an exception if many are detected.
	 */
	@Transactional
	public SocialEvent getSocialEvent( String name, String location, LocalDate startDate, LocalTime startTime ) {
		List<SocialEvent> matchSet1 = socialEventRepository.findSocialEventsByName(name);
		List<SocialEvent> matchSet2 = socialEventRepository.findSocialEventsByLocation(location);
		List<SocialEvent> matchSet3 = socialEventRepository.findSocialEventsByStartDate(startDate);
		List<SocialEvent> matchSet4 = socialEventRepository.findSocialEventsByStartTime(startTime);
		List<SocialEvent> finalMatchSet = getIntersect( getIntersect(matchSet1,matchSet2),
														getIntersect(matchSet3,matchSet4) );
		if( finalMatchSet.size() > 1 )
			throw new IllegalStateException("Duplicate SocialEvent detected of name " + name + ", location " +
											location + ", and of startDate " + startDate.toString() +
											" and startTime " + startTime.toString() );
		if( finalMatchSet.size() == 0 )
			return null;
		return finalMatchSet.get(0);
	}
	/**
	 * getSocialEvent method: Gets a list of social events with either its name, location, or description matching with a given string.
	 * @param str as a String object; and
	 * @return events as a List<SocialEvent> object.
	 */
	@Transactional
	public List<SocialEvent> getSocialEvents( String str ) {
		List<SocialEvent> matchSet1 = socialEventRepository.findSocialEventsByName(str);
		List<SocialEvent> matchSet2 = socialEventRepository.findSocialEventsByDescription(str);
		List<SocialEvent> matchSet3 = socialEventRepository.findSocialEventsByLocation(str);
		return getUnion( getUnion( matchSet1 , matchSet2 ) , matchSet3 );
	}
	/**
	 * getSocialEvent method: Gets a list of social events with the matching starting date.
	 * @param startDate as a java.time.LocalDate object; and
	 * @return events as a List<SocialEvent> object.
	 */
	@Transactional
	public List<SocialEvent> getSocialEvents( LocalDate startDate ) {
		return socialEventRepository.findSocialEventsByStartDate(startDate);
	}
	/**
	 * getSocialEvent method: Gets a list of social events with the matching starting time.
	 * @param startTime as a java.time.LocalTime object; and
	 * @return events as a List<SocialEvent> object.
	 */
	@Transactional
	public List<SocialEvent> getSocialEvents( LocalTime startTime ) {
		return socialEventRepository.findSocialEventsByStartTime(startTime);
	}
	/**
	 * getSocialEvent method: Gets a list of social events with the matching starting date and time.
	 * @param 	startDate	as a java.time.LocalDate object;
	 * @param 	startTime	as a java.time.LocalTime object; and
	 * @return	events		as a List<SocialEvent> object.
	 */
	@Transactional
	public List<SocialEvent> getSocialEvents( LocalDate startDate , LocalTime startTime ) {
		return getUnion( getSocialEvents(startDate) , getSocialEvents(startTime) );
	}
	/**
	 * getAllSocialEvents method: Gets a list of all social events saved on the database.
	 * @return	events as a List<SocialEvent> object.
	 */
	@Transactional
	public List<SocialEvent> getAllSocialEvents( ) {
		return toList( socialEventRepository.findAll() );
	}
	
	//DELETER
	public void deleteSocialEvent(SocialEvent socialEvent) {
		//Safety
		if(socialEvent==null)
			throw new NullPointerException("socialEvent cannot be null.");
		//Delete the invitations of the social event and the references to that invitation
		for(Invitation i : socialEvent.getInvitations())
			this.deleteInvitation(i);
		//Delete this social event
		socialEventRepository.delete(socialEvent);
	}
	
	
	//===================================================================================================================
	// SERVICES - documentRepository Services - user>student>employer>profile>event>doc>contract>eval>tax
	//===================================================================================================================
	
	//HELPERS
	/**
	 * Completes safety checks and converts MultipartFile to an array of bytes.
	 * @param file
	 * @return byte array
	 * @throws IOException
	 */
	private byte[] toBytes(MultipartFile file) throws IOException {
		//File object safety check
		if( file == null )
			throw new NullPointerException("Received a nullpointer MultipartFile file!");
		if( file.isEmpty() )
			throw new IllegalArgumentException("Received an empty file!");
		//Serialize the file into byte array
		try {
			return file.getBytes();
		} catch (IOException ex) {
			throw ex;
		}
	}
	
	//SETTERS
	/* It is not possible to create a document, as documents are an abstract class. This merely serves to allow
	 * an individual to search up the database of all EmploymentContracts, StudentEvaluationForms, and TaxForms in
	 * one single repository, since they are all subclass implementations of Document.
	 */
	
	//GETTERS
	/**
	 * getDocument method: Returns the associated document in the repository associated to a particular id.
	 * @param 	id 			as a long; and
	 * @return	document	as an object that is a subclass of Document.
	 */
	@Transactional
	public Document getDocument( long id ) {
		return documentRepository.findDocumentByDocid(id);
	}
	/**
	 * getDocuments method: Returns a list of document whose name, desc, filepath, or instructions match the given string.
	 * @param 	str			as a String; and
	 * @return	documents	as a List<Document> object.
	 */
	@Transactional
	public List<Document> getDocuments( String str ) {
		List<Document> matches1 = documentRepository.findDocumentsByName(str);
		List<Document> matches2 = documentRepository.findDocumentsByDescription(str);
		List<Document> matches3 = documentRepository.findDocumentsByUri(str);
		List<Document> matches4 = documentRepository.findDocumentsByInstructions(str);
		return getUnion( getUnion( matches1,matches2 ) , getUnion( matches3,matches4 ) );
	}
	/**
	 * getAllDocuments method: Return a list of all documents saved in the repository.
	 * @return	documents	as a List<Document> object.
	 */
	@Transactional
	public List<Document> getAllDocuments( ) {
		return toList( documentRepository.findAll() );
	}
	
	
	//===================================================================================================================
	// SERVICES - employmentContractRepository Services - user>student>employer>profile>event>doc>contract>eval>tax
	//===================================================================================================================
	
	//SETTERS
	/**
	 * createEmploymentContract method: Creates and returns a persisted employment contract object based on inputs.
	 * @param 	name		of the contract as a String;
	 * @param 	uri			of the contract as a String;
	 * @param	file		of the contract in the form of a MultipartFile object; and
	 * @return	contract	as an EmploymentContract object.
	 * @throws IOException 
	 */
	@Transactional
	public EmploymentContract createEmploymentContract(String name, String uri, MultipartFile file) throws IOException {
		
		//Check to make sure that such a contract does not already exist.
		if( employmentContractRepository.existsByName(name) )
			throw new IllegalArgumentException("An employment contract by the same name already exists!");
		if( employmentContractRepository.existsByUri(uri) )
			throw new IllegalArgumentException("An employment contract with the same uri already exists!");
		
		//Convert file to serialized data
		byte[] fileData = this.toBytes(file);
		
		//Create
		EmploymentContract contract = new EmploymentContract(name,uri,fileData);
		contract.setVerified(VerificationState.UNVERIFIED);
		employmentContractRepository.save(contract);
		documentRepository.save(contract);				//Also saved here for the ability to search through all documents.
		return contract;
	}
	/**
	 * verifyEmploymentContract method: A protected method which sets the verification state of a contract to 
	 * 	verified as long as	the credentials provided are that of the employer.
	 * @param contract	as an EmploymentContract object;
	 * @param authName	as a String;
	 * @param authPass	as a String; and
	 * @return success	as a boolean.
	 */
	@Transactional
	public boolean verifyEmploymentContract( EmploymentContract contract, String authName, String authPass ) {
		//If the authentication fails to match with the owner of the profile
		if( !employeeProfileRepository.findEmployeeProfileByContract(contract).getEmployer().equals( 
				loginCredentialsRepository.findByUsername(authName).attemptAccess(authName, authPass) ) )
			return false;
		contract.setVerified(VerificationState.VERIFIED);
		return true;
	}
	/**
	 * denyEmploymentContract method: A protected method which sets the verification state of a contract to 
	 * 	denied as long as the credentials provided are that of the employer. Also sets the EmploymentStatus of the 
	 * 	associated employeeProfile to denied.
	 * @param contract	as an EmploymentContract object;
	 * @param authName	as a String;
	 * @param authPass	as a String; and
	 * @return success	as a boolean.
	 */
	@Transactional
	public boolean denyEmploymentContract( EmploymentContract contract, String authName, String authPass ) {
		if( !employeeProfileRepository.findEmployeeProfileByContract(contract).getEmployer().equals( 
				loginCredentialsRepository.findByUsername(authName).attemptAccess(authName, authPass) ) )
			return false;
		contract.setVerified(VerificationState.DENIED);
		//Since this contract has been denied, the associated employeeProfile is also denied.
		employeeProfileRepository.findEmployeeProfileByContract(contract).setStatus(EmploymentStatus.DENIED);
		return true;
	}
	
	//GETTERS
	/**
	 * getEmploymentContract method: returns the employment contract associated with a particular employment contract id.
	 * @param id of the employment contract as a long; and
	 * @return contract as an EmploymentContract object.
	 */
	@Transactional
	public EmploymentContract getEmploymentContract( long id ) {
		return employmentContractRepository.findEmploymentContractByDocid(id);
	}
	/**
	 * getEmploymentContracts method: returns a list of employment contracts with matching name, description, filepath, or instructions to the provided string.
	 * @param str the input String to search by; and
	 * @return contracts as a List<EmploymentContract> object.
	 */
	@Transactional
	public List<EmploymentContract> getEmploymentContracts( String str ) {
		List<EmploymentContract> m1 = employmentContractRepository.findEmploymentContractsByName(str);
		List<EmploymentContract> m2 = employmentContractRepository.findEmploymentContractsByUri(str);
		List<EmploymentContract> m3 = employmentContractRepository.findEmploymentContractsByDescription(str);
		List<EmploymentContract> m4 = employmentContractRepository.findEmploymentContractsByInstructions(str);
		return getUnion( getUnion( m1, m2 ) , getUnion( m3,m4 ) );
	}
	/**
	 * getEmploymentContracts method: returns a list of employment contracts based on their verification state.
	 * @param verified as a VerificationState enum; and
	 * @return contracts as a List<EmploymentContract> object.
	 */
	@Transactional
	public List<EmploymentContract> getEmploymentContracts( VerificationState verified ) {
		return employmentContractRepository.findEmploymentContractsByVerified(verified);
	}
	/**
	 * getAllEmploymentContracts method: returns a list of all employment contracts saved on the database.
	 * @return all contracts as a List<EmploymentContract> object.
	 */
	@Transactional
	public List<EmploymentContract> getAllEmploymentContracts() {
		return toList( employmentContractRepository.findAll() );
	}
	
	//DELETER
	/**
	 * Helper method used when deleting the employee profile which owns this contract.
	 * @param contract
	 */
	@Transactional
	private void deleteEmploymentContract(EmploymentContract contract) {
		if(contract==null)
			throw new NullPointerException("contract cannot be null.");
		employeeProfileRepository.findEmployeeProfileByContract(contract).setContract(null);
		employmentContractRepository.delete(contract);
		documentRepository.delete(contract);
	}
	
	
	//===================================================================================================================
	// SERVICES - studentEvaluationFormRepository Services - user>student>employer>profile>event>doc>contract>eval>tax
	//===================================================================================================================
	
	//SETTERS
	/**
	 * createStudentEvaluationForm method: Creates and returns a persisted StudentEvaluationForm based on inputs.
	 * A special case exists if the provided is named "empty_eval_form" that the database lacks.
	 * @param 	name		as a String object;
	 * @param 	uri			as a String object;
	 * @param	file		as a MultipartFile object;
	 * @return	evalForm	as a StudentEvaluationForm object.
	 * @throws IOException 
	 */
	@Transactional
	public StudentEvaluationForm createStudentEvaluationForm(String name, String uri, MultipartFile file) throws IOException {
		
		//Prevent duplication
		//Check to make sure that such a contract does not already exist.
		if( studentEvaluationFormRepository.existsByName(name) )
			throw new IllegalArgumentException("A student evaluation form by the same name already exists!");
		if( studentEvaluationFormRepository.existsByUri(uri) )
			throw new IllegalArgumentException("A student evaluation form with the same uri already exists!");
		
		//Convert file to serialized data
		byte[] fileData = this.toBytes(file);
		
		//Create
		StudentEvaluationForm evalForm = new StudentEvaluationForm(name,uri,fileData);
		studentEvaluationFormRepository.save(evalForm);
		documentRepository.save(evalForm);				//Also saved here for the ability to search through all documents.
		
		//Return
		return evalForm;
	}
	
	//GETTERS
	/**
	 * getStudentEvaluationForm method: Returns the Student Evaluation Form associated to the provided numeric ID.
	 * @param 	id 				as a long, which is a uniquely generated number for the specific evaluation form.
	 * @return	evaluationForm	as a StudentEvaluationForm object.
	 */
	@Transactional
	public StudentEvaluationForm getStudentEvaluationForm( long id ) {
		return studentEvaluationFormRepository.findStudentEvaluationFormByDocid(id);
	}
	/**
	 * getStudentEvaluationForm method: Returns the Student Evaluation Form associated to the provided Employee Profile.
	 * @param 	profile			as an EmployeeProfile object; and
	 * @return	evaluationForm	as a StudentEvaluationForm object.
	 */
	@Transactional
	public StudentEvaluationForm getStudentEvaluationForm( EmployeeProfile profile ) {
		return profile.getEvaluationOfStudent();
	}
	/**
	 * getStudentEvaluationForms method: Returns a list of Student Evaluation Forms which have either their name, description,
	 * 	instructions, or filepath attributes matching the provided string.
	 * @param 	str		as a String; and
	 * @return	forms	as a List<StudentEvaluationForm> object.
	 */
	@Transactional
	public List<StudentEvaluationForm> getStudentEvaluationForms( String str ) {
		List<StudentEvaluationForm> mList1 = studentEvaluationFormRepository.findStudentEvaluationFormsByName(str);
		List<StudentEvaluationForm> mList2 = studentEvaluationFormRepository.findStudentEvaluationFormsByUri(str);
		List<StudentEvaluationForm> mList3 = studentEvaluationFormRepository.findStudentEvaluationFormsByDescription(str);
		List<StudentEvaluationForm> mList4 = studentEvaluationFormRepository.findStudentEvaluationFormsByInstructions(str);
		return getUnion( getUnion(mList1,mList2) , getUnion(mList3,mList4) );
	}
	/**
	 * getAllStudentEvaluationForms method: Returns all of the Student Evaluation Forms in the database.
	 * @return	List<StudentEvaluationForm> object.
	 */
	@Transactional
	public List<StudentEvaluationForm> getAllStudentEvaluationForms( ) {
		return toList( studentEvaluationFormRepository.findAll() );
	}
	
	/**
	 * Deletes the provided studentEvaluationForm and references to it. Special case exists for the form of
	 * name "empty_eval_form".
	 * @param studentEvaluationForm
	 */
	@Transactional
	public void deleteStudentEvaluationForm(StudentEvaluationForm studentEvaluationForm) {
		//Safety
		if(studentEvaluationForm==null)
			throw new NullPointerException("studentEvaluationForm cannot be null.");
		
		//If not a student evaluation form, then it's linked to a student's profile.
		if(!studentEvaluationForm.getName().equals("empty_eval_form"))
			employeeProfileRepository.findEmployeeProfileByEvaluationOfStudent(studentEvaluationForm)
				.setEvaluationOfStudent(null);
		
		studentEvaluationFormRepository.delete(studentEvaluationForm);
		documentRepository.delete(studentEvaluationForm);
	}
	
	
	//===================================================================================================================
	// SERVICES - taxFormRepository Services - user>student>employer>profile>event>doc>contract>eval>tax
	//===================================================================================================================
	
	//SETTERS
	/**
	 * createTaxForm method: Creates and returns tax form based on provided inputs.
	 * @param 	name		as a String;
	 * @param 	uri			as a String;
	 * @param	file		as a MultipartFile object;
	 * @param 	year		as a short; and
	 * @return	taxForm		as a TaxForm object.
	 * @throws IOException 
	 */
	@Transactional
	public TaxForm createTaxForm(String name, String uri, MultipartFile file, short year) throws IOException {
		
		//Prevent duplication
		List<TaxForm> sameNames = taxFormRepository.findByName(name);
		List<TaxForm> sameYears = taxFormRepository.findByYear(year);
		if( getIntersect(sameNames,sameYears).size() != 0 )
			throw new IllegalStateException("Taxform with the same name and or year already exists!");
		
		//Convert file to serialized data
		byte[] fileData = this.toBytes(file);
		
		//Create
		TaxForm taxForm = new TaxForm(name,uri,fileData,year);
		taxFormRepository.save(taxForm);
		documentRepository.save(taxForm);				//Also saved here for the ability to search through all documents.
		return taxForm;
	}
	
	//GETTERS
	/**
	 * getTaxForm method: Returns the tax form document associated with a particular id.
	 * @param 	id		as a long; and
	 * @return	taxform	as a TaxForm object.
	 */
	@Transactional
	public TaxForm getTaxForm( long id ) {
		return taxFormRepository.findByDocid(id);
	}
	/**
	 * getTaxForms method: Returns the list of tax form documents associated for a specific year.
	 * @param 	year		as a short; and
	 * @return	taxForms	as a List<TaxForm> object.
	 */
	@Transactional
	public List<TaxForm> getTaxForms( short year ) {
		return taxFormRepository.findByYear(year);
	}
	/**
	 * getTaxForms method: Returns a list of tax form documents that have either their name, filepath, description
	 * 	or instructions matching up with an input string.
	 * @param 	str			as a String; and
	 * @return	taxForms	as a List<TaxForm> object.
	 */
	@Transactional
	public List<TaxForm> getTaxForms( String str ) {
		List<TaxForm> mList1 = taxFormRepository.findByName(str);
		List<TaxForm> mList2 = taxFormRepository.findByUri(str);
		List<TaxForm> mList3 = taxFormRepository.findByDescription(str);
		List<TaxForm> mList4 = taxFormRepository.findByInstructions(str);
		return getUnion( getUnion(mList1,mList2) , getUnion(mList3,mList4) );
	}
	/**
	 * getAllTaxForms method: Returns a list of all tax form documents in the database.
	 * @return	allTaxForms	as a List<TaxForm> object.
	 */
	@Transactional
	public List<TaxForm> getAllTaxForms( ) {
		return toList( taxFormRepository.findAll() );
	}
	
	//DELETER
	/**
	 * Deletes the provided taxform and references to it.
	 * @param taxForm
	 */
	@Transactional
	public void deleteTaxForm(TaxForm taxForm) {
		if(taxForm==null)
			throw new NullPointerException("taxForm cannot be null.");
		//for(Employer employer : employerRepository.findAll()) {
		//	Set<TaxForm> employersTaxForms = employer.getTaxForms();
		//	employersTaxForms.remove(taxForm);
		//	employer.setTaxForms(employersTaxForms);
		//}
		taxFormRepository.delete(taxForm);
		documentRepository.delete(taxForm);
	}
	
	
	
	//===================================================================================================================
	// SERVICES - loginCredentialsRepository Services - Sprint 2 Functionalities loginCredentials>Invitation
	//===================================================================================================================
	
	//SETTERS
	/**
	 * createLoginCredential method: Creates, but does not return a loginCredential, inside the database.
	 * @param username	credential as a String that is UNIQUE among all other credentials;
	 * @param password	credential as a String; and
	 * @param user		key as a CoOperatorUser object. 
	 */
	@Transactional
	public void createLoginCredential( String username, String password, CoOperatorUser user ) {
		
		//Prevent duplication
		if( usernameExists(username) )
			throw new IllegalArgumentException("Login credential with the same name already exists.");
		
		//Make sure that referred-to Employer does not already have loginCredentials,
		//Making a new loginCredential to an already existing would essentially be hacking
		if (this.userHasLoginCredentials(user) )
			throw new IllegalArgumentException("User already has login credentials.");
		
		//Ensure that the username and password are not the same.
		if( username.equals(password) )
			throw new IllegalArgumentException("Username and Password cannot be the same.");
		
		//Create
		LoginCredentials loginCredentials = new LoginCredentials(user,username,password);
		loginCredentialsRepository.save(loginCredentials);
	}
	@Transactional
	public boolean changeLoginCredential( String username, String oldPassword,
			String newPassword1, String newPassword2 ) {
		LoginCredentials loginCred = loginCredentialsRepository.findByUsername(username);
		if(loginCred == null)
			throw new IllegalArgumentException();
		if( loginCred.changePassword(username, oldPassword, newPassword1, newPassword2) ) {
			loginCredentialsRepository.save(loginCred);
			return true;
		}
		return false;
	}
	
	//GETTERS
	/**
	 * checkUsername method: Checks if the username exists within the database as a credential.
	 * @param username	credentials as a String; and
	 * @return	exists	state as a boolean.
	 */
	@Transactional
	public boolean usernameExists( String username ) {
		LoginCredentials loginCredential = loginCredentialsRepository.findByUsername(username);
		if ( loginCredential != null)
			return true;
		else
			return false; 
	}
	/**
	 * HELPER userHasLoginCredentials method: Checks if the provided CoOperatorUser already has
	 * 	a set of LoginCredentials
	 * @param user as a CoOperatorUser object
	 * @return boolean for whether or not that CoOperatorUser already has loginCredentials.
	 */
	@Transactional
	private boolean userHasLoginCredentials( CoOperatorUser user ) {
		List<LoginCredentials> allCredentials = toList( loginCredentialsRepository.findAll() );
		for( LoginCredentials creds : allCredentials )
			if( creds.whoAreYou( LoginCredentials.AUTHENTICATION ).equals(user) )
				return true;
		return false;
	}
	/**
	 * getKey method; Returns the CoOperatorUser object if the login credentials exist. Returns null at failure.
	 * 					Having a valid CoOperatorUser object is one of the necessary things for being able to find
	 * 					all relevant documents within a repository to a particular user. Without one, the searches
	 * 					should fail. As such, we use this as one security measure.
	 * @param username	as a String;
	 * @param password	as a String; and
	 * @return user		as a CoOperatorUser object.
	 * Note that we should ensure that data passed between Frontend and Backend  by this method is encrypted.
	 * 	In fact, The LoginCredentials class should itself hold a string that is already encrypted. Research needs
	 * 	to be done to figure out how to set up an encryption system, though.
	 * We will discuss this security issue further later.
	 */
	@Transactional
	public CoOperatorUser getKey( String username, String password ) {
		LoginCredentials loginCredential = loginCredentialsRepository.findByUsername(username);
		if (loginCredential == null)
			return null; 
		else {
			return loginCredential.attemptAccess(username, password);
		}
	}
	
	//DELETER
	/**
	 * Deletes login credentials of username and password if the user it is associated to no longer exists.
	 * @param username
	 * @param password
	 */
	@Transactional
	public void deleteLoginCredentials( String username, String password ) {
		//Check the validity of the username and password
		LoginCredentials loginCredentials = loginCredentialsRepository.findByUsername(username);
		if(loginCredentials==null)
			throw new IllegalArgumentException("username does not exist.");
		if(!loginCredentials.isCredentials(username, password))
			throw new IllegalArgumentException("password is not valid.");
		//Check if the user still exists
		if(loginCredentials.attemptAccess(username, password)!=null)
			throw new IllegalArgumentException("Refused. Will not delete logincredentials to existing user.");
		//Since the user does not exist, delete these credentials.
		loginCredentialsRepository.delete(loginCredentials);
	}
	
	
	
	//===================================================================================================================
	// SERVICES - invitationRepository Services - Sprint 2 Functionalities loginCredentials>Invitation
	//===================================================================================================================
	
	//SETTERS
	/**
	 * createInvitation method: Creates and persists an Invitation object based on the Employer and SocialEvent
	 * 	objects provided.
	 * @param employer		as an Employer object;
	 * @param socialEvent	as a SocialEvent object; and
	 * @return invitation	as an Invitation object.
	 */
	@Transactional
	public Invitation createInvitation( Employer employer, SocialEvent socialEvent ) {
		
		//Prevent duplication
		if( this.getInvitation(employer, socialEvent) != null )
			throw new IllegalArgumentException("This invitation already exists!");
		
		//Create the social event
		Invitation invitation = new Invitation();
		invitation.setEmployer(employer);
		invitation.setSocialEvent(socialEvent);
		invitation.setId(socialEvent.getId()+employer.getId());
		invitation.setStatus(AttendanceStatus.UNKNOWN);
		invitationRepository.save(invitation);
		
		//employer must be made aware of its new invitation
		Set<Invitation> invitationsToEmployer = employer.getInvitations();
		invitationsToEmployer.add(invitation);
		employer.setInvitations(invitationsToEmployer);
		
		//socialEvent must be aware of its new invitation
		Set<Invitation> invitationsToSocialEvent = socialEvent.getInvitations();
		invitationsToSocialEvent.add(invitation);
		socialEvent.setInvitations(invitationsToSocialEvent);
		
		//We must officialize these new awarenesses
		employerRepository.save(employer);
		socialEventRepository.save(socialEvent);
		
		//All done.
		return invitation;
	}
	
	@Transactional
	public boolean setAttendanceStatus( Invitation invitation , AttendanceStatus intention ) {
		if( !invitation.equals( invitationRepository.findById(invitation.getId()) ) )
			return false;
		invitation.setStatus(intention);
		return true;
	}
	
	//GETTERS
	/**
	 * getInvitation method: Returns an invitation to a provided SocialEvent to a provided Employer.
	 * @param employer		as an Employer object;
	 * @param socialEvent	as a SocialEvent object; and
	 * @return invitation	as an Invitation object.
	 */
	@Transactional
	public Invitation getInvitation( Employer employer, SocialEvent socialEvent ) {
		List<Invitation> list1 = toList( invitationRepository.findByEmployer(employer) );
		List<Invitation> list2 = toList( invitationRepository.findBySocialEvent(socialEvent) );
		List<Invitation> matches = getIntersect(list1, list2);
		if (matches.isEmpty()) {
			return null;
		}
		else
			return matches.get(0);
	}
	/**
	 * getInvitations method: Returns a list of invitations that an Employer has received.
	 * @param employer		as an Employer object; and
	 * @return invitations	as a List<Invitation> object.
	 */
	@Transactional
	public List<Invitation> getInvitations( Employer employer ){
		return invitationRepository.findByEmployer(employer);
	}
	/**
	 * getInvitations method: Returns a list of invitations to a SocialEvent.
	 * @param socialEvent	as a SocialEvent object; and
	 * @return invitations	as a List<Invitation> object.
	 */
	@Transactional
	public List<Invitation> getInvitations( SocialEvent socialEvent ) {
		return invitationRepository.findBySocialEvent(socialEvent);
	}
	/**
	 * getAllInvitations method: Returns a list of all invitations saved in the repository.
	 * @return allInvitations as a List<Invitation> object.
	 */
	@Transactional
	public List<Invitation> getAllInvitations() {
		return toList( invitationRepository.findAll() );
	}
	
	//DELETER
	@Transactional
	public void deleteInvitation(Invitation invitation) {
		//Reference to invitation in social events
		Set<Invitation> socialEventInvitations = invitation.getSocialEvent().getInvitations();
		socialEventInvitations.remove(invitation);
		invitation.getSocialEvent().setInvitations(socialEventInvitations);
		//Reference to invitation in employers
		Set<Invitation> employersInvitations = invitation.getEmployer().getInvitations();
		employersInvitations.remove(invitation);
		invitation.getEmployer().setInvitations(employersInvitations);
		//The invitation itself
		invitationRepository.delete(invitation);
	}
	
}
