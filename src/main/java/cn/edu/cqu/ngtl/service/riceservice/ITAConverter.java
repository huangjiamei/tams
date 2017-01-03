package cn.edu.cqu.ngtl.service.riceservice;

import cn.edu.cqu.ngtl.bo.StuIdClassIdPair;
import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import cn.edu.cqu.ngtl.viewobject.adminInfo.*;
import cn.edu.cqu.ngtl.viewobject.classinfo.*;
import cn.edu.cqu.ngtl.viewobject.common.FileViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.AppraisalDetailViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.MyClassViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.WorkBenchViewObject;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by tangjing on 16-10-19.
 */
public interface ITAConverter {


    List<ClassTeacherViewObject> classInfoToViewObject(List<UTClassInformation> informations);

    ClassTaApplyViewObject classInfoToApplyObject(User user, UTClass clazz);

    ApplyAssistantViewObject applyAssistantToTableViewObject(UTStudent student, UTClass clazz);

    ClassDetailInfoViewObject classInfoToViewObject(UTClass classId);

    TAMSTaApplication submitInfoToTaApplication(ClassInfoForm form);

    List<TermManagerViewObject> termInfoToViewObject(List<UTSession> sessions);

    UTSession termToDataObject(TermManagerViewObject newTerm) throws ParseException;

    List<TaInfoViewObject> taCombineDetailInfo(List<TAMSTa> allTa);

    List<MyTaViewObject> myTaCombinePayDay(List<TAMSTa> allTaFilteredByUid);

    List<cn.edu.cqu.ngtl.viewobject.classinfo.MyTaViewObject> myTaCombinePayDayClass(List<TAMSTa> allTaFilteredByUid);

    List<MyTaViewObject> applicationToViewObject(List<TAMSTaApplication> allApplicationFilterByUid);

    List<ClassTeacherViewObject> classToViewObject(List<UTClass> allClassesFilterByUidAndCondition);

    List<SessionFundingViewObject> sessionFundingToViewObject(List<TAMSUniversityFunding> allFundingBySession);

    List<ClassFundingViewObject> classFundingToViewObject(List<TAMSClassFunding> allFundingByClass,String uId);

    RelationTable workflowStatusRtoJson(String functionId, List<TAMSWorkflowStatusR> workflowStatusRelationByRoleFunctionId);

    List<DepartmentFundingViewObject> departmentFundingToViewObject(List<TAMSDeptFunding> allFundingBySession);

    List<String> extractIdsFromTaInfo(List<TaInfoViewObject> checkedlist);

    List<StuIdClassIdPair> extractIdsFromApplication(List<MyTaViewObject> checkedList);

    List<TeachCalendarViewObject> TeachCalendarToViewObject(List<TAMSTeachCalendar> calendars, boolean needCount); //needCount用于设定是否需要计算子活动的次数

    String countCalendarTotalElapsedTime(List<TeachCalendarViewObject> allCalendar);

    String countCalendarTotalBudget(List<TeachCalendarViewObject> allCalendar);

    List<String> extractIdsFromMyTaInfo(List<MyTaViewObject> checkedList);

    List<TeachCalendarViewObject> activitiesToViewObject(List<TAMSTeachCalendar> calendarsContainActivities);

    ClassTaApplyViewObject instructorAndClassInfoToViewObject(UTClass classInfoById);

    List<WorkBenchViewObject> taCombineWorkbench(List<WorkBenchViewObject> list);

    List<cn.edu.cqu.ngtl.viewobject.classinfo.MyTaViewObject> applicationToViewObjectClass(List<TAMSTaApplication> allApplicationFilterByUid);

    List<AppraisalDetailViewObject> teachCalendarToAppraisalViewObject(List<TAMSTeachCalendar> teachCalendars);

    List<FileViewObject> attachmentsToFileViewObject(List<TAMSAttachments> attachments);

    List<MyTaViewObject> studentInfoToMyTaViewObject(List<UTStudent> studentList);

    TAMSTaApplication TaViewObjectToTaApplication(MyTaViewObject application, String classid);

    List<TaInfoViewObject> getTaInfoListByConditions(Map<String, String> conditions, String uId);

    List<MyClassViewObject> MyClassViewObject(List<Object> myClassIdList);

    String countClassFunding(List<ClassFundingViewObject> classFundings, String totalAssignedFunding, String totalsetted);

    String countClassFundingTotalApproved(List<ClassFundingViewObject> classFundings);

    List<ClassApplyFeedBackViewObject> feedBackToViewObject(List<TAMSClassApplyFeedback> tamsClassApplyFeedbacks);

    List<WorkBenchViewObject> taCombineMyApplicationClass(List<WorkBenchViewObject> list);
}
