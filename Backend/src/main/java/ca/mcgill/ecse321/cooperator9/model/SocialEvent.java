package ca.mcgill.ecse321.cooperator9.model;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class SocialEvent{
	
	private LocalDate startDate;
	//LocalTimes are not entities in the database.
	//@ManyToOne(optional=false)
	public LocalDate getStartDate() {				return this.startDate;		}
	public void setStartDate(LocalDate startDate) {	this.startDate = startDate;	}

	private LocalTime startTime;
	//LocalTimes are not entities in the database.
	//@ManyToOne(optional=false)
	public LocalTime getStartTime() {				return this.startTime;		}
	public void setStartTime(LocalTime startTime) {	this.startTime = startTime;	}

	private LocalDate endDate;
	//LocalDates are not entities in the database.
	//@ManyToOne(optional=false)
	public LocalDate getEndDate() {					return this.endDate;	}
	public void setEndDate(LocalDate endDate) {		this.endDate = endDate;	}

	private LocalTime endTime;
	//LocalTimes are not entities in the database.
	//@ManyToOne(optional=false)
	public LocalTime getEndTime() {					return this.endTime;	}
	public void setEndTime(LocalTime endTime) {		this.endTime = endTime;	}

	private long id;
	public void setId(long value) {	this.id = value;		}
	@Id
	public long getId() {			return this.id;			}
	
	private String name;
	public void setName(String value) {	this.name = value;	}
	public String getName() {			return this.name;	}
	
	private String description;
	public void setDescription(String value) {	this.description = value;	}
	public String getDescription() {			return this.description;	}
	
	private String location;
	public void setLocation(String value) {		this.location = value;	}
	public String getLocation() {				return this.location;	}
	
	private Set<Invitation> invitations;
	@OneToMany(mappedBy="socialEvent" )
	public Set<Invitation> getInvitations() {					return this.invitations;		}
	public void setInvitations(Set<Invitation> invitations) {	this.invitations = invitations;	}

	public boolean equals(Object obj) {
		if( !this.getClass().equals(obj.getClass()) )
			return false;
		if( this.getId() != ((SocialEvent)obj).getId() )
			return false;
		if( !this.getName().equals( ((SocialEvent)obj).getName() ) )
			return false;
		if( !this.getDescription().equals( ((SocialEvent)obj).getDescription() ) )
			return false;
		if( !this.getLocation().equals( ((SocialEvent)obj).getLocation() ) )
			return false;
		if( !this.getStartDate().equals( ((SocialEvent)obj).getStartDate() ) )
			return false;
		if( !this.getEndDate().equals( ((SocialEvent)obj).getEndDate() ) )
			return false;
		if( !this.getStartTime().equals( ((SocialEvent)obj).getStartTime() ) )
			return false;
		if( !this.getEndTime().equals( ((SocialEvent)obj).getEndTime() ) )
			return false;
		return true;
	}
	
}
