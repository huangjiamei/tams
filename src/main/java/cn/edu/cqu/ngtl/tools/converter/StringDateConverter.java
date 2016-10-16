package cn.edu.cqu.ngtl.tools.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Converter(autoApply = false)
public class StringDateConverter implements AttributeConverter<String, Date> {
	@Override
	public Date convertToDatabaseColumn(String objectValue) {
		if(objectValue == null)
			return null;
		
        try {
        	Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(objectValue);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        return null;
	}

	@Override
	public String convertToEntityAttribute(Date dateValue) {
		if(dateValue == null)
			return "";
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateValue);
	}
}