package cn.edu.cqu.ngtl.form.adminmanagement;

import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PERM_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_T;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;
import cn.edu.cqu.ngtl.form.BaseForm;
import cn.edu.cqu.ngtl.viewobject.adminInfo.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by awake on 2016-10-21.
 */
public class AdminInfoForm extends BaseForm implements Serializable {

    /**
     * 时间类别管理页面
     */
    private List<TAMSTimeSettingType> allTimeTypes;

    private TAMSTimeSettingType timeSettingType;

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

    /**
     * 工作流添加/修改类别输入框
     * @return
     */
    private String workflowstatus;
    private String workfloworder;
    //用index来判断是添加还是修改
    private Integer index;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getWorkflowstatus() {
        return workflowstatus;
    }

    public void setWorkflowstatus(String workflowstatus) {
        this.workflowstatus = workflowstatus;
    }

    public String getWorkfloworder() {
        return workfloworder;
    }

    public void setWorkfloworder(String workfloworder) {
        this.workfloworder = workfloworder;
    }

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

    private String sessionFundingStatistics;

    private String sessionFundingTotalApproved;

    //学院当前学期经费饼状图
    private String pieChartsNameValuePairs;

    //批次历史经费柱状图
    private String histogram;

    public String getHistogram() {
        return histogram;
    }

    public void setHistogram(String histogram) {
        this.histogram = histogram;
    }

    private List<SessionFundingViewObject> sessionFundings;

    private List<SessionFundingViewObject> previousSessionFundings;

    private List<DepartmentFundingViewObject> departmentCurrFundings;

    private List<DepartmentFundingViewObject> departmentPreFundings;

    private List<ClassFundingViewObject> classFundings;

    private String classFundingStatistics;

    private String classFundingTotalApproved;

    private List<TaFundingViewObject> taFunding;

    private List<DetailFundingViewObject> detailFunding;

    private boolean springTerm;
    private boolean fallTerm;
    private boolean academicAffairManager;
    private boolean deptManager;

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
    private String departmentTimes;//批次
    private String departmentId;//学院
    private String departmentPreFunds;
    private String departmentApplyFunds;
    private String departmentApprovalFunds;
    private String departmentAddingFunds;
    private String departmentRewardFunds;
    private String departmentTrafficFunds;
    private String departmentTotalFunds;

    /**
     * 经费管理页面————学院历史经费，过滤器的输入框
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

    /**
     * 经费管理页面————经费明细，过滤器的输入框
     * @return
     */
    private String detailsName;
    private String detailsNumber;
    private String detailsBank;
    private String detailsBankNumber;
    private String detailsIDCard;
    private String detailsCourseName;
    private String detailsCourseCode;
    private String month1;
    private String month2;
    private String month3;
    private String month4;
    private String month5;
    private String month6;
    private String month7;
    private String month8;
    private String month9;
    private String month10;
    private String month11;
    private String month12;
    private String detailsTotalFunds;

    /*
    同步信息页面
     */
    private String systemHostType;
    private String systemHostIP;
    private String systemHostPort;
    private String systemDbName;
    private String systemDbUserName;
    private String systemDbPassword;
    private String test;
    private String checkboxesTest;
    private String ConnectMessage;

    public String getClassFundingStatistics() {
        return classFundingStatistics;
    }

    public void setClassFundingStatistics(String classFundingStatistics) {
        this.classFundingStatistics = classFundingStatistics;
    }

    public String getClassFundingTotalApproved() {
        return classFundingTotalApproved;
    }

    public void setClassFundingTotalApproved(String classFundingTotalApproved) {
        this.classFundingTotalApproved = classFundingTotalApproved;
    }

    public String getSessionFundingStatistics() {
        return sessionFundingStatistics;
    }

    public void setSessionFundingStatistics(String sessionFundingStatistics) {
        this.sessionFundingStatistics = sessionFundingStatistics;
    }

    public String getSessionFundingTotalApproved() {
        return sessionFundingTotalApproved;
    }

    public void setSessionFundingTotalApproved(String sessionFundingTotalApproved) {
        this.sessionFundingTotalApproved = sessionFundingTotalApproved;
    }

    public TAMSTimeSettingType getTimeSettingType() {
        return timeSettingType;
    }

    public void setTimeSettingType(TAMSTimeSettingType timeSettingType) {
        this.timeSettingType = timeSettingType;
    }

    public List<TAMSTimeSettingType> getAllTimeTypes() {
        return allTimeTypes;
    }

    public void setAllTimeTypes(List<TAMSTimeSettingType> allTimeTypes) {
        this.allTimeTypes = allTimeTypes;
    }

    public String getCheckboxesTest() {
        return checkboxesTest;
    }

    public void setCheckboxesTest(String checkboxesTest) {
        this.checkboxesTest = checkboxesTest;
    }

    public boolean isAcademicAffairManager() {
        return academicAffairManager;
    }

    public void setAcademicAffairManager(boolean academicAffairManager) {
        this.academicAffairManager = academicAffairManager;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getSystemHostType() {
        return systemHostType;
    }

    public void setSystemHostType(String systemHostType) {
        this.systemHostType = systemHostType;
    }

    public String getSystemHostIP() {
        return systemHostIP;
    }

    public void setSystemHostIP(String systemHostIP) {
        this.systemHostIP = systemHostIP;
    }

    public String getSystemHostPort() {
        return systemHostPort;
    }

    public void setSystemHostPort(String systemHostPort) {
        this.systemHostPort = systemHostPort;
    }

    public String getSystemDbName() {
        return systemDbName;
    }

    public void setSystemDbName(String systemDbName) {
        this.systemDbName = systemDbName;
    }

    public String getSystemDbUserName() {
        return systemDbUserName;
    }

    public void setSystemDbUserName(String systemDbUserName) {
        this.systemDbUserName = systemDbUserName;
    }

    public String getSystemDbPassword() {
        return systemDbPassword;
    }

    public void setSystemDbPassword(String systemDbPassword) {
        this.systemDbPassword = systemDbPassword;
    }

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
    private String instructorCode;
    private List<CourseManagerViewObject> courseManagerViewObjects;
    private String searchCourseNm;
    private String searchCourseNmb;
    private String searchCourseManager;
    private String searchCourseInsCode;
    private CourseManagerViewObject selectedCourseManagerObject;
    private Integer courseManagerIndex;
    private List<UTInstructor> instructorList;
    private List<UTInstructor> instructorExcelList;
    public List<UTInstructor> getInstructorExcelList() {
        return instructorExcelList;
    }

    public void setInstructorExcelList(List<UTInstructor> instructorExcelList) {
        this.instructorExcelList = instructorExcelList;
    }

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

    private String userRoleDepartmentName;//学院
    private String userRoleName;//姓名
    private String userRoleGender;//性别
    private String userRoleNumber;//工号
    private String userRoleIDNumber;//统一认证号



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
   private String workFlowCaStatus;//工作流状态
    private String workFlowCaRe;//工作流顺序

    public String getWorkFlowCaStatus() {
        return workFlowCaStatus;
    }

    public void setWorkFlowCaStatus(String workFlowCaStatus) {
        this.workFlowCaStatus = workFlowCaStatus;
    }

    public String getWorkFlowCaRe() {
        return workFlowCaRe;
    }

    public void setWorkFlowCaRe(String workFlowCaRe) {
        this.workFlowCaRe = workFlowCaRe;
    }

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
        return instructorCode;
    }

    public void setInstructorCode(String instructorCode) {
        instructorCode = instructorCode;
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

    public String getConnectMessage() {
        return ConnectMessage;
    }

    public void setConnectMessage(String connectMessage) {
        ConnectMessage = connectMessage;
    }

    public String getDepartmentTimes() {
        return departmentTimes;
    }

    public void setDepartmentTimes(String departmentTimes) {
        this.departmentTimes = departmentTimes;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentPreFunds() {
        return departmentPreFunds;
    }

    public void setDepartmentPreFunds(String departmentPreFunds) {
        this.departmentPreFunds = departmentPreFunds;
    }

    public String getDepartmentApplyFunds() {
        return departmentApplyFunds;
    }

    public void setDepartmentApplyFunds(String departmentApplyFunds) {
        this.departmentApplyFunds = departmentApplyFunds;
    }

    public String getDepartmentApprovalFunds() {
        return departmentApprovalFunds;
    }

    public void setDepartmentApprovalFunds(String departmentApprovalFunds) {
        this.departmentApprovalFunds = departmentApprovalFunds;
    }

    public String getDepartmentAddingFunds() {
        return departmentAddingFunds;
    }

    public void setDepartmentAddingFunds(String departmentAddingFunds) {
        this.departmentAddingFunds = departmentAddingFunds;
    }

    public String getDepartmentRewardFunds() {
        return departmentRewardFunds;
    }

    public void setDepartmentRewardFunds(String departmentRewardFunds) {
        this.departmentRewardFunds = departmentRewardFunds;
    }

    public String getDepartmentTrafficFunds() {
        return departmentTrafficFunds;
    }

    public void setDepartmentTrafficFunds(String departmentTrafficFunds) {
        this.departmentTrafficFunds = departmentTrafficFunds;
    }

    public String getDepartmentTotalFunds() {
        return departmentTotalFunds;
    }

    public void setDepartmentTotalFunds(String departmentTotalFunds) {
        this.departmentTotalFunds = departmentTotalFunds;
    }

    public String getDetailsName() {
        return detailsName;
    }

    public void setDetailsName(String detailsName) {
        this.detailsName = detailsName;
    }

    public String getDetailsNumber() {
        return detailsNumber;
    }

    public void setDetailsNumber(String detailsNumber) {
        this.detailsNumber = detailsNumber;
    }

    public String getDetailsBank() {
        return detailsBank;
    }

    public void setDetailsBank(String detailsBank) {
        this.detailsBank = detailsBank;
    }

    public String getDetailsBankNumber() {
        return detailsBankNumber;
    }

    public void setDetailsBankNumber(String detailsBankNumber) {
        this.detailsBankNumber = detailsBankNumber;
    }

    public String getDetailsIDCard() {
        return detailsIDCard;
    }

    public void setDetailsIDCard(String detailsIDCard) {
        this.detailsIDCard = detailsIDCard;
    }

    public String getDetailsCourseName() {
        return detailsCourseName;
    }

    public void setDetailsCourseName(String detailsCourseName) {
        this.detailsCourseName = detailsCourseName;
    }

    public String getDetailsCourseCode() {
        return detailsCourseCode;
    }

    public void setDetailsCourseCode(String detailsCourseCode) {
        this.detailsCourseCode = detailsCourseCode;
    }

    public String getMonth1() {
        return month1;
    }

    public void setMonth1(String month1) {
        this.month1 = month1;
    }

    public String getMonth2() {
        return month2;
    }

    public void setMonth2(String month2) {
        this.month2 = month2;
    }

    public String getMonth3() {
        return month3;
    }

    public void setMonth3(String month3) {
        this.month3 = month3;
    }

    public String getMonth4() {
        return month4;
    }

    public void setMonth4(String month4) {
        this.month4 = month4;
    }

    public String getMonth5() {
        return month5;
    }

    public void setMonth5(String month5) {
        this.month5 = month5;
    }

    public String getMonth6() {
        return month6;
    }

    public void setMonth6(String month6) {
        this.month6 = month6;
    }

    public String getMonth7() {
        return month7;
    }

    public void setMonth7(String month7) {
        this.month7 = month7;
    }

    public String getMonth8() {
        return month8;
    }

    public void setMonth8(String month8) {
        this.month8 = month8;
    }

    public String getMonth9() {
        return month9;
    }

    public void setMonth9(String month9) {
        this.month9 = month9;
    }

    public String getMonth10() {
        return month10;
    }

    public void setMonth10(String month10) {
        this.month10 = month10;
    }

    public String getMonth11() {
        return month11;
    }

    public void setMonth11(String month11) {
        this.month11 = month11;
    }

    public String getMonth12() {
        return month12;
    }

    public void setMonth12(String month12) {
        this.month12 = month12;
    }

    public String getDetailsTotalFunds() {
        return detailsTotalFunds;
    }

    public void setDetailsTotalFunds(String detailsTotalFunds) {
        this.detailsTotalFunds = detailsTotalFunds;
    }

    public boolean isSpringTerm() {
        return springTerm;
    }

    public void setSpringTerm(boolean springTerm) {
        this.springTerm = springTerm;
    }

    public boolean isFallTerm() {
        return fallTerm;
    }

    public void setFallTerm(boolean fallTerm) {
        this.fallTerm = fallTerm;
    }

    public String getUserRoleDepartmentName() {
        return userRoleDepartmentName;
    }

    public void setUserRoleDepartmentName(String userRoleDepartmentName) {
        this.userRoleDepartmentName = userRoleDepartmentName;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public String getUserRoleGender() {
        return userRoleGender;
    }

    public void setUserRoleGender(String userRoleGender) {
        this.userRoleGender = userRoleGender;
    }

    public String getUserRoleNumber() {
        return userRoleNumber;
    }

    public void setUserRoleNumber(String userRoleNumber) {
        this.userRoleNumber = userRoleNumber;
    }

    public String getUserRoleIDNumber() {
        return userRoleIDNumber;
    }

    public void setUserRoleIDNumber(String userRoleIDNumber) {
        this.userRoleIDNumber = userRoleIDNumber;
    }

    public boolean isDeptManager() {
        return deptManager;
    }

    public void setDeptManager(boolean deptManager) {
        this.deptManager = deptManager;
    }

    public List<UTInstructor> getInstructorList() {
        return instructorList;
    }

    public void setInstructorList(List<UTInstructor> instructorList) {
        this.instructorList = instructorList;
    }
}
