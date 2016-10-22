package cn.edu.cqu.ngtl.viewobject.course;

import javax.persistence.Transient;

/**
 * Created by tangjing on 16-10-15.
 * Latest modified on 2016-10-17
 */
public class ClassTeacherViewObject {

    private boolean checked;

    private String departmentName;

    private String courseName;

    private String courseCode;

    private String classNumber;

    private String instructorName;

    /**
     * 暂时缺失的参数
     */
    private String workTime;

    private String allowance;

    private String protocalAssistantNumber;

    private String judgeStatus;

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
                "序号"
                ,"姓名"
                ,"性别"
                ,"学号"
                ,"证件类型"
                ,"证件号码"
                ,"学历"
                ,"学制"
                ,"入学年份"
                ,"年级"
                ,"院系"
                ,"专业"
                ,"班级"
                ,"学籍所在校"
                ,"科目编号"
                ,"电子邮箱"
                ,"学籍所在校"
                ,"科目编号"
                ,"电子邮箱"
        };
        return attrTittles;
    }
    @Transient
    public String[] getContents(){
        String[] contents=new String[18];
        contents[0]=getDepartmentName();
        contents[1]=getCourseName();
        contents[2]=getCourseCode();
        contents[3]=getClassNumber();
        contents[4]=getInstructorName();
        contents[5]=getDepartmentName();
        contents[6]=getCourseName();
        contents[7]=getCourseCode();
        contents[8]=getClassNumber();
        contents[9]=getInstructorName();
        contents[10]=getDepartmentName();
        contents[11]=getCourseName();
        contents[12]=getCourseCode();
        contents[13]=getClassNumber();
        contents[14]=getInstructorName();
        contents[15]=getDepartmentName();
        contents[16]=getCourseName();
        contents[17]=getCourseCode();

        return contents;
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

    public String getJudgeStatus() {
        return judgeStatus;
    }

    public void setJudgeStatus(String judgeStatus) {
        this.judgeStatus = judgeStatus;
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
}
