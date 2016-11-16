package cn.edu.cqu.ngtl.service.classservice;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTeachCalendar;
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

    UTClass getClassInfoById(Integer classId);

    UTStudent getStudentInfoById(String stuId);

    List<UTClassInformation> getAllClassesFilterByUid(String uId);

    List<UTClassInformation> getAllClassesFilterByUidAndCondition(String uId, Map<String, String> conditions);

    List<TAMSTeachCalendar> getAllTaTeachCalendarFilterByUidAndClassId(String uId, String classId);
}
