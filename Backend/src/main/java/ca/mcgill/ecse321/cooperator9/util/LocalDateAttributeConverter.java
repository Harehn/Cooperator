package ca.mcgill.ecse321.cooperator9.util;

import java.time.LocalDate;
import java.sql.Date;

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
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate,Date> {

	@Override
	public Date convertToDatabaseColumn( LocalDate locDate ) {
		return ( locDate == null ? null : Date.valueOf(locDate) );
	}
	
	@Override
	public LocalDate convertToEntityAttribute( Date sqlDate ) {
		return ( sqlDate == null ? null : sqlDate.toLocalDate() );
	}
	
}
