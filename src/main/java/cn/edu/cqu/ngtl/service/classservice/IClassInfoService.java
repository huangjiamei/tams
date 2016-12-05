package cn.edu.cqu.ngtl.service.classservice;

import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;

import java.util.List;
import java.util.Map;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
public interface IClassInfoService {

    List<UTClassInformation> getAllClassesMappedByDepartment();

    UTClass getClassInfoById(String classId);

    UTStudent getStudentInfoById(String stuId);

    List<UTClassInformation> getAllClassesFilterByUid(String uId);

    List<UTClassInformation> getAllClassesFilterByUidAndCondition(String uId, Map<String, String> conditions);

    List<TAMSTeachCalendar> getAllTaTeachCalendarFilterByUidAndClassId(String uId, String classId);

    TAMSTeachCalendar instructorAddTeachCalendar(String uId, String classId, TAMSTeachCalendar teachCalendar);

    boolean removeTeachCalenderById(String uId, String classId, String teachCalendarId);

    List<TAMSTeachCalendar> getAllTaTeachActivityAsCalendarFilterByUidAndClassId(String uId, String classId);

    boolean instructorAddClassTaApply(String instructorId, String classId, String assistantNumber, List<TAMSClassEvaluation> classEvaluations);

    List<TAMSTa> getAllTaFilteredByClassid(String classId);

    List<TAMSTaApplication> getAllApplicationFilterByClassid(String classId);

    boolean removeCalendarFileById(String classId, String attachmentId);

    boolean removeAllCalendarFilesByClassIdAndCalendarId(String classId, String calendarId);

    boolean approveToNextStatus(List<String> classIds, String uid);

    boolean rejectToPreviousStatus(List<String> classIds, String uid);
}
