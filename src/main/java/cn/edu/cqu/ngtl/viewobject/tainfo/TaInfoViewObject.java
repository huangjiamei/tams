package cn.edu.cqu.ngtl.viewobject.tainfo;

/**
 * Created by tangjing on 16-11-1.
 */
public class TaInfoViewObject {

    //id用于和后台进行交互，不要把id用于前端显示
    private String id;

    private String classid;

    private String taId;

    private boolean checkBox;
    //课程名称
    private String courseName;
    //课程代码
    private String courseCode;
    //教学班
    private String classNumber;
    //教师
    private String instructorName;
    //姓名
    private String taName;
    //学号
    private String taIDNumber;

    private String taGender;

    private String taMajorName;
    //学历
    private String taCategory;

    private String advisorName;

    private String contactPhone;

    private String vitality;
    //教师考核
    private String teacherAppraise;
    //学生考核
    private String stuAppraise;

    private String statusId;
    //评优
    private String status;

    private String applicationReason;
    //成绩
    private String score;

    private String credit;

    private String deptName;

    public static String[] getAttrTittles() {
        String[] attrTittles={
                "姓名"
                ,"学号"
                ,"学历"
                ,"课程名称"
                ,"课程代码"
                ,"教学班"
                ,"教师"
                ,"教师考核"
                ,"学生考核"
                ,"成绩"
                ,"评优"
        };
        return attrTittles;
    }


    public String[] getContents() {
        String[] contents=new String[11];
        contents[0]=getTaName();
        contents[1]=getTaIDNumber();
        contents[2]=getTaMajorName();
        contents[3]=getCourseName();
        contents[4]=getCourseCode();
        contents[5]=getClassNumber();
        contents[6]=getInstructorName();
        contents[7]=getTeacherAppraise();
        contents[8]=getStuAppraise();
        contents[9]=getScore();
        contents[10]=getStatus();
        return contents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
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

    public String getTaName() {
        return taName;
    }

    public void setTaName(String taName) {
        this.taName = taName;
    }

    public String getTaIDNumber() {
        return taIDNumber;
    }

    public void setTaIDNumber(String taIDNumber) {
        this.taIDNumber = taIDNumber;
    }

    public String getTaGender() {
        return taGender;
    }

    public void setTaGender(String taGender) {
        this.taGender = taGender;
    }

    public String getTaMajorName() {
        return taMajorName;
    }

    public void setTaMajorName(String taMajorName) {
        this.taMajorName = taMajorName;
    }

    public String getAdvisorName() {
        return advisorName;
    }

    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getVitality() {
        return vitality;
    }

    public void setVitality(String vitality) {
        this.vitality = vitality;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getTaId() {
        return taId;
    }

    public void setTaId(String taId) {
        this.taId = taId;
    }

    public String getApplicationReason() {
        return applicationReason;
    }

    public void setApplicationReason(String applicationReason) {
        this.applicationReason = applicationReason;
    }

    public String getTeacherAppraise() {
        return teacherAppraise;
    }

    public void setTeacherAppraise(String teacherAppraise) {
        this.teacherAppraise = teacherAppraise;
    }

    public String getStuAppraise() {
        return stuAppraise;
    }

    public void setStuAppraise(String stuAppraise) {
        this.stuAppraise = stuAppraise;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getTaCategory() {
        return taCategory;
    }

    public void setTaCategory(String taCategory) {
        this.taCategory = taCategory;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
