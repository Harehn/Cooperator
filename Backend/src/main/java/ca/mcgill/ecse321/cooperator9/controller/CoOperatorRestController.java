package ca.mcgill.ecse321.cooperator9.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ca.mcgill.ecse321.cooperator9.service.*;
import ca.mcgill.ecse321.cooperator9.util.BinaryTree;
import ca.mcgill.ecse321.cooperator9.util.InputSanitizer;
import ca.mcgill.ecse321.cooperator9.dao.DocumentRepository;
import ca.mcgill.ecse321.cooperator9.dao.EmployeeProfileRepository;
import ca.mcgill.ecse321.cooperator9.dao.EmployerRepository;
import ca.mcgill.ecse321.cooperator9.dao.EmploymentContractRepository;
import ca.mcgill.ecse321.cooperator9.dao.InvitationRepository;
import ca.mcgill.ecse321.cooperator9.dao.LoginCredentialsRepository;
import ca.mcgill.ecse321.cooperator9.dao.SocialEventRepository;
import ca.mcgill.ecse321.cooperator9.dao.StudentEvaluationFormRepository;
import ca.mcgill.ecse321.cooperator9.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator9.dao.TaxFormRepository;
import ca.mcgill.ecse321.cooperator9.dto.EmployeeProfileDescriptors;
import ca.mcgill.ecse321.cooperator9.dto.EmployerDto;
import ca.mcgill.ecse321.cooperator9.dto.EmployerLoginDTO;
import ca.mcgill.ecse321.cooperator9.dto.EmploymentContractDescriptors;
import ca.mcgill.ecse321.cooperator9.dto.InvitationDescriptors;
import ca.mcgill.ecse321.cooperator9.dto.StudentEvaluationFormDescriptors;
import ca.mcgill.ecse321.cooperator9.dto.TaxFormDescriptors;
import ca.mcgill.ecse321.cooperator9.model.AttendanceStatus;
import ca.mcgill.ecse321.cooperator9.model.CoOperatorUser;
import ca.mcgill.ecse321.cooperator9.model.EmployeeProfile;
import ca.mcgill.ecse321.cooperator9.model.Employer;
import ca.mcgill.ecse321.cooperator9.model.EmploymentContract;
import ca.mcgill.ecse321.cooperator9.model.EmploymentStatus;
import ca.mcgill.ecse321.cooperator9.model.Invitation;
import ca.mcgill.ecse321.cooperator9.model.SocialEvent;
import ca.mcgill.ecse321.cooperator9.model.Student;
import ca.mcgill.ecse321.cooperator9.model.StudentEvaluationForm;
import ca.mcgill.ecse321.cooperator9.model.TaxForm;

/**
 * =============================================================================================================
 * =============================================================================================================
 * ||
 * ||	READ THIS FIRST BEFORE WORKING ON THIS CLASS
 * ||
 * ||	DEVNOTES:
 * ||
 * ||	The CRUD Repositories found in ca.mcgill.ecse321.cooperator9.dao have already been annotated as
 * ||		RepositoryRestResources, which means that every method in each of those repositories is
 * ||		already accessible as an http-addressable service. If you are writing something that is a wrapper
 * ||		for those, you can stop here.
 * ||	This class exists because those methods can't do everything in a convenient, singular package.
 * ||		It doesn't take advantage of any of the more advanced pieces of functionality provided by
 * ||		CoOperatorServices.java, which provides a set of CREATE methods that take into consideration the need
 * ||		for related objects to be updated upon a new created object (Consider how a new EmployeeProfile must
 * ||		be created as a new entity, and also as something immediately afterwards tracked by its
 * ||		student and employer. This is something CoOperatorService.createEmployer already takes care of.
 * ||
 * ||	Finally, if it works, don't touch it.
 * ||
 * =============================================================================================================
 * =============================================================================================================
 */

/**
 * 
 * @author Agent-Bennette (Edwin)
 *
 */
@CrossOrigin()
@RestController
@RequestMapping
public class CoOperatorRestController{
	
	/**
	 * =============================================================================================================
	 * 
	 * 	AUTOWIRED COMPONENTS
	 * 
	 * =============================================================================================================
	 */
	
	@Autowired
	HttpServletRequest httpRequestServlet;
	
	@Autowired 
	CoOperatorService service;
	@Autowired
	LoginCredentialsRepository loginCredentialsRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	EmployerRepository employerRepository;
	@Autowired
	EmployeeProfileRepository employeeProfileRepository;
	@Autowired
	SocialEventRepository socialEventRepository;
	@Autowired
	InvitationRepository invitationRepository;
	@Autowired
	DocumentRepository documentRepository;
	@Autowired
	EmploymentContractRepository employmentContractRepository;
	@Autowired
	StudentEvaluationFormRepository studentEvaluationFormRepository;
	@Autowired
	TaxFormRepository taxFormRepository;
	
	
	
	/**
	 * =============================================================================================================
	 * 
	 * 	CREATOR METHODS
	 * 
	 * =============================================================================================================
	 */
	
	/**
	 * Creates a new student.
	 */
	//DONE: Make Student
	@PostMapping(value= {"/student/{studentId}/{lastName}/{firstName}",
			"/student/{studentId}/{lastName}/{firstName}/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<Student> createStudent( 
			@PathVariable	long 	studentId,
			@PathVariable	String	lastName,
			@PathVariable	String	firstName
	) 
	{
		
		//Input checks and cleaning
		InputSanitizer.checkNames(firstName, lastName);
		lastName = InputSanitizer.cleanName(lastName);
		firstName = InputSanitizer.cleanName(firstName);
		
		//Session checks
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<Student>(HttpStatus.FORBIDDEN); }
		
		//Create and return
		return new ResponseEntity<Student>( service.createStudent(studentId,firstName,lastName), HttpStatus.OK );	
	}
	
	/**
	 * Takes three input string to create a new Employer.
	 * 	Throws exceptions if any of the three strings are invalid.
	 * This is a wrapper around the CoOperatorService.createEmployer method, but now HTTP addressable.
	 * @param company	must be filled as a String;
	 * @param firstName	can be empty as a String; and
	 * @param lastName	can be empty as a String.
	 * @return the new employer as an Employer object persisted in the database.
	 */
	//DONE: Make Employer
	@PostMapping(value= {"/employer/{company}/{lastName}/{firstName}",
			"/employer/{company}/{lastName}/{firstName}/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<Employer> createEmployer(
		@PathVariable	String company,
		@PathVariable	String firstName,
		@PathVariable	String lastName
	) 
	{	
		
		//Input checks
		InputSanitizer.checkNames(company, firstName, lastName);
		company = InputSanitizer.cleanName(company);
		firstName = InputSanitizer.cleanName(firstName);
		lastName = InputSanitizer.cleanName(lastName);
		
		//Session checks
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<Employer>(HttpStatus.FORBIDDEN);}
		
		//Create and return
		return new ResponseEntity<Employer>( service.createEmployer(company,firstName,lastName) , HttpStatus.OK );	
	}
	
	/**
	 * Creates a new EmployeeProfile and its contained EmploymentContract based on input descriptors of
	 * an already-existing Student entity and an already-existing Employer entity in the database, the
	 * starting and ending dates of the contract, and the provided employment contract as a file attached
	 * to the body of the HTTP Request.
	 * @param studentId				as a long
	 * @param employerCompanyName	as a String
	 * @param employerFirstName		as a String
	 * @param employerLastName		as a String
	 * @param jobStartYear			as an int
	 * @param jobStartMonth			as an int
	 * @param jobStartDay			as an int
	 * @param jobEndYear			as an int
	 * @param jobEndMonth			as an int
	 * @param jobEndDay				as an int
	 * @param file					as the contract in the form of a MultipartFile object.
	 * @return
	 * @throws IOException 
	 */
	//DONE: Make EmployeeProfile
	@PostMapping(value= {
		"/employeeprofile/{studentId}/{employerCompanyName}/{employerFirstName}/"+
			"{employerLastName}/{jobStartYear}/{jobStartMonth}/{jobStartDay}/{jobEndYear}/"+
			"{jobEndMonth}/{jobEndDay}",
		"/employeeprofile/{studentId}/{employerCompanyName}/{employerFirstName}/"+
			"{employerLastName}/{jobStartYear}/{jobStartMonth}/{jobStartDay}/{jobEndYear}/"+
			"{jobEndMonth}/{jobEndDay}/"} )
	@ResponseBody 
	@Transactional
	public ResponseEntity<EmployeeProfileDescriptors> createEmployeeProfileByContract(
		@PathVariable 				long 			studentId,
		@PathVariable 				String 			employerCompanyName,
		@PathVariable 				String 			employerFirstName,
		@PathVariable				String 			employerLastName,
		@PathVariable 				int 			jobStartYear,
		@PathVariable 				int 			jobStartMonth,
		@PathVariable 				int 			jobStartDay,
		@PathVariable 				int 			jobEndYear,
		@PathVariable 				int 			jobEndMonth,
		@PathVariable 				int 			jobEndDay,
		@RequestParam("contract")	MultipartFile	file
	) throws IOException 
	{
		
		//Safety checks
		InputSanitizer.checkNames(employerCompanyName, employerFirstName, employerLastName);
		employerCompanyName = InputSanitizer.cleanName(employerCompanyName);
		employerFirstName = InputSanitizer.cleanName(employerFirstName);
		employerLastName = InputSanitizer.cleanName(employerLastName);
		if( studentRepository.existsById(studentId) == false )
			throw new IllegalStateException("Student with id " + studentId + " does not exist!");
		if( service.getEmployer(employerCompanyName,employerFirstName,employerLastName) == null )
			throw new IllegalStateException("Employer of company \"" + employerCompanyName + 
					"\" with full name \"" + employerFirstName + " " + employerLastName +
					" does not exist!");
			//Not going to bother checking days; LocalDate should throw an error for this, anyways.
		if( jobStartYear*365+jobStartMonth*30*jobStartDay > jobEndYear*365+jobEndMonth*30+jobEndDay )
			throw new IllegalArgumentException("Ending date ("+jobEndYear+"-"+jobEndMonth+"-"+jobEndDay+
					") cannot be before starting date ("+jobStartYear+"-"+jobStartMonth+"-"+jobStartDay+".");
		InputSanitizer.checkFile(file);
		
		//Session checker
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<EmployeeProfileDescriptors>(HttpStatus.FORBIDDEN);}
		
		//Get the objects from the database and created.
		Student student = service.getStudent(studentId);
		Employer employer = service.getEmployer(employerCompanyName,employerFirstName,employerLastName);
		LocalDate startDate = LocalDate.of(jobStartYear, jobStartMonth, jobStartDay);
		LocalDate endDate = LocalDate.of(jobEndYear, jobEndMonth, jobEndDay);
		
		//Create the contained contract entity
		String generatedContractName = "contract_between_" + student.getId() + "_and_" +
				employer.getCompany() + "_from_" + startDate.toString() + "_to_" + endDate.toString();
		EmploymentContract contract = this.createEmploymentContract(employerCompanyName, 
				employerLastName, employerFirstName, studentId, jobStartYear, jobStartMonth, jobStartDay,
				generatedContractName, file);
		//Create the profile entity
		EmployeeProfile employeeProfile = service.createEmployeeProfile(student, employer, contract, startDate, endDate);
		//Link the employeeProfile to this contract.
		employeeProfile.setContract(contract);
		
		//Returning
		return new ResponseEntity<EmployeeProfileDescriptors>(
				new EmployeeProfileDescriptors(employeeProfile) , HttpStatus.OK );
		
	}
	
	/**
	 * Creates a socialEvent based on all necessary data.
	 * 	Throws errors if any of the input is invalid.
	 * @param name			as a String
	 * @param description	as a String
	 * @param location		as a String
	 * @param startYear		as an int
	 * @param startMonth	as an int
	 * @param startDay		as an int
	 * @param startHour		as an int
	 * @param startMinute	as an int
	 * @param endYear		as an int
	 * @param endMonth		as an int
	 * @param endDay		as an int
	 * @param endHour		as an int
	 * @param endMinute		as an int
	 * @return
	 */
	//DONE: Make SocialEvent
	@PostMapping(value= {
		"/socialevent/{name}/{description}/{location}/{startYear}/{startMonth}/"+
			"{startDay}/{startHour}/{startMinute}/{endYear}/{endMonth}/{endDay}/{endHour}/{endMinute}",
		"/socialevent/{name}/{description}/{location}/{startYear}/{startMonth}/"+
			"{startDay}/{startHour}/{startMinute}/{endYear}/{endMonth}/{endDay}/{endHour}/{endMinute}/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<SocialEvent> createSocialEvent(
		@PathVariable @NonNull	String name,
		@PathVariable	String description,
		@PathVariable	String location,
		@PathVariable	int startYear,
		@PathVariable	int startMonth,
		@PathVariable	int startDay,
		@PathVariable	int startHour,
		@PathVariable	int startMinute,
		@PathVariable	int endYear,
		@PathVariable	int endMonth,
		@PathVariable	int endDay,
		@PathVariable	int endHour,
		@PathVariable	int endMinute
	) 
	{
		
		//Safety checks
		InputSanitizer.checkString(name, "name of social event");
		name = InputSanitizer.cleanName(name);
		InputSanitizer.checkString(description, "description of social event");
		InputSanitizer.checkString(location, "location of social event");
		if( (startYear*365 + startMonth*30 + startDay)*1440 + startHour*60 + startMinute
				> (endYear*365 + endMonth*30 + endDay)*1440 + endHour*60 + endMinute )
			throw new IllegalArgumentException("Ending time is before starting time. This is not possible.");
		
		//Session checker
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<SocialEvent>(HttpStatus.FORBIDDEN);	}
		
		//Creating attributes
		LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
		LocalTime startTime = LocalTime.of(startHour, startMinute);
		LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);
		LocalTime endTime = LocalTime.of(endHour, endMinute);
		
		//Creating the new SocialEvent
		return new ResponseEntity<SocialEvent>(
			service.createSocialEvent(name, description, location, startDate, startTime, endDate, endTime),
			HttpStatus.OK );
		
	}
	
	/**
	 * Originally an HTTP-addressible method, because contracts have to be created with employee profiles this
	 * method is, essentially, absorbed into the createEmployeeProfile method, though it still remains distinct
	 * from an editor standpoint.
	 */
	//DONE: Make EmploymentContract
	//DONE: Make this an upload method
	@Transactional
	private EmploymentContract createEmploymentContract( String company, String employerLastName, 
			String employerFirstName, long student, int startYear, int startMonth, int startDay,
			String name, MultipartFile file
	) throws IOException 
	{
		//Record the URI at which this file will be available at.
		String uri = "/contract/"+company+"/"+employerLastName+"/"+employerFirstName+"/"+student+"/"+
					startYear+"/"+startMonth+"/"+startDay;
		
		//Create
		EmploymentContract contract = service.createEmploymentContract(name,uri,file);
		//Add details
		String description = "EmploymentContract submitted by student number " + student +
								" for their internship at " + company + " through representative " +
								employerFirstName + " " + employerLastName + " on " +
								LocalDateTime.now().toString() + ".";
		String instructions = "To be read and confirmed by the employer as the legitimate contract.";
		contract.setDescription(description);
		contract.setInstructions(instructions);
		//Officialize the details
		employmentContractRepository.save(contract);
		documentRepository.save(contract);
		
		//Create and return
		return contract;
	}
	
	/**
	 * Upload method which creates a new student evaluation form in the database through an HTTP Request call.
	 * Automatically points itself to the associated Employee Profile, and throws an error if the 
	 * employee profile described by the student id and the employer descriptors does not exist.
	 */
	@PostMapping(value= {	"/studentevaluationform/{company}/{employerLastName}/{employerFirstName}/{student}"+
								"/{year}/{month}/{day}/{name}",
							"/studentevaluationform/{company}/{employerLastName}/{employerFirstName}/{student}"+
								"/{year}/{month}/{day}/{name}/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<StudentEvaluationFormDescriptors> createStudentEvaluationForm(
		@PathVariable				String company,
		@PathVariable				String employerLastName,
		@PathVariable				String employerFirstName,
		@PathVariable				long student,
		@PathVariable				int year,
		@PathVariable				int month,
		@PathVariable				int day,
		@PathVariable				String name,
		@RequestParam("evalForm")	MultipartFile file
	) throws IOException 
	{	
		//Safety check
		InputSanitizer.checkNames(company, employerFirstName, employerLastName);
		company = InputSanitizer.cleanName(company);
		employerFirstName = InputSanitizer.cleanName(employerFirstName);
		employerLastName = InputSanitizer.cleanName(employerLastName);
		InputSanitizer.checkFile(name, file);
		if(name.equalsIgnoreCase("empty_eval_form"))
			throw new IllegalArgumentException("Cannot upload empty_eval_form through this request.");
		
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<StudentEvaluationFormDescriptors>(HttpStatus.FORBIDDEN);	}
		
		//Record the URI at which this file will be available at.
		String uri = "/studentEvaluationForm/"+company+"/"+employerLastName+"/"+employerFirstName+"/"+student+"/"+
					year+"/"+month+"/"+day;
		
		//Create the new student evaluation form
		StudentEvaluationForm studentEvaluationForm = service.createStudentEvaluationForm(name, uri, file);
		
		//Find the associated employee profile through the employer and student
		List<Employer> employers = employerRepository.findEmployersByCompany(company);
		Employer employer = null;
		for( Employer e : employers )
			if( e.getFirstName().equals(employerFirstName) && e.getLastName().equals(employerLastName) )
				employer = e;
		if(employer == null)
			throw new IllegalArgumentException("The described employer " + company + ":" + employerLastName + "," +
				employerFirstName + " does not exist!");
		List<EmployeeProfile> profiles = employeeProfileRepository.findEmployeeProfilesByEmployer(employer);
		EmployeeProfile profile = null;
		for( EmployeeProfile p : profiles )
			if( p.getStudent().getId() == student )
				profile = p;
		if(profile == null)
			throw new IllegalArgumentException("The described student does not exist! among "+profiles.size());
		
		//Link the associated employee profile to its student evaluation form.
		profile.setEvaluationOfStudent(studentEvaluationForm);
		
		//Update the reference officially
		employeeProfileRepository.save(profile);
		
		//Add some additional details.
		String description = "Evaluation of the student's performance in his job at " + employer.getCompany() +
								" as described by representative " + employer.getFirstName() + " " +
								employer.getLastName() + " on " + LocalDateTime.now().toString();
		studentEvaluationForm.setDescription(description);
		String instructions = "For reading by university faculty.";
		studentEvaluationForm.setInstructions(instructions);
		//Officialize these details
		studentEvaluationFormRepository.save(studentEvaluationForm);
		documentRepository.save(studentEvaluationForm);
		
		//Return
		return new ResponseEntity<StudentEvaluationFormDescriptors>(
				new StudentEvaluationFormDescriptors(studentEvaluationForm) , HttpStatus.OK );
	}
	
	
	/**
	 * Dedicated method for creating the empty student evaluation form.
	 */
	@PostMapping(value= {"/studentevaluationform/empty","/studentevaluationform/empty/"})
	@ResponseBody
	@Transactional
	public ResponseEntity<StudentEvaluationFormDescriptors> createEmptyStudentEvaluationForm(
		@RequestParam("empty_eval_form") MultipartFile file	
	)throws IOException
	{ 
		//Safety
		InputSanitizer.checkFile(file);
		if(studentEvaluationFormRepository.existsByName("empty_eval_form"))
			throw new IllegalArgumentException("Cannot upload empty evaluation form to override existing one!");
		//Session checker
		try { isLoggedIn();}
		catch( IllegalArgumentException ex ) {
			return 	new ResponseEntity<StudentEvaluationFormDescriptors>(HttpStatus.FORBIDDEN); }
		//Create
		StudentEvaluationForm empty_eval_form = service.createStudentEvaluationForm(
				"empty_eval_form","/studentevaluationform/empty",file);
		//Add details
		String description = "The empty student evaluation form to be filled in and re-uploaded by employers.";
		String instructions = "Employers are to download and print this empty student evaluation form to " +
								"fill out near the end of each student\'s internship and re-upload a scanned " +
								"version onto the system for academic evaluation.";
		empty_eval_form.setDescription(description);
		empty_eval_form.setInstructions(instructions);
		//Officialize the details
		studentEvaluationFormRepository.save(empty_eval_form);
		documentRepository.save(empty_eval_form);
		//Return
		return new ResponseEntity<StudentEvaluationFormDescriptors>(
				new StudentEvaluationFormDescriptors(empty_eval_form) , HttpStatus.OK );
	}
	
	
	/**
	 * Upload method for new taxform.
	 * All employers see this new taxform.
	 */
	@PostMapping(value= {"/taxform/{year}/{name}","/taxform/{year}/{name}/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<TaxFormDescriptors> createTaxForm(
		@PathVariable 				short year,
		@PathVariable 				String name,
		@RequestParam("taxForm")	MultipartFile file
	) throws IOException 
	{	
		//Safety check
		InputSanitizer.checkFile(name, file);
		
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<TaxFormDescriptors>(HttpStatus.FORBIDDEN);	}
		
		//Record the URI at which this file will be available at.
		String uri = "/taxform/"+year;
		//Create
		TaxForm taxform = service.createTaxForm(name, uri, file, year);
		//Add details
		taxform.setDescription("Empty taxform supplied by the government for employers to fill out and submit.");
		taxform.setInstructions("Employers are to download this form for their use for taxes.");
		//Officialize the details
		taxFormRepository.save(taxform);
		documentRepository.save(taxform);
		//Return
		return new ResponseEntity<TaxFormDescriptors>( new TaxFormDescriptors(taxform), HttpStatus.OK );	
	}
	
	/**
	 * Creates an invitation based on defining tags of both SocialEvents and Employer
	 * 	Throws errors if any String or int is deemed invalid;
	 * 	Throws error if no company described as such can be found.
	 * @param employerCompany	, ie the name of the company, as a String
	 * @param employerFirstName	as a String
	 * @param employerLastName	as a String
	 * @param name				of the socialEvent as a String
	 * @param location			of the socialEvent as a String
	 * @param startYear			of the socialEvent as an int
	 * @param startMonth		of the socialEvent as an int
	 * @param startDay			of the socialEvent as an int
	 * @param startHour			of the socialEvent as an int
	 * @param startMinute		of the socialEvent as an int
	 * @return
	 */
	//DONE: Make Invitation
	@PostMapping(value= {
		"/invitation/{employerCompany}/{employerLastName}/{employerFirstName}/{name}/{location}/"
			+"{startYear}/{startMonth}/{startDay}/{startHour}/{startMinute}",
		"/invitation/{employerCompany}/{employerLastName}/{employerFirstName}/{name}/{location}/"
			+"{startYear}/{startMonth}/{startDay}/{startHour}/{startMinute}/"
	} )
	@ResponseBody
	@Transactional
	public ResponseEntity<InvitationDescriptors> createInvitation(
		@PathVariable	String employerCompany,
		@PathVariable	String employerFirstName,
		@PathVariable	String employerLastName,
		@PathVariable	String name,
		@PathVariable	String location,
		@PathVariable	int startYear,
		@PathVariable	int startMonth,
		@PathVariable	int startDay,
		@PathVariable	int startHour,
		@PathVariable	int startMinute
	)
	{
		
		//Safety check
		InputSanitizer.checkNames(employerCompany,employerFirstName,employerLastName);
		InputSanitizer.checkString(name, "name (of the event)");
		InputSanitizer.checkString(location, "location");
		employerCompany = InputSanitizer.cleanName(employerCompany);
		employerFirstName = InputSanitizer.cleanName(employerFirstName);
		employerLastName = InputSanitizer.cleanName(employerLastName);
		name = InputSanitizer.cleanName(name);
		if( service.getEmployer(employerCompany,employerFirstName,employerLastName) == null )
			throw new IllegalArgumentException("Defined employer does not exist!");
		LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
		LocalTime startTime = LocalTime.of(startHour, startMinute);
		if( service.getSocialEvent(name,location,startDate,startTime) == null )
			throw new IllegalArgumentException("Defined social event does not exist!");
		
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<InvitationDescriptors>(HttpStatus.FORBIDDEN);	}
		
		//Finding references
		Employer employer = service.getEmployer(employerCompany,employerFirstName,employerLastName);
		SocialEvent socialEvent = service.getSocialEvent(name,location,startDate,startTime);
		
		//Creating and linking
		Invitation invitation = service.createInvitation(employer, socialEvent);
		
		//Return success info
		return new ResponseEntity<InvitationDescriptors>(new InvitationDescriptors(invitation), HttpStatus.OK );
	}
	
	/**
	 * Creates a logincredential for an employer.
	 * 	Throws errors for invalid input String;
	 * 	Throws an error if the firstname and lastname are the same;
	 * 	Throws an error if the employer defined by inputs cannot be found.
	 * @param username
	 * @param password
	 * @param companyName
	 * @param firstName
	 * @param lastName
	 */
	//DONE: Make LoginCredential
	@PostMapping(value= {"/loginCredentials/employer","/loginCredentials/employer/"} )
	@ResponseBody
	@Transactional
	public boolean createLoginCredential(
		@RequestParam(value="username") 	String username,
		@RequestParam(value="password") 	String password,
		@RequestParam(value="companyName") 	String companyName,
		@RequestParam(value="firstName") 	String firstName,
		@RequestParam(value="lastName") 	String lastName
	)
	{	
		//Input safety check
		InputSanitizer.checkString(username, "username");
		InputSanitizer.checkString(password, "password");
		InputSanitizer.checkNames(companyName, firstName, lastName);
		companyName = InputSanitizer.cleanName(companyName);
		firstName = InputSanitizer.cleanName(firstName);
		lastName = InputSanitizer.cleanName(lastName);
		if( service.getEmployer(companyName,firstName,lastName) == null )
			throw new IllegalArgumentException("Employer does not exist!");
		
		//Find the referred-to Employer
		CoOperatorUser user = service.getEmployer(companyName,firstName,lastName);
		
		//Create the new login credential (or attempt to; additional error checks follow).
		service.createLoginCredential(username, password, user);
		
		//Indicate a success.
		return true;
	}
	
	
	
	
	/**
	 * =============================================================================================================
	 * 
	 * 	SETTER METHODS
	 * 
	 * =============================================================================================================
	 */
	
	/**
	 * Confirms the legitimacy of an employment contract.
	 * 	Throws errors for any invalid input;
	 * 	Throws error if the associated student cannot be found;
	 * 	Throws error if the employer described cannot be found;
	 * 	Throws error if the student and employer described do not have the described employment profile;
	 * 	Throws error of the username and password are not that of the employer.
	 * @param studentId				as a long passed through URI
	 * @param company				as a String passed through URI
	 * @param employerFirstName		as a String passed through URI
	 * @param employerLastName		as a String passed through URI
	 * @param startYear				as an int passed through URI
	 * @param startMonth			as an int passed through URI
	 * @param startDay				as an int passed through URI
	 * @param username				as a String passed as a JSON
	 * @param password				as a String passed as a JSON
	 * @return ResponseEntity<EmploymentContract>(theContract,HttpStatus.OK)
	 */
	//DONE: Confirm Co-Op (Issue #10)
	//Business Method
	@PutMapping(value= {
		"/employmentcontract/confirm/{studentId}/{company}/{employerFirstName}/{employerLastName}/"
			+"{startYear}/{startMonth}/{startDay}",
		"/employmentcontract/confirm/{studentId}/{company}/{employerFirstName}/{employerLastName}/"
			+"{startYear}/{startMonth}/{startDay}/"		} )
	@ResponseBody
	@Transactional
	public ResponseEntity<EmploymentContractDescriptors> confirmEmploymentContract(
		@PathVariable 	long 	studentId,
		@PathVariable 	String 	company,
		@PathVariable 	String 	employerFirstName,
		@PathVariable 	String 	employerLastName,
		@PathVariable 	int 	startYear,
		@PathVariable 	int 	startMonth,
		@PathVariable 	int 	startDay,
		@RequestParam	String	username,
		@RequestParam	String	password
	) 
	{
		//Safety checks
		InputSanitizer.checkNames(company, employerFirstName, employerLastName);
		InputSanitizer.checkString(username, "username");
		InputSanitizer.checkString(password, "password");
		company = InputSanitizer.cleanName(company);
		employerFirstName = InputSanitizer.cleanName(employerFirstName);
		employerLastName = InputSanitizer.cleanName(employerLastName);
		
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<EmploymentContractDescriptors>(HttpStatus.FORBIDDEN);	}
		
		//Find the student and employer to find the employeeProfile to find the employmentContract.
		//Pull errors if they don't exist.
		Student student = service.getStudent(studentId); //Get the student
		if(student==null)
			throw new IllegalArgumentException("No such student found.");
		Employer employer = service.getEmployer(company, employerFirstName, employerLastName); //Get the employer
		if(employer==null)
			throw new IllegalArgumentException("No such employer found.");
		List<EmployeeProfile> allProfiles = service.getEmployeeProfiles(student,employer);
		if(allProfiles.size()==0)
			throw new IllegalArgumentException("No such employment between student and employer found.");
		EmployeeProfile profile = null;
		for( EmployeeProfile employeeProfile : allProfiles )
			if( employeeProfile.getStartDate().equals(LocalDate.of(startYear, startMonth, startDay)) )
				profile = employeeProfile; //Get the target employeeProfile
		if(profile==null)
			throw new IllegalArgumentException("Found profile between student and employer but "
				+"not of the given starting employment date.");
		EmploymentContract contract = profile.getContract(); //Finally, get the employment contract
		if(contract==null)
			throw new IllegalStateException("Contact system admin: profile exists without contract.");
		
		//Attempt to confirm
		if( service.verifyEmploymentContract(contract, username, password) == false )
			throw new IllegalArgumentException("Provided username and password not valid for this contract.");
		
		//Officiallize the confirmation
		contract.setInstructions("This contract has been confirmed. The student is set.");
		employmentContractRepository.save(contract);
		documentRepository.save(contract);
		
		//Failure case
		return new ResponseEntity<EmploymentContractDescriptors>(
				new EmploymentContractDescriptors(contract) , HttpStatus.OK);
	}
	
	
	/**
	 * Denies the legitimacy of an employment contract.
	 * 	Throws errors for any invalid input;
	 * 	Throws error if the associated student cannot be found;
	 * 	Throws error if the employer described cannot be found;
	 * 	Throws error if the student and employer described do not have the described employment profile;
	 * 	Throws error of the username and password are not that of the employer.
	 * @param studentId				as a long passed through URI
	 * @param company				as a String passed through URI
	 * @param employerFirstName		as a String passed through URI
	 * @param employerLastName		as a String passed through URI
	 * @param startYear				as an int passed through URI
	 * @param startMonth			as an int passed through URI
	 * @param startDay				as an int passed through URI
	 * @param username				as a String passed as a JSON
	 * @param password				as a String passed as a JSON
	 * @return ResponseEntity<EmploymentContract>(theContract,HttpStatus.OK)
	 */
	//DONE: Deny Co-Op (Issue #10)
	//Business Method
	@PutMapping(value= {
		"/employmentcontract/deny/{studentId}/{company}/{employerFirstName}/{employerLastName}/"
			+"{startYear}/{startMonth}/{startDay}",
		"/employmentcontract/deny/{studentId}/{company}/{employerFirstName}/{employerLastName}/"
			+"{startYear}/{startMonth}/{startDay}/"		} )
	@ResponseBody
	@Transactional
	public ResponseEntity<EmploymentContractDescriptors> denyEmploymentContract(
		@PathVariable 	long 	studentId,
		@PathVariable 	String 	company,
		@PathVariable 	String 	employerFirstName,
		@PathVariable 	String 	employerLastName,
		@PathVariable 	int 	startYear,
		@PathVariable 	int 	startMonth,
		@PathVariable 	int 	startDay,
		@RequestParam	String	username,
		@RequestParam	String	password
	) 
	{
		//Safety checks
		InputSanitizer.checkNames(company, employerFirstName, employerLastName);
		InputSanitizer.checkString(username, "username");
		InputSanitizer.checkString(password, "password");
		company = InputSanitizer.cleanName(company);
		employerFirstName = InputSanitizer.cleanName(employerFirstName);
		employerLastName = InputSanitizer.cleanName(employerLastName);
		
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<EmploymentContractDescriptors>(HttpStatus.FORBIDDEN);	}
		
		//Find the student and employer to find the employeeProfile to find the employmentContract.
		//Pull errors if they don't exist.
		Student student = service.getStudent(studentId); //Get the student
		if(student==null)
			throw new IllegalArgumentException("No such student found.");
		Employer employer = service.getEmployer(company, employerFirstName, employerLastName); //Get the employer
		if(employer==null)
			throw new IllegalArgumentException("No such employer found.");
		List<EmployeeProfile> allProfiles = service.getEmployeeProfiles(student,employer);
		if(allProfiles.size()==0)
			throw new IllegalArgumentException("No such employment between student and employer found.");
		EmployeeProfile profile = null;
		for( EmployeeProfile employeeProfile : allProfiles )
			if( employeeProfile.getStartDate().equals(LocalDate.of(startYear, startMonth, startDay)) )
				profile = employeeProfile; //Get the target employeeProfile
		if(profile==null)
			throw new IllegalArgumentException("Found profile between student and employer but "
				+"not of the given starting employment date.");
		EmploymentContract contract = profile.getContract(); //Finally, get the employment contract
		if(contract==null)
			throw new IllegalStateException("Contact system admin: profile exists without contract.");
		
		//Attempt to deny
		if( service.denyEmploymentContract(contract, username, password) == false )
			throw new IllegalArgumentException("Provided username and password not valid for this contract.");
		
		//Officialize change
		contract.setInstructions("This contract has been denied and is therefore an indicator to the " +
								"student that they will have to submit a legitimate version of the contract " +
								"or forfeit their internship academically.");
		employmentContractRepository.save(contract);
		documentRepository.save(contract);
		
		//Success case
		return new ResponseEntity<EmploymentContractDescriptors>(
				new EmploymentContractDescriptors(contract) , HttpStatus.OK);
	}
	
	
	/**
	 * Accepts the invitation an employer receives to a social event by the invitation's id and employer's credentials.
	 * @param invitationId
	 * @param username
	 * @param password
	 * @return
	 */
	@PutMapping(value= {
			"/employer/invitation/{invitationId}/accept",
			"/employer/invitation/{invitationId}/accept/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<InvitationDescriptors> acceptInvitation(
		@PathVariable 	long 	invitationId,
		@RequestParam	String	username,
		@RequestParam	String	password
	)
	{
		//Input sanity checking
		InputSanitizer.checkString(username, "username");
		InputSanitizer.checkString(password, "password");
		
		//Get the invitation and ensure that the invitation belongs to who is being authenticated
		Invitation invitation = invitationRepository.findById(invitationId);
		if(invitation==null) {
			throw new NullPointerException("Invitation specified does not exist!");
		}
		if( !invitation.getEmployer().equals(service.getKey(username, password)) ) {
			throw new IllegalArgumentException("The invitation does not belong to the authenticated user.");
		}
		
		//Accept the invitation
		service.setAttendanceStatus(invitation, AttendanceStatus.ATTENDING);
		
		return new ResponseEntity<InvitationDescriptors>(new InvitationDescriptors(invitation), HttpStatus.OK);
	}
	
	/**
	 * Refuses the invitation an employer receives to a social event by the invitation's id and employer's credentials.
	 * @param invitationId
	 * @param username
	 * @param password
	 * @return
	 */
	@PutMapping(value= {
			"/employer/invitation/{invitationId}/refuse",
			"/employer/invitation/{invitationId}/refuse/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<InvitationDescriptors> refuseInvitation(
		@PathVariable 	long 	invitationId,
		@RequestParam	String	username,
		@RequestParam	String	password
	)
	{
		//Input sanity checking
		InputSanitizer.checkString(username, "username");
		InputSanitizer.checkString(password, "password");
		
		//Get the invitation and ensure that the invitation belongs to who is being authenticated
		Invitation invitation = invitationRepository.findById(invitationId);
		if(invitation==null) {
			throw new NullPointerException("Invitation specified does not exist!");
		}
		if( !invitation.getEmployer().equals(service.getKey(username, password)) ) {
			throw new IllegalArgumentException("The invitation does not belong to the authenticated user.");
		}
		
		//Accept the invitation
		service.setAttendanceStatus(invitation, AttendanceStatus.REFUSED);
		
		return new ResponseEntity<InvitationDescriptors>(new InvitationDescriptors(invitation), HttpStatus.OK);
	}
	
	
	/**
	 * Attempts to allow the user to change their login credentials' password.
	 * @param username		as a String
	 * @param oldPassword	as a String
	 * @param newPassword1	as a String
	 * @param newPassword2	as a String
	 * @return ResponseEntity<String>
	 */
	//DONE: Allow the employer to change password. (Issue #20)
	//Business Method
	@PutMapping(value= {"/login/changepassword","/login/changepassword/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<String> changePassword(
		@RequestParam(value="username")		String username,
		@RequestParam(value="oldPassword")	String oldPassword,
		@RequestParam(value="newPassword1")	String newPassword1,
		@RequestParam(value="newPassword2")	String newPassword2
	)
	{
		
		//Sanity check input Strings
		InputSanitizer.checkString(username, "username");
		InputSanitizer.checkString(oldPassword, "oldPassword");
		InputSanitizer.checkString(newPassword1, "newPassword1");
		InputSanitizer.checkString(newPassword2, "newPassword2");
		
		//Find login credential.
		if( service.usernameExists(username) == false )
			throw new IllegalArgumentException("username does not exist.");
		
		//Check that our old password is fine
		if( service.getKey(username,oldPassword) == null )
			throw new IllegalArgumentException("oldpassword is not valid.");
		
		//Make sure that our two new passwords match
		if( !newPassword1.equals(newPassword2) )
			throw new IllegalArgumentException("newPassword1 and newPassword2 do not match.");
		
		//Attempt to change the password. If it returns true, we've succeeded; if false, we've failed.
		if( !service.changeLoginCredential(username, oldPassword, newPassword1, newPassword2) )
			throw new IllegalStateException("Service failed to change the password.");
		
		//Return success indicator
		return new ResponseEntity<String>("Password change succesful.",HttpStatus.OK);
	}
	
	
	
	/**
	 * =============================================================================================================
	 * 
	 * 	GETTER METHODS
	 * 
	 * =============================================================================================================
	 */
	
	/**
	 * Returns the employer object by descriptors.
	 * @param company
	 * @param lastName
	 * @param firstName
	 * @return
	 */
	@GetMapping( value= {
			"/employer/{company}/{lastName}/{firstName}",
			"/employer/{company}/{lastName}/{firstName}/"})
	@ResponseBody
	@Transactional
	public ResponseEntity<EmployerDto> getEmployer(
		@PathVariable	String company,
		@PathVariable	String lastName,
		@PathVariable	String firstName
	){
		//Safety checks
		InputSanitizer.checkNames(company, firstName, lastName);
		company = InputSanitizer.cleanName(company);
		firstName = InputSanitizer.cleanName(firstName);
		lastName = InputSanitizer.cleanName(lastName);
		
		//Session check
		try {	isLoggedIn();		}
		catch( IllegalArgumentException ex ) {
			throw new IllegalStateException("user is not logged in.");}
		
		//Find the employer
		Employer employer = service.getEmployer(company, firstName, lastName);
		//Sanity check
		if(employer==null)
			throw new IllegalArgumentException("Could not find described employer.");
		
		//Return
		return new ResponseEntity<EmployerDto>(new EmployerDto(employer),HttpStatus.OK);
	}
	/**
	 * Returns the employer database id by descriptors.
	 * @param company
	 * @param lastName
	 * @param firstName
	 * @return
	 */
	@GetMapping( value= {
			"/employer/{company}/{lastName}/{firstName}/id",
			"/employer/{company}/{lastName}/{firstName}/id/"})
	@ResponseBody
	@Transactional
	public long getEmployerId(
		@PathVariable	String company,
		@PathVariable	String lastName,
		@PathVariable	String firstName
	){
		return(this.getEmployer(company, lastName, firstName)).getBody().getId();
	}
	
	
	
	/**
	 * Helper Method
	 * Gets the EmployeeProfile based on employer and student descriptors and starting date descriptors.
	 */
	private EmployeeProfile getEmployeeProfile(
			String 	company,
			String 	employerLastName,
			String 	employerFirstName,
			long	studentId,
			int		jobStartYear,
			int		jobStartMonth,
			int		jobStartDay
	) {		
		//Find the described student
		Student student = studentRepository.findStudentById(studentId);
		if(student==null)
			throw new IllegalArgumentException("Described student does not exist!");
		//Find the described employer
		List<Employer> companyEmployers = employerRepository.findEmployersByCompany(company);
		Employer employer = null;
		for( Employer e : companyEmployers )
			if( e.getFirstName().equals(employerFirstName) && e.getLastName().equals(employerLastName) )
				employer = e;
		if(employer==null)
			throw new IllegalArgumentException("Described employer " + company +": "+employerFirstName+" "+
				employerLastName+" does not exist!");
		//Find all of the employee profiles between this student and employer
		List<EmployeeProfile> studentProfiles = employeeProfileRepository.findEmployeeProfilesByStudent(student);
		List<EmployeeProfile> sharedProfiles = new ArrayList<EmployeeProfile>();
		for( EmployeeProfile p : studentProfiles )
			if( p.getEmployer().equals(employer) )
				sharedProfiles.add(p);
		if(sharedProfiles.size()==0)
			throw new IllegalArgumentException("The provided student is not employed by the provided employer!");
		//Find the specific employee profile with the matching time
		EmployeeProfile profile = null;
		for( EmployeeProfile p : sharedProfiles )
			if( p.getStartDate().equals( LocalDate.of(jobStartYear, jobStartMonth, jobStartDay) ) ) 
			{
				profile = p;
				break;
			}
		if(profile==null)
			throw new IllegalArgumentException("The student is not employed by the employer from this date.");
		
		//Found the profile; Now return it
		return profile;
	}
	
	
	/**
	 * Returns the employment contract of the associated employee profile via student and employer descriptors
	 * and the starting date.
	 */
	@GetMapping(
			value = "/contract/{company}/{employerLastName}/{employerFirstName}/{studentId}"+
					"/{jobStartYear}/{jobStartMonth}/{jobStartDay}",
			produces = MediaType.APPLICATION_PDF_VALUE )
	@Transactional
	public @ResponseBody byte[] getEmploymentContract(
			@PathVariable	String 	company,
			@PathVariable	String 	employerLastName,
			@PathVariable	String 	employerFirstName,
			@PathVariable	long	studentId,
			@PathVariable	int 	jobStartYear,
			@PathVariable	int		jobStartMonth,
			@PathVariable	int		jobStartDay
	) {
		
		//Safety checks
		InputSanitizer.checkNames(company, employerFirstName, employerLastName);
		company = InputSanitizer.cleanName(company);
		employerFirstName = InputSanitizer.cleanName(employerFirstName);
		employerLastName = InputSanitizer.cleanName(employerLastName);
		
		//Session checker
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			throw new IllegalStateException("Not logged in."); }
		
		//Get the EmployeeProfile so as to get to the EmploymentContract
		EmployeeProfile profile = this.getEmployeeProfile(company, employerLastName, employerFirstName, 
				studentId, jobStartYear, jobStartMonth, jobStartDay);
		//Found the profile; get its student evaluation form.
		EmploymentContract contract = profile.getContract();
		
		//Return the byte array
		return contract.getData();
		
	}
	/**
	 * Returns the Descriptors object of the EmploymentContract described
	 * That is to say that the received JSON will not contain the byte array of data, but everything
	 * else around it.
	 */
	@GetMapping(
			value = "/contract/{company}/{employerLastName}/{employerFirstName}/{studentId}"+
					"/{jobStartYear}/{jobStartMonth}/{jobStartDay}/preview" )
	@ResponseBody
	@Transactional
	public ResponseEntity<EmploymentContractDescriptors> previewEmploymentContract(
			@PathVariable	String 	company,			@PathVariable	String 	employerLastName,
			@PathVariable	String 	employerFirstName,	@PathVariable	long	studentId,
			@PathVariable int jobStartYear, @PathVariable int jobStartMonth, @PathVariable int jobStartDay
	) {
		
		//Safety checks
		InputSanitizer.checkNames(company, employerFirstName, employerLastName);
		company = InputSanitizer.cleanName(company);
		employerFirstName = InputSanitizer.cleanName(employerFirstName);
		employerLastName = InputSanitizer.cleanName(employerLastName);
		
		//Session checker
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			throw new IllegalStateException("Not logged in."); }
		
		//Get the EmployeeProfile so as to get to the EmploymentContract
		EmployeeProfile profile = this.getEmployeeProfile(company, employerLastName, employerFirstName, 
				studentId, jobStartYear, jobStartMonth, jobStartDay);
		//Found the profile; get its student evaluation form.
		EmploymentContract contract = profile.getContract();
		
		//Return the byte array
		return new ResponseEntity<EmploymentContractDescriptors>(
				new EmploymentContractDescriptors(contract),HttpStatus.OK);
	}
	/**
	 * Returns a list of Descriptors of the list of EmploymentContracts related to an employer.
	 */
	@GetMapping(value = "/contract/{company}/{employerLastName}/{employerFirstName}/previewall")
	@ResponseBody
	@Transactional
	public ResponseEntity<List<EmploymentContractDescriptors>> previewEmploymentContracts(
			@PathVariable	String 	company,			@PathVariable	String 	employerLastName,
			@PathVariable	String 	employerFirstName
	) {
		//Safety checks
		InputSanitizer.checkNames(company, employerFirstName, employerLastName);
		company = InputSanitizer.cleanName(company);
		employerFirstName = InputSanitizer.cleanName(employerFirstName);
		employerLastName = InputSanitizer.cleanName(employerLastName);
		
		//Session checker
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			throw new IllegalStateException("Not logged in."); }
		
		//Get the employer
		Employer employer = service.getEmployer(company, employerFirstName, employerLastName);
		//Sanity check
		if(employer==null)
			throw new IllegalArgumentException("Could not find described employer.");
		
		//Build the list of descriptors
		List<EmploymentContractDescriptors> descriptorList = new ArrayList<EmploymentContractDescriptors>();
		for( EmployeeProfile profile : employer.getEmployees() )
			descriptorList.add( new EmploymentContractDescriptors(profile.getContract()) );
		
		//Return
		return new ResponseEntity<List<EmploymentContractDescriptors>>(descriptorList,HttpStatus.OK);
	}
	
	
	/*
	 *	Returns the byte array of the unfilled student evaluation form and hopes that the client will know that 
	 *	the returned data is in the form of an OCTET Stream.
	 */
	//Business Method
	@GetMapping(
			value= "/studentevaluationform/empty",
			produces = MediaType.APPLICATION_PDF_VALUE )
	@Transactional
	public @ResponseBody byte[] getBlankStudentEvaluationForm() throws FileNotFoundException, IOException{
		
		//Session checker
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			throw new IllegalStateException("Not logged in.");	}
		
		//Safety checking for whether or not there is an empty eval form
		if( studentEvaluationFormRepository.findStudentEvaluationFormsByName("empty_eval_form").size() == 0 )
			throw new FileNotFoundException("Could not find \'empty_eval_form\' entity!");
		if( studentEvaluationFormRepository.findStudentEvaluationFormsByName("empty_eval_form").size() > 1 )
			throw new IllegalStateException("Contact system administrator: too many \'empty_eval_form\' entities.");
		//Returning of empty_eval_form
		StudentEvaluationForm empty_eval_form = studentEvaluationFormRepository
				.findStudentEvaluationFormsByName("empty_eval_form").get(0);
		return empty_eval_form.getData();
	}
	/*
	 *	Returns the peripheral information of the empty_eval_doc file.
	 */
	//Business Method
	@GetMapping(value= "/studentevaluationform/empty/preview")
	@ResponseBody
	@Transactional
	public ResponseEntity<StudentEvaluationFormDescriptors> previewBlankStudentEvaluationForm() 
			throws FileNotFoundException, IOException
	{	
		//Session checker
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			throw new IllegalStateException("Not logged in.");	}
		
		//Safety checking for whether or not there is an empty eval form
		if( studentEvaluationFormRepository.findStudentEvaluationFormsByName("empty_eval_form").size() == 0 )
			throw new FileNotFoundException("Could not find \'empty_eval_form\' entity!");
		if( studentEvaluationFormRepository.findStudentEvaluationFormsByName("empty_eval_form").size() > 1 )
			throw new IllegalStateException("Contact system administrator: too many \'empty_eval_form\' entities.");
		//Returning of empty_eval_form
		StudentEvaluationForm empty_eval_form = studentEvaluationFormRepository
				.findStudentEvaluationFormsByName("empty_eval_form").get(0);
		StudentEvaluationFormDescriptors descriptor = new StudentEvaluationFormDescriptors(empty_eval_form);
		return new ResponseEntity<StudentEvaluationFormDescriptors>(descriptor,HttpStatus.OK);
	}
	
	
	/*
	 * Returns the byte array of the student evaluation form based on student and company and time descriptors.
	 */
	@GetMapping(
			value = "/studentevaluationform/{company}/{employerLastName}/{employerFirstName}/{studentId}"+
					"/{jobStartYear}/{jobStartMonth}/{jobStartDay}",
			produces = MediaType.APPLICATION_PDF_VALUE  )
	@Transactional
	public @ResponseBody byte[] getStudentEvaluationForm(
			@PathVariable	String 	company,
			@PathVariable	String 	employerLastName,
			@PathVariable	String 	employerFirstName,
			@PathVariable	long	studentId,
			@PathVariable	int		jobStartYear,
			@PathVariable	int		jobStartMonth,
			@PathVariable	int		jobStartDay
		) throws FileNotFoundException, IOException
	{
		
		//Safety Checks
		InputSanitizer.checkNames(company, employerFirstName, employerLastName);
		company = InputSanitizer.cleanName(company);
		employerFirstName = InputSanitizer.cleanName(employerFirstName);
		employerLastName = InputSanitizer.cleanName(employerLastName);
		
		//Find the student evaluation form through the employee profile and its descriptors.
		EmployeeProfile profile = this.getEmployeeProfile(company, employerLastName, employerFirstName, 
				studentId, jobStartYear, jobStartMonth, jobStartDay);
		//Found the profile; get its student evaluation form.
		StudentEvaluationForm evalForm = profile.getEvaluationOfStudent();
		
		//Return it, if it exists.
		if(evalForm==null)
			throw new FileNotFoundException("This employment profile does not contain an student evaluation form!");
		return evalForm.getData();
		
	}
	/*
	 * Previews the student evaluation form by providing its peripheral information
	 */
	@GetMapping(
			value = "/studentevaluationform/{company}/{employerLastName}/{employerFirstName}/{studentId}"+
					"/{jobStartYear}/{jobStartMonth}/{jobStartDay}/preview")
	@ResponseBody
	@Transactional
	public ResponseEntity<StudentEvaluationFormDescriptors> previewStudentEvaluationForm(
			@PathVariable	String 	company,			@PathVariable	String 	employerLastName,
			@PathVariable	String 	employerFirstName,	@PathVariable	long	studentId,
			@PathVariable int jobStartYear, @PathVariable int jobStartMonth, @PathVariable int jobStartDay
		) throws FileNotFoundException, IOException
	{
		//Safety Checks
		InputSanitizer.checkNames(company, employerFirstName, employerLastName);
		company = InputSanitizer.cleanName(company);
		employerFirstName = InputSanitizer.cleanName(employerFirstName);
		employerLastName = InputSanitizer.cleanName(employerLastName);
		
		//Find the student evaluation form through the employee profile and its descriptors.
		EmployeeProfile profile = this.getEmployeeProfile(company, employerLastName, employerFirstName, 
				studentId, jobStartYear, jobStartMonth, jobStartDay);
		//Found the profile; get its student evaluation form.
		StudentEvaluationForm evalForm = profile.getEvaluationOfStudent();
		
		//Return it, if it exists.
		if(evalForm==null)
			throw new FileNotFoundException("This employment profile does not contain an student evaluation form!");
		StudentEvaluationFormDescriptors descriptor = new StudentEvaluationFormDescriptors(evalForm);
		return new ResponseEntity<StudentEvaluationFormDescriptors>(descriptor,HttpStatus.OK);
	}
	/*
	 * Previews all student evaluation forms related to a described employer
	 *  in the form of its descriptors.
	 */
	@GetMapping(value = "/studentevaluationform/{company}/{employerLastName}/{employerFirstName}/previewall")
	@ResponseBody
	@Transactional
	public ResponseEntity<List<StudentEvaluationFormDescriptors>> previewStudentEvaluationForms(
			@PathVariable	String 	company,			@PathVariable	String 	employerLastName,
			@PathVariable	String 	employerFirstName
		) throws FileNotFoundException, IOException
	{
		//Safety Checks
		InputSanitizer.checkNames(company, employerFirstName, employerLastName);
		company = InputSanitizer.cleanName(company);
		employerFirstName = InputSanitizer.cleanName(employerFirstName);
		employerLastName = InputSanitizer.cleanName(employerLastName);
		
		//Build the list of all student evaluation forms indirectly related to an employer in descriptors
		List<StudentEvaluationFormDescriptors> descriptors = new ArrayList<StudentEvaluationFormDescriptors>();
		Employer employer = service.getEmployer(company, employerFirstName, employerLastName);
		//Sanity check
		if(employer==null)
			throw new IllegalArgumentException("Could not find described employer.");
		for(EmployeeProfile profile : employer.getEmployees())
			descriptors.add( new StudentEvaluationFormDescriptors(profile.getEvaluationOfStudent()) );
		
		//Return
		return new ResponseEntity<List<StudentEvaluationFormDescriptors>>(descriptors,HttpStatus.OK);
	}
	
	
	//Done: Download specific TaxForm (Issue #12)
	//Business Method
	/*
	 * Download a specific taxform.
	 */
	@GetMapping(
			value = "/taxform/{year}/{name}",
			produces = MediaType.APPLICATION_PDF_VALUE)
	@Transactional 
	public @ResponseBody byte[] getTaxForm(
			@PathVariable short year,
			@PathVariable String name
	) throws FileNotFoundException, IOException
	{
		//Safety check
		InputSanitizer.checkString(name, "name");
		
		//Session checker
		try {	isLoggedIn();		}
		catch (IllegalArgumentException e) {
			return null;	}
		
		//Get the taxform
		if(!taxFormRepository.existsByYear(year))
			throw new FileNotFoundException("There are no taxforms available in the given year.");
		List<TaxForm> taxFormsInYear = taxFormRepository.findByYear(year);
		TaxForm taxForm = null;
		for(TaxForm t : taxFormsInYear)
			if(t.getName().equals(name)) {
				taxForm = t;
				break;
			}
		
		//Return its file
		return taxForm.getData();
	}
	/*
	 * Previews all taxforms by providing a list of taxform descriptors.
	 */
	@GetMapping(value = "/taxform/previewall")
	@ResponseBody
	@Transactional 
	public ResponseEntity<List<TaxFormDescriptors>> previewAllTaxForms() 
			throws FileNotFoundException, IOException
	{
		//Session checker
		try {	isLoggedIn();		}
		catch (IllegalArgumentException e) {
			return null;	}

		//Get all taxforms
		List<TaxFormDescriptors> allTaxFormsDescribed = new ArrayList<TaxFormDescriptors>();
		for( TaxForm t : taxFormRepository.findAll() )
			allTaxFormsDescribed.add( new TaxFormDescriptors(t) );
		
		//Return all taxforms
		return new ResponseEntity<List<TaxFormDescriptors>>(allTaxFormsDescribed,HttpStatus.OK);
	}
	/*
	 * Previews all taxforms of the provided year by providing that list of taxform descriptors.
	 */
	@GetMapping(value = "/taxform/{year}/preview")
	@ResponseBody
	@Transactional 
	public ResponseEntity<List<TaxFormDescriptors>> previewTaxForms(
			@PathVariable short year
	) throws FileNotFoundException, IOException
	{
		//Session checker
		try {	isLoggedIn();		}
		catch (IllegalArgumentException e) {
			return null;	}

		//Get all taxforms
		List<TaxFormDescriptors> taxFormsDescribed = new ArrayList<TaxFormDescriptors>();
		for( TaxForm t : taxFormRepository.findAll() )
			if(t.getYear()==year)
				taxFormsDescribed.add( new TaxFormDescriptors(t) );
		
		//Return all taxforms
		return new ResponseEntity<List<TaxFormDescriptors>>(taxFormsDescribed,HttpStatus.OK);
	}
	/*
	 * Previews a specific taxform.
	 */
	@GetMapping(value = "/taxform/{year}/{name}/preview")
	@ResponseBody
	@Transactional 
	public ResponseEntity<TaxFormDescriptors> previewTaxForm(
			@PathVariable short year,
			@PathVariable String name
	) throws FileNotFoundException, IOException
	{
		//Safety check
		InputSanitizer.checkString(name, "name");
		
		//Session checker
		try {	isLoggedIn();		}
		catch (IllegalArgumentException e) {
			return null;	}
		
		//Get the taxform
		if(!taxFormRepository.existsByYear(year))
			throw new FileNotFoundException("There are no taxforms available in the given year.");
		List<TaxForm> taxFormsInYear = taxFormRepository.findByYear(year);
		TaxForm taxForm = null;
		for(TaxForm t : taxFormsInYear)
			if(t.getName().equals(name)) {
				taxForm = t;
				break;
			}
		if(taxForm==null)
			throw new FileNotFoundException("Could not find taxform " + name + " in year " + year + ".");
		
		//Return the descriptors of this file
		return new ResponseEntity<TaxFormDescriptors>(new TaxFormDescriptors(taxForm),HttpStatus.OK);
	}
	
	
	
	/**
	 * Returns a list of all of the invitations associated to an employer.
	 * Pops errors for invalid inputs
	 * Pops error if the employer can't be found.
	 * @param companyName		as a String
	 * @param employerLastName	as a String
	 * @param employerFirstName	as a String
	 * @return	ResponseEntity<List<Invitation>>
	 */
	//DONE: View all invitations. (Issue #14)
	//Business Method
	@GetMapping(value= {"/employer/{companyName}/{employerLastName}/{employerFirstName}/invitations",
		"/employer/{companyName}/{employerLastName}/{employerFirstName}/invitations/"})
	@ResponseBody
	@Transactional
	public ResponseEntity<List<InvitationDescriptors>> getInvitations(
		@PathVariable	String companyName,
		@PathVariable	String employerLastName,
		@PathVariable	String employerFirstName
	)
	{
		//Sanity checking input
		InputSanitizer.checkNames(companyName, employerFirstName, employerLastName);
		companyName = InputSanitizer.cleanName(companyName);
		employerFirstName = InputSanitizer.cleanName(employerFirstName);
		employerLastName = InputSanitizer.cleanName(employerLastName);
		
		//Login session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<List<InvitationDescriptors>>(HttpStatus.FORBIDDEN);	}
		
		//If the employer exists, get it.
		Employer employer = service.getEmployer(companyName,employerFirstName,employerLastName);
		if(employer==null)
			throw new IllegalArgumentException("Described employer does not exist!");

		//Get the list of all of the employer's Invitations.
		List<InvitationDescriptors> invitationsDescribed = new ArrayList<InvitationDescriptors>();
		for( Invitation invitation : employer.getInvitations() )
			invitationsDescribed.add( new InvitationDescriptors(invitation) );

		//Return this list.
		return new ResponseEntity<List<InvitationDescriptors>>(invitationsDescribed,HttpStatus.OK);
	}
	
	
	/**
	 * Returns a list of all of the employee profiles related to an employer based on their descriptors.
	 * @param company
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	@GetMapping(value= {"/employeeprofile/{company}/{lastName}/{firstName}",
						"/employeeprofile/{company}/{lastName}/{firstName}/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<List<EmployeeProfileDescriptors>> getEmployeeProfilesOfEmployer(
			@PathVariable	String company,
			@PathVariable	String firstName,
			@PathVariable	String lastName
			)
	{
		//Sanity checking input
		InputSanitizer.checkNames(company, firstName, lastName);
		company = InputSanitizer.cleanName(company);
		firstName = InputSanitizer.cleanName(firstName);
		lastName = InputSanitizer.cleanName(lastName);

		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<List<EmployeeProfileDescriptors>>(HttpStatus.FORBIDDEN);	}

		//If the employer exists, get it.
		Employer employer = service.getEmployer(company,firstName,lastName);
		if(employer==null)
			throw new IllegalArgumentException("Described employer "+company+":"+lastName+","+firstName+" does not exist!");

		//Get a list of all profiles that are due within the given amount of days.
		List<EmployeeProfileDescriptors> sortedProfiles;
		BinaryTree<EmployeeProfileDescriptors> profileDescriptions = new BinaryTree<EmployeeProfileDescriptors>();
		long treeSize = 0;
		for( EmployeeProfile profile : employer.getEmployees() ) {
			profileDescriptions.add(new EmployeeProfileDescriptors(profile),profile.getEndDate().toEpochDay());
			treeSize++;
		}
		if(treeSize==0)
			throw new IllegalArgumentException("Described employer does not have any employees.");
		
		sortedProfiles = profileDescriptions.inOrder();

		//Return the sorted list.
		return new ResponseEntity<List<EmployeeProfileDescriptors>>(sortedProfiles,HttpStatus.OK);
	}
	
	
	/**
	 * Returns a list of all EmployeeProfiles whose evaluation forms have not yet been submitted and
	 *  are due within the specific amount of days, numberOfDaysAtMostToDeadlines, that is ordered 
	 *  from earliest to latest due date.
	 * Pulls errors for invalid inputs;
	 * Pulls errors for invalid employer.
	 * @param company
	 * @param firstName
	 * @param lastName
	 * @param numberOfDaysAtMostToDeadlines
	 * @return
	 */
	//DONE: View all deadlines upcoming deadlines for StudentEvaluationForms to upload. (Issue #15)
	//Business Method
	@GetMapping(value= {"/employer/{company}/{lastName}/{firstName}/studentevaluationforms/upcoming",
			"/employer/{company}/{lastName}/{firstName}/studentevaluationforms/upcoming"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<List<EmployeeProfileDescriptors>> getEmployeeProfilesWithUpcomingStudentEvaluationForms(
			@PathVariable	String company,
			@PathVariable	String firstName,
			@PathVariable	String lastName,
			@RequestParam	int numberOfDaysAtMostToDeadlines
	)
	{
		//Sanity checking input
		InputSanitizer.checkNames(company, firstName, lastName);
		company = InputSanitizer.cleanName(company);
		firstName = InputSanitizer.cleanName(firstName);
		lastName = InputSanitizer.cleanName(lastName);
		if(numberOfDaysAtMostToDeadlines<0)
			throw new IllegalArgumentException("number of days at most to deadlines is at its lowest 0.");
		
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<List<EmployeeProfileDescriptors>>(HttpStatus.FORBIDDEN);	}
		
		//If the employer exists, get it.
		Employer employer = service.getEmployer(company,firstName,lastName);
		if(employer==null)
			throw new IllegalArgumentException("Described employer does not exist!");

		//Get a list of all profiles that are due within the given amount of days.
		List<EmployeeProfileDescriptors> sortedProfiles;
		BinaryTree<EmployeeProfileDescriptors> profileDescriptions = new BinaryTree<EmployeeProfileDescriptors>();
		for( EmployeeProfile profile : employer.getEmployees() )
			if( profile.getStatus().equals(EmploymentStatus.ACTIVE) )
				if( profile.getEndDate().isBefore(LocalDate.now().plusDays(numberOfDaysAtMostToDeadlines)) )
					profileDescriptions.add(
							new EmployeeProfileDescriptors(profile) , profile.getEndDate().toEpochDay());
		sortedProfiles = profileDescriptions.inOrder();
		
		//Return the sorted list.
		return new ResponseEntity<List<EmployeeProfileDescriptors>>(sortedProfiles,HttpStatus.OK);
	}
	
	/*
	 *	Allows the user to start a session with the server through their login credentials.
	 */
	//DONE: Check edge case of user logging in after being logged in
	//Business Method
	@GetMapping("/login/{username}")
	@ResponseBody
	public ResponseEntity<EmployerLoginDTO> login (@PathVariable String username, @RequestParam String password) {
		//Safety checks
		InputSanitizer.checkString(username, "username");
		InputSanitizer.checkString(password, "password");
		
		//Here's what you should have been doing:
		//Check if the username is legit
		if(!service.usernameExists(username))
			throw new IllegalArgumentException("provided username is invalid.");
		//If the username is legit, try to get its key with the username and password
		CoOperatorUser user = service.getKey(username, password);
		if(user==null) //If you got null back, it means it wasn't valid.
			throw new IllegalArgumentException("provided password is invalid.");
		if(!user.getClass().equals(Employer.class))
			throw new IllegalArgumentException("the obtained user is not an employer.");
		
		return new ResponseEntity<EmployerLoginDTO>(
				new EmployerLoginDTO( (Employer)user , httpRequestServlet.getSession().getId() ) , 
				HttpStatus.OK);
		
		//This system works on multi-paged systems. However, and for our purposes, this is not necessary; and
		//more importantly, this is not practical.
		/*
		//create or persist session
		if(httpRequestServlet.getSession(false)== null) {
			HttpSession session = httpRequestServlet.getSession();
			String sessionID = session.getId();
			return new ResponseEntity<EmployerLoginDTO>(new EmployerLoginDTO((Employer)user,sessionID),HttpStatus.OK);
		}
		else {
			throw new IllegalArgumentException("You are already logged in.");
		}
		*/
	}
	
	/*
	 *	Checks if the provided username exists
	 */
	@GetMapping("/login/{username}/checkexists")
	@ResponseBody
	public ResponseEntity<Boolean> login (@PathVariable String username) {
		//Safety checks
		InputSanitizer.checkString(username, "username");
		
		//Here's what you should have been doing:
		//Check if the username is legit
		if(!service.usernameExists(username))
			return new ResponseEntity<Boolean>( false, HttpStatus.OK );
		return new ResponseEntity<Boolean>( true, HttpStatus.OK );
		
	}
	
	//DONE: View all students that are currently employed. (Issue #17)
	//Business Method
	@GetMapping("/employer/{company}/{lastName}/{firstName}/employeeprofiles/current")
	@ResponseBody
	@Transactional
	public ResponseEntity<List<EmployeeProfileDescriptors>> getAllCurrentEmployees(
			@PathVariable	String company,
			@PathVariable	String firstName,
			@PathVariable	String lastName
	)
	{
		//Sanity checking input
		InputSanitizer.checkNames(company, firstName, lastName);
		company = InputSanitizer.cleanName(company);
		firstName = InputSanitizer.cleanName(firstName);
		lastName = InputSanitizer.cleanName(lastName);
		
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return null;	}
		
		//If the employer exists, get it.
		Employer employer = service.getEmployer(company,firstName,lastName);
		if(employer==null)
			throw new IllegalArgumentException("Described employer does not exist!");

		//Get a list of all profiles that are currently employed.
		List<EmployeeProfileDescriptors> profileDescriptions = new ArrayList<EmployeeProfileDescriptors>();
		for( EmployeeProfile profile : employeeProfileRepository.findEmployeeProfilesByEmployer(employer))
			if(profile.getStartDate().isBefore(LocalDate.now()))
				if(profile.getEndDate().isAfter(LocalDate.now()))
					if(profile.getStatus().equals(EmploymentStatus.ACTIVE))
						profileDescriptions.add( new EmployeeProfileDescriptors(profile) );
		
		//Return the list.
		return new ResponseEntity<List<EmployeeProfileDescriptors>>(profileDescriptions,HttpStatus.OK);
	}

	//Checks whether the user is logged in 
	private void isLoggedIn() throws IllegalArgumentException {
		if(httpRequestServlet.getSession(false) == null) 
			throw new IllegalArgumentException("Log in to access methods.");
	}
	
	
	
	/**
	 * =============================================================================================================
	 * 
	 * 	DELETE METHODS
	 * 
	 * 		Some of these would best be done with the implementation of an admin account.
	 * 
	 * =============================================================================================================
	 */
	
	
	/**
	 * Deletes the described student and the EmployeeProfiles dependent on it (and their sub-dependencies).
	 * @param studentId
	 * @param lastName
	 * @param firstName
	 * @return
	 */
	@DeleteMapping(value= {	"/student/{studentId}/{lastName}/{firstName}",
							"/student/{studentId}/{lastName}/{firstName}/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<String> deleteStudent( 
			@PathVariable	long 	studentId,
			@PathVariable	String	lastName,
			@PathVariable	String	firstName
	)
	{
		//Safety check
		InputSanitizer.checkNames(firstName, lastName);
		firstName = InputSanitizer.cleanName(firstName);
		lastName = InputSanitizer.cleanName(lastName);
		//Make sure the student exists
		if(!studentRepository.existsById(studentId))
			throw new IllegalArgumentException("studentId does not exist.");
		if(!studentRepository.findStudentById(studentId).getLastName().equals(lastName))
			throw new IllegalArgumentException("lastName does not match studentId record.");
		if(!studentRepository.findStudentById(studentId).getFirstName().equals(firstName))
			throw new IllegalArgumentException("firstName does not match studentId record.");
		
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);	}
		
		//Delete the student
		Student student = studentRepository.findStudentById(studentId);
		service.deleteStudent(student);
		
		//Return with info
		return new ResponseEntity<String>("Deleted student "+studentId+" "+lastName+", "+firstName,HttpStatus.OK);
	}
	
	
	/**
	 * Deletes the specified employer and their employee profiles and invitations etc.
	 * @param company
	 * @param lastName
	 * @param firstName
	 * @return
	 */
	@DeleteMapping(value= {"/employer/{company}/{lastName}/{firstName}",
							"/employer/{company}/{lastName}/{firstName}/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<String> deleteEmployer(
			@PathVariable	String company,
			@PathVariable	String lastName,
			@PathVariable	String firstName
			) 
	{
		//Safety checks
		InputSanitizer.checkNames(company, firstName, lastName);
		company = InputSanitizer.cleanName(company);
		firstName = InputSanitizer.cleanName(firstName);
		lastName = InputSanitizer.cleanName(lastName);
		
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);	}
		
		//Delete the employer
		Employer employer = service.getEmployer(company,firstName,lastName);
		service.deleteEmployer(employer);
		
		//Return with info
		return new ResponseEntity<String>("Deleted employer "+company+": "+lastName+", "+firstName,HttpStatus.OK);
	}
	
	
	/**
	 * Deletes login credentials (username and password input). Fails if the user's data still exists.
	 * @param username
	 * @param password
	 * @return
	 */
	@DeleteMapping(value= {"/loginCredential","/loginCredentials/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<String> deleteLoginCredential(
		@RequestParam(value="username") 	String username,
		@RequestParam(value="password") 	String password
	)
	{
		//Safety check
		InputSanitizer.checkString(username, "username");
		InputSanitizer.checkString(password, "password");
		
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);	}
		
		//Delete the login credentials
		//(service already checks if this is allowed)
		service.deleteLoginCredentials(username, password);
		
		//Return with info
		return new ResponseEntity<String>("Deleted the login credentials",HttpStatus.OK);
	}
	
	
	/**
	 * Deletes the employee profile described and its contract and student evaluation.
	 * @param studentId
	 * @param employerCompanyName
	 * @param employerFirstName
	 * @param employerLastName
	 * @param jobStartYear
	 * @param jobStartMonth
	 * @param jobStartDay
	 * @param jobEndYear
	 * @param jobEndMonth
	 * @param jobEndDay
	 * @return
	 * @throws IOException
	 */
	@DeleteMapping(value= {
			"/employeeprofile/{studentId}/{employerCompanyName}/{employerFirstName}/"+
				"{employerLastName}/{jobStartYear}/{jobStartMonth}/{jobStartDay}/{jobEndYear}/"+
				"{jobEndMonth}/{jobEndDay}",
			"/employeeprofile/{studentId}/{employerCompanyName}/{employerFirstName}/"+
				"{employerLastName}/{jobStartYear}/{jobStartMonth}/{jobStartDay}/{jobEndYear}/"+
				"{jobEndMonth}/{jobEndDay}/"} )
	@ResponseBody 
	@Transactional
	public ResponseEntity<String> deleteEmployeeProfile(
			@PathVariable 				long 			studentId,
			@PathVariable 				String 			employerCompanyName,
			@PathVariable 				String 			employerFirstName,
			@PathVariable				String 			employerLastName,
			@PathVariable 				int 			jobStartYear,
			@PathVariable 				int 			jobStartMonth,
			@PathVariable 				int 			jobStartDay,
			@PathVariable 				int 			jobEndYear,
			@PathVariable 				int 			jobEndMonth,
			@PathVariable 				int 			jobEndDay
	) throws IOException 
	{
		//Safety checks
		InputSanitizer.checkNames(employerCompanyName,employerFirstName,employerLastName);
		employerCompanyName = InputSanitizer.cleanName(employerCompanyName);
		employerFirstName = InputSanitizer.cleanName(employerFirstName);
		employerLastName = InputSanitizer.cleanName(employerLastName);
		if( LocalDate.of(jobStartYear, jobStartMonth, jobStartDay).isAfter(
				LocalDate.of(jobEndYear, jobEndMonth, jobEndDay)) )
			throw new IllegalArgumentException("Starting job date cannot be after ending job date.");
		
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);	}
		
		//Get the employee profile
		EmployeeProfile profile = this.getEmployeeProfile(employerCompanyName, employerLastName, 
				employerFirstName, studentId, jobStartYear, jobStartMonth, jobStartDay);
		
		//Delete the employee profile.
		service.deleteEmployeeProfile(profile);
		
		//Return info
		return new ResponseEntity<String>("Succesfully deleted the employee profile.",HttpStatus.OK);
	}
	
	
	/**
	 * Deletes the described invitation and references to it.
	 * @param employerCompany
	 * @param employerFirstName
	 * @param employerLastName
	 * @param name
	 * @param location
	 * @param startYear
	 * @param startMonth
	 * @param startDay
	 * @param startHour
	 * @param startMinute
	 * @return
	 */
	@DeleteMapping(value= {
			"/invitation/{employerCompany}/{employerLastName}/{employerFirstName}/{name}/{location}/"
					+"{startYear}/{startMonth}/{startDay}/{startHour}/{startMinute}",
				"/invitation/{employerCompany}/{employerLastName}/{employerFirstName}/{name}/{location}/"
					+"{startYear}/{startMonth}/{startDay}/{startHour}/{startMinute}/"
	} )
	@ResponseBody
	@Transactional
	public ResponseEntity<String> deleteInvitation(
			@PathVariable	String employerCompany,
			@PathVariable	String employerFirstName,
			@PathVariable	String employerLastName,
			@PathVariable	String name,
			@PathVariable	String location,
			@PathVariable	int startYear,
			@PathVariable	int startMonth,
			@PathVariable	int startDay,
			@PathVariable	int startHour,
			@PathVariable	int startMinute
	)
	{
		//Safety Check
		InputSanitizer.checkNames(employerCompany, employerFirstName, employerLastName);
		InputSanitizer.checkString(name, "name");
		InputSanitizer.checkString(location, "location");
		employerCompany = InputSanitizer.cleanName(employerCompany);
		employerFirstName = InputSanitizer.cleanName(employerFirstName);
		employerLastName = InputSanitizer.cleanName(employerLastName);
		name = InputSanitizer.cleanName(name);
		
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);	}
		
		//Get the employer
		Employer employer = service.getEmployer(employerCompany, employerFirstName, employerLastName);
		if(employer==null)
			throw new IllegalArgumentException("Described employer does not exist.");
		//Get the event
		SocialEvent socialEvent = service.getSocialEvent(name, location, 
				LocalDate.of(startYear, startMonth, startDay), LocalTime.of(startHour, startMinute));
		if(socialEvent==null)
			throw new IllegalArgumentException("Described social event does not exist.");
		//Get the invitation
		Invitation invitation = service.getInvitation(employer, socialEvent);
		if(invitation==null)
			throw new IllegalArgumentException("This employer was not invited to this social event.");
		
		//Delete this invitation
		service.deleteInvitation(invitation);
		
		//Return info
		return new ResponseEntity<String>("Succesfully deleted the invitation.",HttpStatus.OK);
	}
		
	
	/**
	 * Deletes the described social event and removes its related invitations and references to that.
	 * @param name
	 * @param description
	 * @param location
	 * @param startYear
	 * @param startMonth
	 * @param startDay
	 * @param startHour
	 * @param startMinute
	 * @param endYear
	 * @param endMonth
	 * @param endDay
	 * @param endHour
	 * @param endMinute
	 * @return
	 */
	@DeleteMapping(value= {
			"/socialevent/{name}/{description}/{location}/{startYear}/{startMonth}/"+
				"{startDay}/{startHour}/{startMinute}/{endYear}/{endMonth}/{endDay}/{endHour}/{endMinute}",
			"/socialevent/{name}/{description}/{location}/{startYear}/{startMonth}/"+
				"{startDay}/{startHour}/{startMinute}/{endYear}/{endMonth}/{endDay}/{endHour}/{endMinute}/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<String> deleteSocialEvent(
			@PathVariable 	String name,
			@PathVariable	String description,
			@PathVariable	String location,
			@PathVariable	int startYear,
			@PathVariable	int startMonth,
			@PathVariable	int startDay,
			@PathVariable	int startHour,
			@PathVariable	int startMinute,
			@PathVariable	int endYear,
			@PathVariable	int endMonth,
			@PathVariable	int endDay,
			@PathVariable	int endHour,
			@PathVariable	int endMinute
	) 
	{
		//Safety check
		InputSanitizer.checkString(name, "name");
		name = InputSanitizer.cleanName(name);
		InputSanitizer.checkString(description, "description");
		InputSanitizer.checkString(location,"location");
		LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
		LocalTime startTime = LocalTime.of(startHour, startMinute);
		LocalDateTime start = LocalDateTime.of(startDate,startTime);
		LocalDateTime end = LocalDateTime.of(endYear, endMonth, endDay, endHour, endMinute);
		if(start.isAfter(end))
			throw new IllegalArgumentException("start cannot be after the end.");
		
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);	}
				
		//Get the social event
		SocialEvent socialEvent = service.getSocialEvent(name, location, startDate, startTime);
		if(socialEvent==null)
			throw new IllegalArgumentException("The described social event does not exist.");
		
		//Delete the social event
		socialEventRepository.delete(socialEvent);
		
		//Return success info
		return new ResponseEntity<String>("Succesfully deleted the social event.",HttpStatus.OK);
	}
	
	
	//NOTTODO: Employment Contracts should only be deleted when their profile is to be deleted.
	// They should, however, be updatable.
	
	
	/**
	 * Deletes the specified student evaluation form.
	 * @param company
	 * @param employerLastName
	 * @param employerFirstName
	 * @param student
	 * @param startYear
	 * @param startMonth
	 * @param startDay
	 * @param name
	 * @return
	 * @throws IOException
	 */
	@DeleteMapping(value= {	"/studentevaluationform/{company}/{employerLastName}/{employerFirstName}/{student}"+
								"/{jobStartYear}/{jobStartMonth}/{jobStartDay}/{name}",
							"/studentevaluationform/{company}/{employerLastName}/{employerFirstName}/{student}"+
								"/{jobStartYear}/{jobStartMonth}/{jobStartDay}/{name}/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<String> deleteStudentEvaluationForm(
			@PathVariable				String company,
			@PathVariable				String employerLastName,
			@PathVariable				String employerFirstName,
			@PathVariable				long student,
			@PathVariable				int startYear,
			@PathVariable				int startMonth,
			@PathVariable				int startDay,
			@PathVariable				String name
	) throws IOException
	{
		//Safety check
		InputSanitizer.checkNames(company, employerFirstName, employerLastName);
		InputSanitizer.checkString(name, "name");
		company = InputSanitizer.cleanName(company);
		employerFirstName = InputSanitizer.cleanName(employerFirstName);
		employerLastName = InputSanitizer.cleanName(employerLastName);
		
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);	}
		
		//Special Case: If we're dealing with the empty_eval_form
		if(name.equals("empty_eval_form")) {
			//Get the empty eval form
			List<StudentEvaluationForm> foundForms = service.getStudentEvaluationForms("empty_eval_form");
			if(foundForms.size() == 0)
				throw new IllegalArgumentException("There is no empty evaluation form.");
			if(foundForms.size() > 1 )
				throw new IllegalStateException("Contact system admin: too many empty evaluation forms.");
			StudentEvaluationForm empty_eval_form = foundForms.get(0);
			//Delete the empty_eval_form.
			service.deleteStudentEvaluationForm(empty_eval_form);
			//Return success message
			return new ResponseEntity<String>("Succesfully deleted the empty evaluation form.",HttpStatus.OK);
		}
		//Normal case follows: Not the empty_eval_form
		
		//Get the form
		StudentEvaluationForm studentEvaluationForm
			= this.getEmployeeProfile(company, employerLastName, employerFirstName, student, 
					startYear, startMonth, startDay).getEvaluationOfStudent();
		if(studentEvaluationForm==null)
			throw new IllegalArgumentException("This student evaluation form does not exist.");
		
		//Delete the form
		service.deleteStudentEvaluationForm(studentEvaluationForm);
		
		//Return success message.
		return new ResponseEntity<String>("succesfully deleted the student evaluation form.",HttpStatus.OK);
	}
	
	
	/**
	 * Deletes the empty student evaluation form.
	 * @return
	 * @throws IOException
	 */
	@DeleteMapping(value= {	"/studentevaluationform/empty_eval_form","studentevaluationform/empty_eval_form/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<String> deleteEmptyStudentEvaluationForm() throws IOException
	{
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);	}

		//Get the empty eval form
		List<StudentEvaluationForm> foundForms = service.getStudentEvaluationForms("empty_eval_form");
		if(foundForms.size() == 0)
			throw new IllegalArgumentException("There is no empty evaluation form.");
		if(foundForms.size() > 1 )
			throw new IllegalStateException("Contact system admin: too many empty evaluation forms.");
		StudentEvaluationForm empty_eval_form = foundForms.get(0);
		//Delete the empty_eval_form.
		service.deleteStudentEvaluationForm(empty_eval_form);
		//Return success message
		return new ResponseEntity<String>("Succesfully deleted the empty evaluation form.",HttpStatus.OK);
	}
	
	
	/**
	 * Deletes the taxform of the provided name and year.
	 * @param year
	 * @param name
	 * @return
	 * @throws IOException
	 */
	@DeleteMapping(value= {"/taxform/{year}/{name}","/taxform/{year}/{name}/"} )
	@ResponseBody
	@Transactional
	public ResponseEntity<String> deleteTaxForm(
		@PathVariable 				short year,
		@PathVariable 				String name
	) throws IOException 
	{
		//Safety check
		InputSanitizer.checkString(name, "name");
		
		//Session check
		try {	isLoggedIn();	}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);	}
		
		//Gets the tax form
		TaxForm taxForm = service.getTaxForm(year);
		if(taxForm==null)
			throw new IllegalArgumentException("Could not found tax form in "+year+".");
		
		//Deletes this tax form
		service.deleteTaxForm(taxForm);
		
		//Returns the success message
		return new ResponseEntity<String>("Succesfully deleted the "+name+" taxform of "+year+".",HttpStatus.OK);
	}
	
}
