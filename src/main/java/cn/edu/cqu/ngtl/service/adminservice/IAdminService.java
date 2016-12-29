package cn.edu.cqu.ngtl.service.adminservice;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.viewobject.adminInfo.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by tangjing on 16-10-25.
 */
public interface IAdminService {

    List<CMCourseClassification> getAllClassification();

    boolean addCourseClassificationOnlyWithName(String name);

    boolean changeCourseClassificationNameById(Integer id, String name);

    boolean removeCourseClassificationById(Integer id);

    List<TAMSCourseManager> getAllCourseManager();

    List<TAMSTaCategory> getAllTaCategories();

    boolean addTaCategory(TAMSTaCategory newTaCategory);

    boolean changeTaCategoryByEntiy(TAMSTaCategory tamsTaCategory);

    boolean removeTaCategoryById(Integer id);

    List<TAMSIssueType> getAllIssueTypes();

    boolean addTaIssueType(TAMSIssueType issueType);

    List<UTSession> getAllSessions();

    List<UTSession> getSelectedSessions(String termName, String startTime, String endTime) throws ParseException;

    boolean addSession(UTSession session);

    boolean changeIssueType(TAMSIssueType issueType);

    boolean removeIssueTypeById(String id);

    boolean changeSession(UTSession session);

    boolean removeTermByYearAndTerm(String termYear, String termTerm);

    boolean setCurrentSession(String termYear, String termTerm);

    List<UTInstructor> getInstructorByconditions(Map<String, String> conditions);

/*
    List<TAMSDeptFunding> getCurrFundingBySession();

    List<TAMSDeptFunding> getPreviousFundingBySession();
    */

    //获取学校经费
    List<TAMSUniversityFunding> getCurrFundingBySession();

    List<TAMSUniversityFunding> getPreviousFundingBySession();

    List<TAMSWorkflowStatusR> getWorkflowStatusRelationByRoleFunctionId(String roleFunctionId);

    String getRoleFunctionIdByRoleIdAndFunctionId(String roleId, String functionId);

    String setRoleFunctionIdByRoleIdAndFunctionId(String roleId, String functionId);

    void setWorkflowStatusRelationByRoleFunctionId(String functionId, String rfId, RelationTable rt);

    List<TAMSDeptFunding> getDepartmentCurrFundingBySession();

    List<TAMSDeptFunding> getDepartmentPreFundingBySession();

    List<TAMSClassFunding> getFundingByClass();

    List<TAMSCourseManager> getCourseManagerByCondition(Map<String, String> conditions);

    //学校经费过滤
    List<TAMSUniversityFunding> getUniFundPreByCondition(Map<String, String> conditions);

    List<TAMSDeptFunding> getDepartmentPreFundingByCondition(Map<String, String> conditions);

    //发布学院经费
    Long releaseDeptFunding(List<DepartmentFundingViewObject> departmentFundingViewObjects);

    Integer countDeptFunding(List<DepartmentFundingViewObject> departmentFundingViewObjects);

    //保存学院经费
    void saveDeptFunding(List<DepartmentFundingViewObject> departmentFundingViewObjects);

    //保存学期经费
    void saveSessionFunding(List<SessionFundingViewObject> sessionFundingViewObjects);

    //发布课程经费
    void saveClassFunding(List<ClassFundingViewObject> classFundingViewObjects);

    List<TAMSDeptFunding> getDeptFundCurrByCondition(Map<String, String> conditions);

    List<DetailFundingViewObject> getDetailFundByCondition(Map<String, String> conditions);

    /**
     * 时间阶段设置
     */
    boolean addTimeSetting(User user, String typeId, String startTime, String endTime);

    boolean deleteOneTimeSetting(TAMSTimeSettings tamsTimeSettings);

    List<TAMSTimeSettings> getallTimeSettings();

    List<ClassFundingViewObject> getClassFundByCondition(Map<String, String> conditions);

    List<TaFundingViewObject> getTaFundByCondition(Map<String, String> conditions);

    List<TAMSWorkflowStatus> getWorkFlowCategory();

    List<TAMSWorkflowStatus> getWorkFlowCategoryByCondition(String workflowfunction);

    //boolean addWorkFlowCategory(Map<String, String> conditions);

    //boolean modifyWorkFlowCategory(Map<String, String> conditions, String status, String order);

    boolean saveWorkFlowCategory(TAMSWorkflowStatus tamsWorkflowStatus);

    boolean deleteWorkFlowCategory(TAMSWorkflowStatus tamsWorkflowStatus);

    List<TAMSTimeSettingType> getAllTimeCategory();

    boolean saveTimeCategory(TAMSTimeSettingType timeSettingType);

    boolean deleteTimeCategory(TAMSTimeSettingType timeSettingType);

    String getSessionFundingStatistics();

    String getSessionFundingTotalApprove(String totalAssigned);

    String getClassTotalAssignedFunding();

    String getSessionFundingStatistics(String totalPlan);

    String getSessionFundingTotalApprove(List<DepartmentFundingViewObject> departmentFundingViewObjects);

    boolean releaseClassFunding(List<ClassFundingViewObject> classFundingViewObjects);

    short getWorkTime();

    void saveTaFunding(List<TaFundingViewObject> taFundingViewObjects);

    boolean initCourseManagerData();

    boolean addCourseManagerByInsIdAndCourseId(String courseId, String instructorId);

    List<UTInstructor> getInstructorByNameAndCodeAndDepartmentId(String Name, String code,String departmentId);

    List<UTInstructor> getInstructorByNameAndCode(String Name, String code);

    List<TAMSCourseManager> getCourseManagerByUid(String uId);

    List<TAMSDeptFundingDraft> getAllDeptFundingDraft();

    Integer countClassFunding (List<ClassFundingViewObject> classFundingViewObjects, String totalAssignedClass);

    String prepareCourseManagerToPDF(List<CourseManagerViewObject> courseManagerViewObjectList);

    List<TAMSTaBlackList> getAllBlackList();
    String prepareJingFeiToPDF(List<DetailFundingViewObject> JingFeiManager);
    String prepareSchoolHistoryFundsPDF(List<SessionFundingViewObject> SchoolHistoryFundsManager);
    String prepareCollegeHistoryFundsPDF(List<DepartmentFundingViewObject> CollegeHistoryFundsManager);
    String prepareCollegeFundsPDF(List<DepartmentFundingViewObject> CollegeFundsManager);

}
