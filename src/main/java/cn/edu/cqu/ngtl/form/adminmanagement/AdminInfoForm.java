package cn.edu.cqu.ngtl.form.adminmanagement;

import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PERM_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_T;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSIssueType;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaCategory;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTimeSettings;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;
import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;
import cn.edu.cqu.ngtl.form.BaseForm;
import cn.edu.cqu.ngtl.viewobject.adminInfo.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by awake on 2016-10-21.
 */
public class AdminInfoForm extends BaseForm implements Serializable {


    private static final long serialVersionUID = -1974217788100313751L;



    /**
     * 工作流管理页面相关
     */

    private String roleId;

    private String functionId;

    private String workflowRelationTable;

    /**
     * 工作流类型页面相关
     */
    private List<TAMSWorkflowStatus> tamsWorkflowStatuses;

    /**
     * 工作流类型过滤器输入框
     */
    private String getWorkFlowStatus;

    public List<TAMSWorkflowStatus> getTamsWorkflowStatuses() {
        return tamsWorkflowStatuses;
    }

    public void setTamsWorkflowStatuses(List<TAMSWorkflowStatus> tamsWorkflowStatuses) {
        this.tamsWorkflowStatuses = tamsWorkflowStatuses;
    }

    public String getGetWorkFlowStatus() {
        return getWorkFlowStatus;
    }

    public void setGetWorkFlowStatus(String getWorkFlowStatus) {
        this.getWorkFlowStatus = getWorkFlowStatus;
    }

    /**
     * 经费管理页面
     */

    private String pieChartsNameValuePairs;

    private List<SessionFundingViewObject> sessionFundings;

    private List<SessionFundingViewObject> previousSessionFundings;

    private List<DepartmentFundingViewObject> departmentCurrFundings;

    private List<DepartmentFundingViewObject> departmentPreFundings;

    private List<ClassFundingViewObject> classFundings;

    private List<TaFundingViewObject> taFunding;

    private List<DetailFundingViewObject> detailFunding;

    /**
     * 通过input中转站将一个用于区分当前修改的到底是哪个tab的flag传到后台。
     * TAB_FLAG_SCHOOL：学校tab
     * TAB_FLAG_DEPARTMENT：学院tab
     * TAB_FLAG_CLASS：课程(教学班？)tab
     */
    private String curTabFlag;
    public static final String TAB_FLAG_SCHOOL="TAB_FLAG_SCHOOL";
    public static final String TAB_FLAG_DEPARTMENT="TAB_FLAG_DEPARTMENT";
    public static final String TAB_FLAG_CLASS="TAB_FLAG_CLASS";


    /**
     * 经费管理页面————批次经费，过滤器的输入框
     */
    private String sTimes;//批次
    private String sPreFunds;//预算
    private String sApplyFunds;//申报
    private String sApprovalFunds;//批准
    private String sAddingFunds;//追加
    private String sRewardFunds;//奖励
    private String sTrafficFunds;//交通补贴
    private String sTotalFunds;//总经费

    /**
     * 经费管理页面————学院经费，过滤器的输入框
     */
    private String dTimes;
    private String deptId;
    private String dPreFunds;
    private String dApplyFunds;
    private String dApprovalFunds;
    private String dAddingFunds;
    private String dRewardFunds;
    private String dTrafficFunds;//交通补贴
    private String dTotalFunds;

    /**
     * 经费管理页面————课程经费，过滤器的输入框
     */
    private String cDept;
    private String cName;
    private String cCode;
    private String cNbr;
    private String cTeacher;
    private String cApplyFunds;
    private String cActualFunds;
    private String cPhdFunds;
    private String cBonus;
    private String cTrafficFunds;
    private String cTotalFunds;

    /**
     * 经费管理页面————助教经费，过滤器的输入框
     * @return
     */
    private String tDept;
    private String tName;
    private String tNumber;
    private String tType;
    private String tCourseName;
    private String tCourseCode;
    private String tAssignedFunds;
    private String tPhdFunds;
    private String tTrafficFunds;
    private String tBonus;
    private String tTotalFunds;

    public String gettDept() {
        return tDept;
    }

    public void settDept(String tDept) {
        this.tDept = tDept;
    }

    public String gettNumber() {
        return tNumber;
    }

    public void settNumber(String tNumber) {
        this.tNumber = tNumber;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettType() {
        return tType;
    }

    public void settType(String tType) {
        this.tType = tType;
    }


    public String gettCourseName() {
        return tCourseName;
    }

    public void settCourseName(String tCourseName) {
        this.tCourseName = tCourseName;
    }

    public String gettCourseCode() {
        return tCourseCode;
    }

    public void settCourseCode(String tCourseCode) {
        this.tCourseCode = tCourseCode;
    }

    public String gettAssignedFunds() {
        return tAssignedFunds;
    }

    public void settAssignedFunds(String tAssignedFunds) {
        this.tAssignedFunds = tAssignedFunds;
    }

    public String gettPhdFunds() {
        return tPhdFunds;
    }

    public void settPhdFunds(String tPhdFunds) {
        this.tPhdFunds = tPhdFunds;
    }

    public String gettTrafficFunds() {
        return tTrafficFunds;
    }

    public void settTrafficFunds(String tTrafficFunds) {
        this.tTrafficFunds = tTrafficFunds;
    }

    public String gettBonus() {
        return tBonus;
    }

    public void settBonus(String tBonus) {
        this.tBonus = tBonus;
    }

    public String getcDept() {
        return cDept;
    }

    public void setcDept(String cDept) {
        this.cDept = cDept;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public String getcNbr() {
        return cNbr;
    }

    public void setcNbr(String cNbr) {
        this.cNbr = cNbr;
    }

    public String getcTeacher() {
        return cTeacher;
    }

    public void setcTeacher(String cTeacher) {
        this.cTeacher = cTeacher;
    }

    public String getcApplyFunds() {
        return cApplyFunds;
    }

    public void setcApplyFunds(String cApplyFunds) {
        this.cApplyFunds = cApplyFunds;
    }

    public String getcActualFunds() {
        return cActualFunds;
    }

    public void setcActualFunds(String cActualFunds) {
        this.cActualFunds = cActualFunds;
    }

    public String getcPhdFunds() {
        return cPhdFunds;
    }

    public void setcPhdFunds(String cPhdFunds) {
        this.cPhdFunds = cPhdFunds;
    }

    public String getcBonus() {
        return cBonus;
    }

    public void setcBonus(String cBonus) {
        this.cBonus = cBonus;
    }

    public String getcTrafficFunds() {
        return cTrafficFunds;
    }

    public void setcTrafficFunds(String cTrafficFunds) {
        this.cTrafficFunds = cTrafficFunds;
    }

    public String getcTotalFunds() {
        return cTotalFunds;
    }

    public void setcTotalFunds(String cTotalFunds) {
        this.cTotalFunds = cTotalFunds;
    }

    public String gettTotalFunds() {
        return tTotalFunds;
    }

    public void settTotalFunds(String tTotalFunds) {
        this.tTotalFunds = tTotalFunds;
    }

    /**
     * 批次管理页面
     */

    private List<TermManagerViewObject> allTerms;

    private TermManagerViewObject currentTerm;

    private Integer termIndex;

    /**
     * 批次查询条件
     */

    private String termName;

    private String startTime;

    private String endTime;

    public List<TermManagerViewObject> getOldTerms() {
        return oldTerms;
    }

    public void setOldTerms(List<TermManagerViewObject> oldTerms) {
        this.oldTerms = oldTerms;
    }

    private List<TermManagerViewObject> oldTerms;

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    private String totalMoney;

    /**
     * 任务类别管理页面
     */

    private List<TAMSIssueType> allIssueTypes;
    private TAMSIssueType issueType;
    private Integer issueIndex; // issue=task,index用于区分新建还是编辑操作

    /**
     * 课程类别管理页面
     */

    private List<CMCourseClassification> allClassifications;

    private CMCourseClassification classification;

    private Integer courseClassificationIndex;


    /**
     * 助教类别/薪酬管理页面
     */

    private List<TAMSTaCategory> allTaCategories;
    private TAMSTaCategory oldTaCategory;
    private Integer taIndex;
    /**
     * 课程负责人管理页面
     */
    private String courseNm;
    private String courseNmb;
    private String courseManager;
    private String InstructorCode;
    private List<CourseManagerViewObject> courseManagerViewObjects;
    private String searchCourseNm;
    private String searchCourseNmb;
    private String searchCourseManager;
    private String searchCourseInsCode;
    private CourseManagerViewObject selectedCourseManagerObject;
    private Integer courseManagerIndex;


    /**
     * 页面错误信息
     */
    private String errMsg;

    /**
     * 角色权限相关页面
     */

    private List<KRIM_ROLE_T> RMPkrimRoleTs;
    private KRIM_ROLE_T RMPkrimRoleT;
    private List<KRIM_PERM_T> RMPkrimPermTs;
    private String permissionNM;
    private String permissionContent;
    private String permissionStatus;
    private Integer permissionIndex;

    /**
     * 用户角色管理页面
     */

    private List<KRIM_ROLE_T> URMPkrimRoleTs;
    private List<UTInstructor> URMutInstructors;
    private UTInstructor URMutInstructor;
    private String URMsearchDepartmentId;

    /**
     *
     * 时间设置页面的类型名称，时间输入框
     */
    private String timeType;
    private String startTimeSet;
    private String endTimeSet;
    private List<TAMSTimeSettings> timeSettingsList;

   /*
   工作流类别
    */
   private String workFlowSearchBtn;//查询输入框

    public List<TAMSTimeSettings> getTimeSettingsList() {
        return timeSettingsList;
    }

    public void setTimeSettingsList(List<TAMSTimeSettings> timeSettingsList) {
        this.timeSettingsList = timeSettingsList;
    }
    private String settingsTime;

    public String getStartTimeSet() {
        return startTimeSet;
    }

    public void setStartTimeSet(String startTimeSet) {
        this.startTimeSet = startTimeSet;
    }

    public String getEndTimeSet() {
        return endTimeSet;
    }

    public void setEndTimeSet(String endTimeSet) {
        this.endTimeSet = endTimeSet;
    }

    public String getSettingsTime() {
        return settingsTime;
    }

    public void setSettingsTime(String settingsTime) {
        this.settingsTime = settingsTime;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public List<SessionFundingViewObject> getPreviousSessionFundings() {
        return previousSessionFundings;
    }

    public void setPreviousSessionFundings(List<SessionFundingViewObject> previousSessionFundings) {
        this.previousSessionFundings = previousSessionFundings;
    }

    public List<DepartmentFundingViewObject> getDepartmentCurrFundings() {
        return departmentCurrFundings;
    }

    /**
     * 通过input中转站将一个用于区分当前修改的到底是哪个tab的flag传到后台。
     * TAB_FLAG_SCHOOL：学校tab
     * TAB_FLAG_DEPARTMENT：学院tab
     * TAB_FLAG_CLASS：课程(教学班？)tab
     */
    public String getCurTabFlag() {
        return curTabFlag;
    }

    public void setCurTabFlag(String curTabFlag) {
        this.curTabFlag = curTabFlag;
    }

    public void setDepartmentCurrFundings(List<DepartmentFundingViewObject> departmentCurrFundings) {
        this.departmentCurrFundings = departmentCurrFundings;
    }

    public List<DepartmentFundingViewObject> getDepartmentPreFundings() {
        return departmentPreFundings;
    }

    public void setDepartmentPreFundings(List<DepartmentFundingViewObject> departmentPreFundings) {
        this.departmentPreFundings = departmentPreFundings;
    }

    public List<SessionFundingViewObject> getSessionFundings() {
        return sessionFundings;
    }

    public void setSessionFundings(List<SessionFundingViewObject> sessionFundings) {
        this.sessionFundings = sessionFundings;
    }

    public List<ClassFundingViewObject> getClassFundings() {
        return classFundings;
    }

    public void setClassFundings(List<ClassFundingViewObject> classFundings) {
        this.classFundings = classFundings;
    }

    public String getPieChartsNameValuePairs() {
        return pieChartsNameValuePairs;
    }

    public void setPieChartsNameValuePairs(String pieChartsNameValuePairs) {
        this.pieChartsNameValuePairs = pieChartsNameValuePairs;
    }

    public String getWorkflowRelationTable() {
        return workflowRelationTable;
    }

    public void setWorkflowRelationTable(String workflowRelationTable) {
        this.workflowRelationTable = workflowRelationTable;
    }

    public Integer getTermIndex() {
        return termIndex;
    }

    public void setTermIndex(Integer termIndex) {
        this.termIndex = termIndex;
    }

    public TermManagerViewObject getCurrentTerm() {
        return currentTerm;
    }

    public void setCurrentTerm(TermManagerViewObject currentTerm) {
        this.currentTerm = currentTerm;
    }

    public Integer getIssueIndex() {
        return issueIndex;
    }

    public void setIssueIndex(Integer issueIndex) {
        this.issueIndex = issueIndex;
    }

    public Integer getTaIndex() {
        return taIndex;
    }

    public void setTaIndex(Integer taIndex) {
        this.taIndex = taIndex;
    }

    public List<TermManagerViewObject> getAllTerms() {
        return allTerms;
    }

    public void setAllTerms(List<TermManagerViewObject> allTerms) {
        this.allTerms = allTerms;
    }

    public TAMSIssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(TAMSIssueType issueType) {
        this.issueType = issueType;
    }

    public List<TAMSIssueType> getAllIssueTypes() {
        return allIssueTypes;
    }

    public void setAllIssueTypes(List<TAMSIssueType> allIssueTypes) {
        this.allIssueTypes = allIssueTypes;
    }

    public TAMSTaCategory getOldTaCategory() {
        return oldTaCategory;
    }

    public void setOldTaCategory(TAMSTaCategory oldTaCategory) {
        this.oldTaCategory = oldTaCategory;
    }

    public List<TAMSTaCategory> getAllTaCategories() {
        return allTaCategories;
    }

    public void setAllTaCategories(List<TAMSTaCategory> allTaCategories) {
        this.allTaCategories = allTaCategories;
    }

    public CMCourseClassification getClassification() {
        return classification;
    }

    public void setClassification(CMCourseClassification classification) {
        this.classification = classification;
    }

    public List<CMCourseClassification> getAllClassifications() {
        return allClassifications;
    }

    public void setAllClassifications(List<CMCourseClassification> allClassifications) {
        this.allClassifications = allClassifications;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<KRIM_ROLE_T> getRMPkrimRoleTs() {
        return RMPkrimRoleTs;
    }

    public void setRMPkrimRoleTs(List<KRIM_ROLE_T> RMPkrimRoleTs) {
        this.RMPkrimRoleTs = RMPkrimRoleTs;
    }

    public KRIM_ROLE_T getRMPkrimRoleT() {
        return RMPkrimRoleT;
    }

    public void setRMPkrimRoleT(KRIM_ROLE_T RMPkrimRoleT) {
        this.RMPkrimRoleT = RMPkrimRoleT;
    }

    public List<KRIM_PERM_T> getRMPkrimPermTs() {
        return RMPkrimPermTs;
    }

    public void setRMPkrimPermTs(List<KRIM_PERM_T> RMPkrimPermTs) {
        this.RMPkrimPermTs = RMPkrimPermTs;
    }

    public List<KRIM_ROLE_T> getURMPkrimRoleTs() {
        return URMPkrimRoleTs;
    }

    public void setURMPkrimRoleTs(List<KRIM_ROLE_T> URMPkrimRoleTs) {
        this.URMPkrimRoleTs = URMPkrimRoleTs;
    }

    public List<UTInstructor> getURMutInstructors() {
        return URMutInstructors;
    }

    public void setURMutInstructors(List<UTInstructor> URMutInstructors) {
        this.URMutInstructors = URMutInstructors;
    }

    public UTInstructor getURMutInstructor() {
        return URMutInstructor;
    }

    public void setURMutInstructor(UTInstructor URMutInstructor) {
        this.URMutInstructor = URMutInstructor;
    }

    public String getURMsearchDepartmentId() {
        return URMsearchDepartmentId;
    }

    public void setURMsearchDepartmentId(String URMsearchDepartmentId) {
        this.URMsearchDepartmentId = URMsearchDepartmentId;
    }

    public String getCourseNm() {
        return courseNm;
    }

    public void setCourseNm(String courseNm) {
        this.courseNm = courseNm;
    }

    public String getCourseNmb() {
        return courseNmb;
    }

    public void setCourseNmb(String courseNmb) {
        this.courseNmb = courseNmb;
    }

    public String getCourseManager() {
        return courseManager;
    }

    public void setCourseManager(String courseManager) {
        this.courseManager = courseManager;
    }

    public String getInstructorCode() {
        return InstructorCode;
    }

    public void setInstructorCode(String instructorCode) {
        InstructorCode = instructorCode;
    }

    public List<CourseManagerViewObject> getCourseManagerViewObjects() {
        return courseManagerViewObjects;
    }

    public void setCourseManagerViewObjects(List<CourseManagerViewObject> courseManagerViewObjects) {
        this.courseManagerViewObjects = courseManagerViewObjects;
    }

    public String getSearchCourseNm() {
        return searchCourseNm;
    }

    public void setSearchCourseNm(String searchCourseNm) {
        this.searchCourseNm = searchCourseNm;
    }

    public String getSearchCourseNmb() {
        return searchCourseNmb;
    }

    public void setSearchCourseNmb(String searchCourseNmb) {
        this.searchCourseNmb = searchCourseNmb;
    }

    public String getSearchCourseManager() {
        return searchCourseManager;
    }

    public void setSearchCourseManager(String searchCourseManager) {
        this.searchCourseManager = searchCourseManager;
    }

    public String getSearchCourseInsCode() {
        return searchCourseInsCode;
    }

    public void setSearchCourseInsCode(String searchCourseInsCode) {
        this.searchCourseInsCode = searchCourseInsCode;
    }

    public CourseManagerViewObject getSelectedCourseManagerObject() {
        return selectedCourseManagerObject;
    }

    public void setSelectedCourseManagerObject(CourseManagerViewObject selectedCourseManagerObject) {
        this.selectedCourseManagerObject = selectedCourseManagerObject;
    }

    public Integer getCourseManagerIndex() {
        return courseManagerIndex;
    }

    public void setCourseManagerIndex(Integer courseManagerIndex) {
        this.courseManagerIndex = courseManagerIndex;
    }

    public String getPermissionNM() {
        return permissionNM;
    }

    public void setPermissionNM(String permissionNM) {
        this.permissionNM = permissionNM;
    }

    public String getPermissionContent() {
        return permissionContent;
    }

    public void setPermissionContent(String permissionContent) {
        this.permissionContent = permissionContent;
    }

    public String getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(String permissionStatus) {
        this.permissionStatus = permissionStatus;
    }

    public Integer getPermissionIndex() {
        return permissionIndex;
    }

    public void setPermissionIndex(Integer permissionIndex) {
        this.permissionIndex = permissionIndex;
    }

    public Integer getCourseClassificationIndex() {
        return courseClassificationIndex;
    }

    public void setCourseClassificationIndex(Integer courseClassificationIndex) {
        this.courseClassificationIndex = courseClassificationIndex;
    }

    public String getsTimes() {
        return sTimes;
    }

    public void setsTimes(String sTimes) {
        this.sTimes = sTimes;
    }

    public String getsPreFunds() {
        return sPreFunds;
    }

    public void setsPreFunds(String sPreFunds) {
        this.sPreFunds = sPreFunds;
    }

    public String getsApplyFunds() {
        return sApplyFunds;
    }

    public void setsApplyFunds(String sApplyFunds) {
        this.sApplyFunds = sApplyFunds;
    }

    public String getsApprovalFunds() {
        return sApprovalFunds;
    }

    public void setsApprovalFunds(String sApprovalFunds) {
        this.sApprovalFunds = sApprovalFunds;
    }

    public String getsAddingFunds() {
        return sAddingFunds;
    }

    public void setsAddingFunds(String sAddingFunds) {
        this.sAddingFunds = sAddingFunds;
    }

    public String getsRewardFunds() {
        return sRewardFunds;
    }

    public void setsRewardFunds(String sRewardFunds) {
        this.sRewardFunds = sRewardFunds;
    }

    public String getsTrafficFunds() {
        return sTrafficFunds;
    }

    public void setsTrafficFunds(String sTrafficFunds) {
        this.sTrafficFunds = sTrafficFunds;
    }

    public String getsTotalFunds() {
        return sTotalFunds;
    }

    public void setsTotalFunds(String sTotalFunds) {
        this.sTotalFunds = sTotalFunds;
    }

    public String getdTimes() {
        return dTimes;
    }

    public void setdTimes(String dTimes) {
        this.dTimes = dTimes;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getdPreFunds() {
        return dPreFunds;
    }

    public void setdPreFunds(String dPreFunds) {
        this.dPreFunds = dPreFunds;
    }

    public String getdApplyFunds() {
        return dApplyFunds;
    }

    public void setdApplyFunds(String dApplyFunds) {
        this.dApplyFunds = dApplyFunds;
    }

    public String getdApprovalFunds() {
        return dApprovalFunds;
    }

    public void setdApprovalFunds(String dApprovalFunds) {
        this.dApprovalFunds = dApprovalFunds;
    }

    public String getdAddingFunds() {
        return dAddingFunds;
    }

    public void setdAddingFunds(String dAddingFunds) {
        this.dAddingFunds = dAddingFunds;
    }

    public String getdRewardFunds() {
        return dRewardFunds;
    }

    public void setdRewardFunds(String dRewardFunds) {
        this.dRewardFunds = dRewardFunds;
    }

    public String getdTrafficFunds() {
        return dTrafficFunds;
    }

    public void setdTrafficFunds(String dTrafficFunds) {
        this.dTrafficFunds = dTrafficFunds;
    }

    public String getdTotalFunds() {
        return dTotalFunds;
    }

    public void setdTotalFunds(String dTotalFunds) {
        this.dTotalFunds = dTotalFunds;
    }

    public List<TaFundingViewObject> getTaFunding() {
        return taFunding;
    }

    public void setTaFunding(List<TaFundingViewObject> taFunding) {
        this.taFunding = taFunding;
    }

    public List<DetailFundingViewObject> getDetailFunding() {
        return detailFunding;
    }

    public void setDetailFunding(List<DetailFundingViewObject> detailFunding) {
        this.detailFunding = detailFunding;
    }

    public String getWorkFlowSearchBtn() {
        return workFlowSearchBtn;
    }

    public void setWorkFlowSearchBtn(String workFlowSearchBtn) {
        this.workFlowSearchBtn = workFlowSearchBtn;
    }
}
