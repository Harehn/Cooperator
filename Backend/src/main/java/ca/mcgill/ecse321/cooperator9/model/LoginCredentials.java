package ca.mcgill.ecse321.cooperator9.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class LoginCredentials{
	
	private CoOperatorUser user;
	private String username;
	private String password;
	
	//Default Constructor
	public LoginCredentials() {
		
	}
	
	//Custom-written Constructor
	public LoginCredentials( CoOperatorUser user, String username, String password ) {
		this.setUser(user);
		this.setUsername(username);
		this.setPassword(password);
	}
	
	@ManyToOne(optional=false)
	public CoOperatorUser getUser() {				return this.user;		}	
	public void setUser(CoOperatorUser user) {		this.user = user;		}
	
	//Manually written-in sanity check
	private boolean setUsername(String value) {
		//if( isAlphanumeric(value) ) {
			this.username = value;
			return true;
		//}
		//return false;
	}
	@Id
	public String getUsername() {					return this.username;	}
	
	public boolean setPassword(String value) {
	    //if( isAlphanumeric(value) ) {
	    	this.password = value;
			return true;
		//}
		//return false;
	}
	public String getPassword() {
		return this.password;
	}

	//Manually-written methods for the credentials
	/**
	 * Returns a boolean of if the provided string is the username of this login credential.
	 * @author - Edwin
	 * @param username
	 * @return	success boolean.
	 */
	public boolean isUsername( String username ) {
		if( this.username.equals(username) ) 
			return true;
		return false;
	}
	/**
	 * Returns a boolean of if the provided username and password is valid.
	 * @author - Edwin
	 * @param username
	 * @param password
	 * @return	success boolean.
	 */
	public boolean isCredentials( String username, String password ) {
		if( this.username.equals(username) && this.password.equals(password) ) 
			return true;
		return false;
	}
	/**
	 * Returns the CoOperatorUser object the login credential provides access to, essentially giving an access key.
	 * @author - Edwin
	 * @param 	username	as a String
	 * @param 	password	as a String
	 * @return	user		as a CoOperatorUser object
	 */
	public CoOperatorUser attemptAccess( String username, String password ) {
		if(this.username==null)
			throw new NullPointerException("Username was null wtf");
		if(this.password==null)
			throw new NullPointerException("Password was null wtf");
		if( this.username.equals(username) && this.password.equals(password) ) 
			return this.getUser();
		return null;
	}
	/**
	 * Changes the username of the login credential if successful; returns a boolean for success.
	 * @author - Edwin
	 * @param oldUsername	as a String
	 * @param newUsername1	as a String
	 * @param newUsername2	as a String (The user must attempt their new username twice).
	 * @param password		as a String
	 * @return	success boolean
	 */
	public boolean changeUsername( String oldUsername, String newUsername1, String newUsername2, String password ) {
		if( this.username.equals(oldUsername) && this.username.equals(password) && newUsername1.equals(newUsername2) ) {
			boolean successfulChange = this.setUsername(newUsername1);
			return successfulChange;
		}
		return false;
	}
	/**
	 * Changes the password of the login credential if successful; returns a boolean for success.
	 * @author - Edwin
	 * @param 	username		as a String
	 * @param 	oldPassword		as a String
	 * @param 	newPassword1	as a String
	 * @param 	newPassword2	as a String (The user must attempt their new password twice).
	 * @return	success boolean
	 */
	public boolean changePassword( String username , String oldPassword, String newPassword1, String newPassword2 ) {
		if( this.username.equals(username) && this.password.equals(oldPassword) && newPassword1.equals(newPassword2) ) {
			boolean successfulChange = this.setPassword(newPassword1);
			return successfulChange;
		}
		return false;
	}
	/**
	 * helper isAlphanumeric method: Protects String from non-alphanumeric input.
	 * @param str String
	 * @return boolean for if the input String is alphanumeric-only.
	 */
	private boolean isAlphanumeric( String str ) {
		for( int i = 0 ; i < str.length(); i++ ) {
			//If charAt(i) is alphanumeric
			if ( str.charAt(i)>='A' && str.charAt(i)<='Z' || 
					str.charAt(i)>='a' && str.charAt(i)<='z' || 
					str.charAt(i)>='0' && str.charAt(i)<='0' ) {
				continue;
			}
			//If not alphanumeric...
			return false;
		}
		return true;
	}
	
	/**
	 * Returns false. To prevent a potential security breach, you cannot check if some LoginCredential you
	 * 	happen or "happen" to have is legitimate.
	 */
	public boolean equals(Object obj) {
		return false;
	}

	//Essentially, a password for making a LoginCredential speak. This would need some more work to be
	//an actually legitimate strategy, but the idea is that there shouldn't be any HTTP addressable ways
	//to get this code, and since the java classes will be, well, classes, no one looking at the repo
	//should be able to get access to this anyways.
	//Not sure how that would help them anyways, since literally only on method in all code does this in
	//CoOperatorService.java...
	public static final String AUTHENTICATION = "f1S9M7fE!56T@HRd3$3O69W3@a9I0U19^1a1S&9fHf*YbK(810)HG_-4WUJa3e";
	public CoOperatorUser whoAreYou(String heydudewhyareyouseeingthismethodey) {
		//This method allows one to check whether or not a LoginCredential is being made for an
		//already existing CoOperatorUser, which would be one way to hack the system.
		if( heydudewhyareyouseeingthismethodey.equals(AUTHENTICATION) )
			return this.getUser();
		return null;
	}

}
