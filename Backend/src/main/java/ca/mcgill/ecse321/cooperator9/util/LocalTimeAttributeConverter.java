package ca.mcgill.ecse321.cooperator9.util;

import java.time.LocalTime;
import java.sql.Time;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 
 * @author https://thoughts-on-java.org/persist-localdate-localdatetime-jpa/
 *
 *	This class was created because java.time.LocalDate is not supported by JPA.
 *
 */
@Converter(autoApply = true)
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime,Time> {

	@Override
	public Time convertToDatabaseColumn( LocalTime locTime ) {
		return ( locTime == null ? null : Time.valueOf(locTime) );
	}
	
	@Override
	public LocalTime convertToEntityAttribute( Time sqlTime ) {
		return ( sqlTime == null ? null : sqlTime.toLocalTime() );
	}
	
}
