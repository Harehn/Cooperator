package ca.mcgill.ecse321.cooperator9.dto;

import ca.mcgill.ecse321.cooperator9.model.Document;

/**
 * 
 * @author Edwin
 *
 *	This class and its subclasses exist to make it so that when an HTTP Request returns a list of documents, we
 *	don't force the client to download all of the actual document data - just the header, peripheral data, like
 *	the name, description, and of course the URI at which the actual documents can be accessed.
 *
 */
abstract class DocumentDescriptors {

	protected long docid;
	protected String name;
	protected String description;
	protected String instructions;
	protected String uri;
	
	public DocumentDescriptors() {}
	public DocumentDescriptors(Document document) {
		if(document==null)
			throw new NullPointerException("Received null document in documentdesriptor constructor.");
		this.docid 			= document.getDocid();
		this.name 			= document.getName();
		this.description 	= document.getDescription();
		this.instructions 	= document.getInstructions();
		this.uri 			= document.getUri();
		//The purpose of this descriptor is to not contain byte data.*/
	}
	public DocumentDescriptors(long docid, String name, String description, 
			String instructions, String uri, byte[] data) {
		this.docid			= docid;
		this.name			= name;
		this.description	= description;
		this.instructions	= instructions;
		this.uri			= uri;
		//The purpose of this descriptor is to not contain byte data.
	}
	
	public long getDocid() {			return docid;			}
	public String getName() {			return name;			}
	public String getDescription() {	return description;		}
	public String getInstructions() {	return instructions;	}
	public String getUri() {			return uri;				}
	
}
