package cn.edu.cqu.ngtl.tools.utils;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.tams.impl.TAMSTimeSettingsDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTimeSettings;
import cn.edu.cqu.ngtl.service.userservice.impl.UserInfoServiceImpl;
import org.kuali.rice.krad.util.GlobalVariables;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	
	public Date convertStringToDate(String dateString) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");//小写的mm表示的是分钟  
		Date date=sdf.parse(dateString);
		return date;
	}

	public boolean betweenPeriod(String startTimeString,String endTimeString) throws ParseException{
		boolean between = false;
		User user = (User) GlobalVariables.getUserSession().retrieveObject("user");
		String princpalId = user.getCode();
		String uId = GlobalVariables.getUserSession().getPrincipalId();
		if (new UserInfoServiceImpl().isSysAdmin(uId)) {
			between = true;
		} else {
			if(startTimeString != null && endTimeString != null){
				Date startTime = this.convertStringToDate(startTimeString);
				Date endTime = this.convertStringToDate(endTimeString);
				Date nowTime = new Date();
				if (nowTime.getTime() >= startTime.getTime() && nowTime.getTime() <= endTime.getTime()) {
					between = true;
				}
			}
			else{
				between = true;
			}
		}

		return between;
	}

	public boolean isBetweenPeriodInCurrentSession(String timeSettingTypeId) {
        boolean between = false;
        if(timeSettingTypeId != null){
            TAMSTimeSettings timeSettings = new TAMSTimeSettingsDaoJpa().selectByTypeId(timeSettingTypeId);
            try {
                between = this.betweenPeriod(timeSettings.getStartTime(), timeSettings.getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return between;
    }

	public boolean isBetweenPeriod(String timeSettingTypeId, String sessionId)  {
		boolean between = false;
		if(sessionId != null && timeSettingTypeId != null){
			TAMSTimeSettings timeSettings = new TAMSTimeSettingsDaoJpa().selectByTypeIdAndSessionId(timeSettingTypeId, sessionId);
			try {
				between = this.betweenPeriod(timeSettings.getStartTime(), timeSettings.getEndTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return between;
	}

    public boolean betweenPeriodWithoutLogin(String startTimeString,
                                             String endTimeString) throws ParseException {
        boolean between = false;
        if (startTimeString != null && endTimeString != null) {
            Date startTime = this.convertStringToDate(startTimeString);
            Date endTime = this.convertStringToDate(endTimeString);
            Date nowTime = new Date();
            if (nowTime.getTime() >= startTime.getTime()
                    && nowTime.getTime() <= endTime.getTime()) {
                between = true;
            }
        }

        return between;
    }

    public boolean isBetweenPeriodInCurrentSessionWithoutLogin(String timeSettingTypeId) {
        boolean between = false;
        if(timeSettingTypeId != null){
            TAMSTimeSettings timeSettings = new TAMSTimeSettingsDaoJpa().selectByTypeId(timeSettingTypeId);
            try {
                between = this.betweenPeriodWithoutLogin(timeSettings.getStartTime(), timeSettings.getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return between;
    }
	
	public boolean isBetweenPeriodWithoutLogin(String timeSettingTypeId, String sessionId)  {
		boolean between = false;
		if(sessionId != null && timeSettingTypeId != null){
			TAMSTimeSettings timeSettings = new TAMSTimeSettingsDaoJpa().selectByTypeIdAndSessionId(timeSettingTypeId, sessionId);
			try {
				between = this.betweenPeriodWithoutLogin(timeSettings.getStartTime(), timeSettings.getEndTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return between;
	}

}
