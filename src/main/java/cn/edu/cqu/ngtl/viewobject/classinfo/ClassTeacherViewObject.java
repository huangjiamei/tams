package cn.edu.cqu.ngtl.viewobject.classinfo;

import javax.persistence.Transient;

/**
 * Created by tangjing on 16-10-15.
 * Latest modified on 2016-10-17
 */
public class ClassTeacherViewObject {

    private String id;

    private boolean checked;

    private String departmentName;

    private String courseName;

    private String courseCode;

    private String classNumber;

    private String instructorName;

    private String status;

    private String order;

    private String appFunds;

    private String taCount;

    private String classType;

    /**
     * 暂时缺失的参数
     */
    private String workTime;

    private String allowance;

    private String protocalAssistantNumber;



    private String studentCounts;

    private String classCounts;

    private String instructorCounts;

    /** End */

    private String courseCredit;

    private String courseHour;

    private String isRequired;

    private String courseClassification;

    private String sessionYear;

    private String programName;


    @Transient
    public static String[] getAttrTittles(){
        String[] attrTittles={
                "课程名称"
                ,"课程代码"
                ,"教学班"
                ,"教师"
                ,"耗费工时"
                ,"学院"
                ,"状态"
        };
        return attrTittles;
    }
    @Transient
    public String[] getContents(){
        String[] contents=new String[7];
        contents[0]=getCourseName();
        contents[1]=getCourseCode();
        contents[2]=getClassNumber();
        contents[3]=getInstructorName();
        contents[4]=getWorkTime();
        contents[5]=getDepartmentName();
        contents[6]=getStatus();
        return contents;
    }

    public String getTaCount() {
        return taCount;
    }

    public void setTaCount(String taCount) {
        this.taCount = taCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getAllowance() {
        return allowance;
    }

    public void setAllowance(String allowance) {
        this.allowance = allowance;
    }

    public String getProtocalAssistantNumber() {
        return protocalAssistantNumber;
    }

    public void setProtocalAssistantNumber(String protocalAssistantNumber) {
        this.protocalAssistantNumber = protocalAssistantNumber;
    }

    public String getStudentCounts() {
        return studentCounts;
    }

    public void setStudentCounts(String studentCounts) {
        this.studentCounts = studentCounts;
    }

    public String getClassCounts() {
        return classCounts;
    }

    public void setClassCounts(String classCounts) {
        this.classCounts = classCounts;
    }

    public String getInstructorCounts() {
        return instructorCounts;
    }

    public void setInstructorCounts(String instructorCounts) {
        this.instructorCounts = instructorCounts;
    }

    public String getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        this.courseCredit = courseCredit;
    }

    public String getCourseHour() {
        return courseHour;
    }

    public void setCourseHour(String courseHour) {
        this.courseHour = courseHour;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public String getCourseClassification() {
        return courseClassification;
    }

    public void setCourseClassification(String courseClassification) {
        this.courseClassification = courseClassification;
    }

    public String getSessionYear() {
        return sessionYear;
    }

    public void setSessionYear(String sessionYear) {
        this.sessionYear = sessionYear;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getAppFunds() {
        return appFunds;
    }

    public void setAppFunds(String appFunds) {
        this.appFunds = appFunds;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
}
