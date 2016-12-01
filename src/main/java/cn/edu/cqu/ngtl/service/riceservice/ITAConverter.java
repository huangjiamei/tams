package cn.edu.cqu.ngtl.service.riceservice;

import cn.edu.cqu.ngtl.bo.StuIdClassIdPair;
import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import cn.edu.cqu.ngtl.viewobject.adminInfo.DepartmentFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.RelationTable;
import cn.edu.cqu.ngtl.viewobject.adminInfo.SessionFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.TermManagerViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.*;
import cn.edu.cqu.ngtl.viewobject.adminInfo.*;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyAssistantViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTaApplyViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassDetailInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.MyTaViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.WorkBenchViewObject;

import java.text.ParseException;
import java.util.List;

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

    List<MyTaViewObject> applicationToViewObject(List<TAMSTaApplication> allApplicationFilterByUid);

    List<ClassTeacherViewObject> classToViewObject(List<UTClass> allClassesFilterByUidAndCondition);

    List<SessionFundingViewObject> sessionFundingToViewObject(List<TAMSUniversityFunding> allFundingBySession);

    List<ClassFundingViewObject> classFundingToViewObject(List<TAMSClassFunding> allFundingByClass);

    RelationTable workflowStatusRtoJson(List<TAMSWorkflowStatusR> workflowStatusRelationByRoleFunctionId);

    List<DepartmentFundingViewObject> departmentFundingToViewObject(List<TAMSDeptFunding> allFundingBySession);

    List<String> extractIdsFromTaInfo(List<TaInfoViewObject> checkedlist);

    List<StuIdClassIdPair> extractIdsFromApplication(List<MyTaViewObject> checkedList);

    List<TeachCalendarViewObject> TeachCalendarToViewObject(List<TAMSTeachCalendar> calendars, boolean needCount); //needCount用于设定是否需要计算子活动的次数

    String countCalendarTotalElapsedTime(List<TeachCalendarViewObject> allCalendar);

    String countCalendarTotalBudget(List<TeachCalendarViewObject> allCalendar);

    List<String> extractIdsFromMyTaInfo(List<MyTaViewObject> checkedList);

    List<TeachCalendarViewObject> activitiesToViewObject(List<TAMSTeachCalendar> calendarsContainActivities);

    ClassTaApplyViewObject instructorAndClassInfoToViewObject(User instructor, UTClass classInfoById);

    List<WorkBenchViewObject> taCombineWorkbench(List<WorkBenchViewObject> list);
}
