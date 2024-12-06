package ca.mcgill.ecse321.cooperator9.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * The HTTP RESTful service calls have a lot of sanity checking. They take up a lot of space, so they're all put
 * into this dedicated class for input checks and input cleanup.
 * @author Edwin
 *
 */
public class InputSanitizer {

	/**
	 * Throw errors of the input string cannot is null or empty. Descriptor of the error can be changed with
	 * an input paramName.
	 * @param str
	 */
	public static void checkString(String str,String paramName) {
		if(str==null)
			throw new NullPointerException("Input string \'"+paramName+"\' cannot be null.");
		if(str.isEmpty())
			throw new IllegalArgumentException("Input string \'"+paramName+"\' cannot be empty.");
	}
	
	/**
	 * Throws errors if names are invalid inputs.
	 * @param firstName
	 * @param lastName
	 */
	public static void checkNames(String firstName, String lastName) {
		if(lastName==null)
			throw new NullPointerException("lastName cannot be null.");
		if(lastName.isEmpty())
			throw new IllegalArgumentException("lastName cannot be empty.");
		if(firstName==null)
			throw new NullPointerException("firstName cannot be null.");
		if(firstName.isEmpty())
			throw new IllegalArgumentException("firstName cannot be empty.");
	}
	
	/**
	 * Throws errors if names are invalid inputs.
	 * @param firstName
	 * @param lastName
	 */
	public static void checkNames(String company, String firstName, String lastName) {
		if(company==null)
			throw new NullPointerException("company name cannot be null.");
		if(company.isEmpty())
			throw new NullPointerException("company name cannot be null.");
		checkNames(firstName,lastName);
	}
	
	/**
	 * Enforces lowercase characters and uppercase first letters.
	 * @param name
	 * @return
	 */
	public static String cleanName(String name) {
		name = name.toLowerCase();
		name = (char)(name.charAt(0)+('A'-'a')) + name.substring(1);
		return name;
	}
	
	/**
	 * Checks that the file is good to use
	 * @param file
	 */
	public static void checkFile(MultipartFile file) {
		if( file == null )
			throw new NullPointerException("file cannot be null!");
		if (file.isEmpty() )
			throw new IllegalArgumentException("file cannot be empty!");
	}
	
	/**
	 * Checks that the file is good to use and its document entity name is sane.
	 * @param name
	 * @param file
	 */
	public static void checkFile(String name, MultipartFile file) {
		if( name == null )
			throw new NullPointerException("name cannot be null!");
		if( name.equals(""))
			throw new IllegalArgumentException("name cannot be empty!");
		checkFile(file);
	}
	
}
