package cn.edu.cqu.ngtl.form.classmanagement;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassEvaluation;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTeachCalendar;
import cn.edu.cqu.ngtl.form.BaseForm;
import cn.edu.cqu.ngtl.viewobject.classinfo.*;
import cn.edu.cqu.ngtl.viewobject.common.FileViewObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by awake on 2016-10-21.
 */
public class ClassInfoForm extends BaseForm implements Serializable {

    /**
     * 课程详细操作需要用到的id
     */
    private String currClassId;

    /**
     * 教学活动相关
     */
    private List<TeachCalendarViewObject> allActivities;

    /**
     * 教学日历相关
     */

    private String totalElapsedTime;

    private TAMSTeachCalendar teachCalendar;

    private String addTeachCTime;//新建日历的时间控件

    private TeachCalendarViewObject currentCalendarInfo;

    private List<FileViewObject> calendarFiles;

   /*
    助教申报页面
    */
   private List<TeachCalendarViewObject> allCalendar;


    /**
     * 班级管理隐藏搜索框
     *
     **/
    private String condDepartmentName;
    private String condCourseName;
    private String hintCourseName = "{[\"C++\",\"编译原理\",\"操作系统\"]}";
    private String condCourseCode;
    private String condClassNumber;
    private String condInstructorName;
    private String courseWorkTime;
    private String courseStatus;



    private String condJudgeStatus;
    private String condCourseHour;
    private String condCourseCredit;
    private String condIsRequired;
    private String condCourseClassification;
    private String condSessionYear;
    private String condProgramName;

    /*
    课程页面
     */
    private String returnReason;//驳回理由
    private String approveReason;//审批理由
    private String returnReasonOptionFinder;//驳回optionfinder
    private String approveReasonOptionFinder;//审批optionfinder

    private List<ClassTeacherViewObject> classList;

    private ClassDetailInfoViewObject detailInfoViewObject;

    /**
     * 学生申请助教页面相关
     */

    private ApplyAssistantViewObject applyAssistantViewObject;

    private String applyReason;

    /**
     * 老师提交申请页面相关
     */

    private ClassTaApplyViewObject applyViewObject;
    private String totalBudget;
    private List<TAMSClassEvaluation> classEvaluations;

    public List<TAMSClassEvaluation> getClassEvaluations() {
        return classEvaluations;
    }


    /*
    助教管理页面
     */
    private List<MyTaViewObject> allMyTa;
    private List<MyTaViewObject> allApplication;

    private MyTaViewObject selectedTa = new MyTaViewObject(); // 添加助教申请人时会用到，在搜索得到的助教列表中点击'查看'，把查询得到的ta数据放到这个变量中
    private List<MyTaViewObject> conditionTAList=new ArrayList<>(); // 查询时返回符合条件的talist，存储到这个list中
    private String StudentName;
    private String StudentNumber;

    public MyTaViewObject getSelectedTa() {
        return selectedTa;
    }

    public void setSelectedTa(MyTaViewObject selectedTa) {
        this.selectedTa = selectedTa;
    }

    public List<MyTaViewObject> getConditionTAList() {
        return conditionTAList;
    }

    public void setConditionTAList(List<MyTaViewObject> conditionTAList) {
        this.conditionTAList = conditionTAList;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentNumber() {
        return StudentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        StudentNumber = studentNumber;
    }

    public List<FileViewObject> getCalendarFiles() {
        return calendarFiles;
    }

    public void setCalendarFiles(List<FileViewObject> calendarFiles) {
        this.calendarFiles = calendarFiles;
    }

    public TeachCalendarViewObject getCurrentCalendarInfo() {
        return currentCalendarInfo;
    }

    public void setCurrentCalendarInfo(TeachCalendarViewObject currentCalendarInfo) {
        this.currentCalendarInfo = currentCalendarInfo;
    }

    public void setClassEvaluations(List<TAMSClassEvaluation> classEvaluations) {
        this.classEvaluations = classEvaluations;
    }

    public String getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(String totalBudget) {
        this.totalBudget = totalBudget;
    }

    public String getCurrClassId() {
        return currClassId;
    }

    public void setCurrClassId(String currClassId) {
        this.currClassId = currClassId;
    }

    public List<TeachCalendarViewObject> getAllActivities() {
        return allActivities;
    }

    public void setAllActivities(List<TeachCalendarViewObject> allActivities) {
        this.allActivities = allActivities;
    }

    public List<TeachCalendarViewObject> getAllCalendar() {
        return allCalendar;
    }

    public void setAllCalendar(List<TeachCalendarViewObject> allCalendar) {
        this.allCalendar = allCalendar;
    }

    public String getTotalElapsedTime() {
        return totalElapsedTime;
    }

    public void setTotalElapsedTime(String totalElapsedTime) {
        this.totalElapsedTime = totalElapsedTime;
    }

    public TAMSTeachCalendar getTeachCalendar() {
        return teachCalendar;
    }

    public void setTeachCalendar(TAMSTeachCalendar teachCalendar) {
        this.teachCalendar = teachCalendar;
    }

    public String getCondDepartmentName() {
        return condDepartmentName;
    }

    public void setCondDepartmentName(String condDepartmentName) {
        this.condDepartmentName = condDepartmentName;
    }

    public String getCondCourseName() {
        return condCourseName;
    }

    public void setCondCourseName(String condCourseName) {
        this.condCourseName = condCourseName;
    }

    public String getHintCourseName() {
        return hintCourseName;
    }

    public void setHintCourseName(String hintCourseName) {
        this.hintCourseName = hintCourseName;
    }

    public String getCondCourseCode() {
        return condCourseCode;
    }

    public void setCondCourseCode(String condCourseCode) {
        this.condCourseCode = condCourseCode;
    }

    public String getCondClassNumber() {
        return condClassNumber;
    }

    public void setCondClassNumber(String condClassNumber) {
        this.condClassNumber = condClassNumber;
    }

    public String getCondInstructorName() {
        return condInstructorName;
    }

    public void setCondInstructorName(String condInstructorName) {
        this.condInstructorName = condInstructorName;
    }

    public String getCondJudgeStatus() {
        return condJudgeStatus;
    }

    public void setCondJudgeStatus(String condJudgeStatus) {
        this.condJudgeStatus = condJudgeStatus;
    }

    public String getCondCourseHour() {
        return condCourseHour;
    }

    public void setCondCourseHour(String condCourseHour) {
        this.condCourseHour = condCourseHour;
    }

    public String getCondCourseCredit() {
        return condCourseCredit;
    }

    public void setCondCourseCredit(String condCourseCredit) {
        this.condCourseCredit = condCourseCredit;
    }

    public String getCondIsRequired() {
        return condIsRequired;
    }

    public void setCondIsRequired(String condIsRequired) {
        this.condIsRequired = condIsRequired;
    }

    public String getCondCourseClassification() {
        return condCourseClassification;
    }

    public void setCondCourseClassification(String condCourseClassification) {
        this.condCourseClassification = condCourseClassification;
    }

    public String getCondSessionYear() {
        return condSessionYear;
    }

    public void setCondSessionYear(String condSessionYear) {
        this.condSessionYear = condSessionYear;
    }

    public String getCondProgramName() {
        return condProgramName;
    }

    public void setCondProgramName(String condProgramName) {
        this.condProgramName = condProgramName;
    }

    public ClassTaApplyViewObject getApplyViewObject() {
        return applyViewObject;
    }

    public void setApplyViewObject(ClassTaApplyViewObject applyViewObject) {
        this.applyViewObject = applyViewObject;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public ApplyAssistantViewObject getApplyAssistantViewObject() {
        return applyAssistantViewObject;
    }

    public void setApplyAssistantViewObject(ApplyAssistantViewObject applyAssistantViewObject) {
        this.applyAssistantViewObject = applyAssistantViewObject;
    }

    public List<ClassTeacherViewObject> getClassList() {
        return classList;
    }

    public void setClassList(List<ClassTeacherViewObject> classList) {
        this.classList = classList;
    }

    public ClassDetailInfoViewObject getDetailInfoViewObject() {
        return detailInfoViewObject;
    }

    public void setDetailInfoViewObject(ClassDetailInfoViewObject detailInfoViewObject) {
        this.detailInfoViewObject = detailInfoViewObject;
    }

    public String getAddTeachCTime() {
        return addTeachCTime;
    }

    public void setAddTeachCTime(String addTeachCTime) {
        this.addTeachCTime = addTeachCTime;
    }

    public List<MyTaViewObject> getAllMyTa() {
        return allMyTa;
    }

    public void setAllMyTa(List<MyTaViewObject> allMyTa) {
        this.allMyTa = allMyTa;
    }

    public List<MyTaViewObject> getAllApplication() {
        return allApplication;
    }

    public void setAllApplication(List<MyTaViewObject> allApplication) {
        this.allApplication = allApplication;
    }

    public String getCourseWorkTime() {
        return courseWorkTime;
    }

    public void setCourseWorkTime(String courseWorkTime) {
        this.courseWorkTime = courseWorkTime;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getApproveReason() {
        return approveReason;
    }

    public void setApproveReason(String approveReason) {
        this.approveReason = approveReason;
    }

    public String getReturnReasonOptionFinder() {
        return returnReasonOptionFinder;
    }

    public void setReturnReasonOptionFinder(String returnReasonOptionFinder) {
        this.returnReasonOptionFinder = returnReasonOptionFinder;
    }

    public String getApproveReasonOptionFinder() {
        return approveReasonOptionFinder;
    }

    public void setApproveReasonOptionFinder(String approveReasonOptionFinder) {
        this.approveReasonOptionFinder = approveReasonOptionFinder;
    }

}
