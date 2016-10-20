
package cn.edu.cqu.ngtl.tools.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * http://www.unitime.org/sct_dataformat.php
 * 
 * @author hmj, yanchaozhengshu
 *
 */
@Converter(autoApply = false)
public class UnitimeDayOfWeekConverter implements AttributeConverter<String, Integer> {

    /**
     * {@inheritDoc}
     *
     * 例子：“一三五”转成84(=1010100)
     */
	@Override
	public Integer convertToDatabaseColumn(String objectValue) {
		if (objectValue == null) {
			return null;
		}
		int result = 0;
		for (int i=0;i<objectValue.length();i++){
			switch(objectValue.charAt(i)){
			case '一':
				result+=64;
				break;
			case '二':
				result+=32;
				break;
			case '三':
				result+=16;
				break;
			case '四':
				result+=8;
				break;
			case '五':
				result+=4;
			    break;
			case '六':
				result+=2;
			    break;
			case '日':
				result+=1;
				break;
			}
		}

        return result;
	}

    /**
     * {@inheritDoc}
     *
     * 例子：84(=1010100) 转成“一三五”
     */
	@Override
	public String convertToEntityAttribute(Integer dataValue) {
		if (dataValue == null) {
			return null;
		}
		String dayName = "一二三四五六日";
		String result = "";
		
		String bs=Integer.toBinaryString(dataValue);
		while(bs.length() > 7) 
			bs = bs.substring(1);		
		while(bs.length() < 7)
			bs = "0" + bs;
		
		for(int i = 0; i < bs.length(); i++) {
			switch(bs.charAt(i)) {
			case '1':
				result = result + dayName.charAt(i);
				break;
			}
		}
		return result;
	}
}