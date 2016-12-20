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

    void validClassFunds(String classId);

    List<UTClassInformation> getAllCurSessionClasses();

    UTClass getClassInfoById(String classId);

    UTStudent getStudentInfoById(String stuId);

    List<UTClassInformation> getAllClassesFilterByUid(String uId);

    List<UTClassInformation> getAllClassesFilterByUidAndCondition(String uId, Map<String, String> conditions);

    List<TAMSTeachCalendar> getAllTaTeachCalendarFilterByUidAndClassId(String uId, String classId);

    TAMSTeachCalendar instructorAddTeachCalendar(String uId, String classId, TAMSTeachCalendar teachCalendar);

    boolean removeTeachCalenderById(String uId, String classId, String teachCalendarId);

    List<TAMSTeachCalendar> getAllTaTeachActivityAsCalendarFilterByUidAndClassId(String uId, String classId);

    /**
     * 助教提交申请的函数<br>
     * return : 状态码<br>
     *      1 : 不在教师申请助教期间
     *      2 : 已经提交过的申请，因为其他原因处于未申请的状态（将会重设为申请初始状态）
     *      3 : 写入申请信息失败
     *      4 : 写入课程考核信息失败
     *      5 : 未找到用户的角色
     *      6 : 未找到"审核方法"
     *      7 : 成功
     *      8 : 正常提交过的申请，即（看上去像是重复申请）
     *      9 : 未知错误
     * @return
     */
    short instructorAddClassTaApply(String instructorId, String classId, String assistantNumber, List<TAMSClassEvaluation> classEvaluations);

    List<TAMSTa> getAllTaFilteredByClassid(String classId);

    List<TAMSTaApplication> getAllApplicationFilterByClassid(String classId);

    boolean removeCalendarFileById(String classId, String attachmentId);

    boolean removeAllCalendarFilesByClassIdAndCalendarId(String classId, String calendarId);

    boolean approveToNextStatus(List<String> classIds, String uid);

    boolean rejectToPreviousStatus(List<String> classIds, String uid);

    List<TAMSWorkflowStatus> classStatusAvailable(String uid, String classId);

    boolean classStatusToCertainStatus(String uid, String classId, String workflowStatusId);

    boolean insertFeedBack(String classId,String uId,String reasons,String oldStatus,String newStatus);

    List<TAMSClassApplyFeedback> getFeedBackByClassId(String classId);
}
