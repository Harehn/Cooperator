package ca.mcgill.ecse321.cooperator9.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.jboss.logging.Logger;
import org.springframework.web.multipart.MultipartFile;

/**
 * Note that filePath is subject to design changes as we figure out how we want documents to be stored as pdf's.
 * Last updated 07-07-2019 
 */

@Entity
public abstract class Document{
	
	/*
	 * Attributes of Document:
	 * -	long docId
	 * -	String NAME
	 * -	String description
	 * -	String instructions
	 * -	String URI
	 * -	byte[] DATA	
	 */
	
	//Alternative to constructor so as to obey Java Spring's desire for default (null) constructors.
	//To be used inside the constructors of implemented methods.
	void construct( String name, String uri, byte[] fileData ) {
		this.name = name;
		this.uri = uri;
		this.data = fileData;
		this.docid = name.hashCode() + uri.hashCode();
	}
	
	//Unique numeric ID for each document. Used to differentiate between Document entities.
	private long docid;
	@Id
	public long getDocid() {			return this.docid;	}
	public void setDocid(long id) {		this.docid = id;	}
	
	//URI link to get access to the file.
	private String uri;
	public String getUri() {				return this.uri;		}
	public void setUri(String uri) {		this.uri = uri;			}
	
	//Literal description of the file contained
	private String description;
	public void setDescription(String value) {	this.description = value;	}
	public String getDescription() {			return this.description;	}
	
	//Literal instructions for the file for any high-level user
	private String instructions;
	public void setInstructions(String value) {	this.instructions = value;	}
	public String getInstructions() {			return this.instructions;	}
	
	//Name of the file
	private String name;
	public String getName() {					return this.name;			}
	public void setName(String name) {			this.name = name;			}
	
	//Serialized data of the file itself.
	private byte[] data;
	public byte[] getData() {						return this.data;			}
	public void setData(byte[] data) {			this.data = data;			}
	
	//Comparison method
	public boolean equals( Object obj ) {
		if( !this.getClass().equals(obj.getClass()) )
			return false;
		if( !this.getName().equals( ((Document)obj).getName() ) )
			return false;
		if( !this.getUri().equals( ((Document)obj).getUri() ) )
			return false;
		if( !this.getDescription().equals( ((Document)obj).getDescription() ) )
			return false;
		if( !this.getInstructions().equals( ((Document)obj).getInstructions() ) )
			return false;
		return true;
	}
	
}
