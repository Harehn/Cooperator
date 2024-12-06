package ca.mcgill.ecse321.cooperator9.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

//http://tomee.apache.org/examples-trunk/jpa-enumerated/
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Invitation{
	
	private Employer employer;
	@ManyToOne(optional=false)
	public Employer getEmployer() {							return this.employer;			}
	public void setEmployer(Employer employer) {			this.employer = employer;		}

	private SocialEvent socialEvent;
	@ManyToOne(optional=false)
	public SocialEvent getSocialEvent() {					return this.socialEvent;		}
	public void setSocialEvent(SocialEvent socialEvent) {	this.socialEvent = socialEvent;	}

	private long id;
	public void setId(long value) {							this.id = value;				}
	@Id
	public long getId() {									return this.id;					}
	
	@Enumerated(EnumType.STRING)
	private AttendanceStatus status;
	//This is not being mapped to any other entity.
	//@ManyToOne(optional=false)
	public AttendanceStatus getStatus() {					return this.status;				}
	public void setStatus(AttendanceStatus status) {		this.status = status;			}

	public boolean equals(Object obj) {
		if( !this.getClass().equals(obj.getClass()) )
			return false;
		if( this.getId() != ((Invitation)obj).getId() )
			return false;
		if( !this.getEmployer().equals( ((Invitation)obj).getEmployer() ) )
			return false;
		if( !this.getSocialEvent().equals( ((Invitation)obj).getSocialEvent() ))
			return false;
		if( this.getStatus() != ((Invitation)obj).getStatus() )
			return false;
		return true;
	}
	
}
