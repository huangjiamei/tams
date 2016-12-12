
package cn.edu.cqu.ngtl.tools.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Properties;

/**
 * http://www.unitime.org/sct_dataformat.php
 * http://www.jwc.cqu.edu.cn
 *
 * 老校区上课从上午8:00(96)开始，下午从14:30(174)开始，晚上从19:30(234)开始
 * 新校区上课从上午8:30(102)开始，下午从14:00(168)开始，晚上从19:00(228)开始
 *
 * @author hmj, yanchaozhengshu
 *
 */
@Converter(autoApply = false)
public class UnitimeTimeSlotConverter implements AttributeConverter<String, Integer> {

    /**
     * {@inheritDoc}
     *
     * 把"1,2节"转成96(=08:00)，"3,4节"转成122(=10:10)
     *  "5,6节"转成174(=14:30)，"7,8节"转成200(=16:40)
     *  "9,10节"转成234(=19:30)
     *
     * 把"1，2节"转成102(=08:30)，"3，4节"转成126(=10:30)
     *  "5，6节"转成168(=14:00)，"7，8节"转成192(=16:00)
     *  "9，10节"转成228(=19:00)
     *
     * FIXME - HMJ - 1、用两个数字之间的中英文逗号来区分校区，勉强支持了两个校区。
     *               2、如果有第3，4，5，...个校区，那该怎么办？我管不了那么多了。
     */
	@Override
	public Integer convertToDatabaseColumn(String objectValue) {
		if (objectValue == null) {
			return null;
		}

        if(objectValue.startsWith("1,")||objectValue.startsWith("8:00"))
        	return 96;
        if(objectValue.startsWith("1，")||objectValue.startsWith("8:30"))
        	return 102;
        if(objectValue.startsWith("3,")||objectValue.startsWith("10:10"))
        	return 122;
        if(objectValue.startsWith("3，")||objectValue.startsWith("10:30"))
        	return 126;
        if(objectValue.startsWith("5,")||objectValue.startsWith("14:30"))
        	return 174;
        if(objectValue.startsWith("5，")||objectValue.startsWith("14:00"))
        	return 168;
        if(objectValue.startsWith("7,")||objectValue.startsWith("16:40"))
        	return 200;
        if(objectValue.startsWith("7，")||objectValue.startsWith("16:00"))
        	return 192;
        if(objectValue.startsWith("9,")||objectValue.startsWith("19:30"))
        	return 234;
        if(objectValue.startsWith("9，")||objectValue.startsWith("19:00"))
        	return 228;

		return 0;
}

    /**
     * {@inheritDoc}
     *
     * 把96/102转成"1,2节"/"1，2节"，122/126转成"3,4节"/"3，4节"
     *  174/168转成"5,6节"/"5，6节"，200/192转成"7,8节"/"7，8节"
     *  234/228转成"9,10节"/"9，10节"
     */
	@Override
	public String convertToEntityAttribute(Integer dataValue) {
		if (dataValue == null) {
			return null;
		}

		String propertyPath = this.getClass().getClassLoader().getResource("enroll.properties").getPath();
		/*
		String propertyPath = System.getProperty("catalina.home") + File.separator +
			     "webapps" + File.separator +
			     ConfigContext.getCurrentContextConfig().getProperty("application.id")+ File.separator+
				"WEB-INF" + File.separator +
			     "classes" + File.separator + "WEB-INF/enroll.properties";
	    */

		Properties enrollProperties = new Properties();
//		try {
//			enrollProperties.load( new FileInputStream(propertyPath));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		boolean classTimeSlot = false;
//		if(enrollProperties.getProperty("classTimeSlot") !=null){
//			classTimeSlot = Boolean.parseBoolean(enrollProperties.getProperty("classTimeSlot"));
//		}

		String name="";
		if(!classTimeSlot){
			switch(dataValue){
			case 96:
				name="1,2"; break;
			case 102:
				name="1，2"; break;
			case 122:
				name="3,4"; break;
			case 126:
				name="3，4"; break;
			case 174:
				name="5,6"; break;
			case 168:
				name="5，6"; break;
			case 200:
				name="7,8"; break;
			case 192:
				name="7，8"; break;
			case 234:
				name="9,10"; break;
			case 228:
				name="9，10"; break;
			}
			return name+"节";
		}else if(classTimeSlot){
			switch(dataValue){
			case 96:
				name="8:00-9:40"; break;
			case 102:
				name="8:30-10:10"; break;
			case 122:
				name="10:10-11:50"; break;
			case 126:
				name="10:30-12:10"; break;
			case 174:
				name="14:30-16:10"; break;
			case 168:
				name="14:00-15:40"; break;
			case 200:
				name="16:40-18:20"; break;
			case 192:
				name="16:00-17:40"; break;
			case 234:
				name="19:30-21:10"; break;
			case 228:
				name="19:00-20:40"; break;
			}
		}
		return name;
	}
}