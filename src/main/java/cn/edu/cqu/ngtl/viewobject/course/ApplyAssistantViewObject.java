package cn.edu.cqu.ngtl.viewobject.course;

/**
 * Created by long2ice
 * Date on 2016/10/20
 */

//申请助教的页面
public class ApplyAssistantViewObject {

    private String username;
    private String studentId;
    //导师
    private String teacher;
    //本科专业
    private String ug_Major;
    //研究生专业
    private String g_Major;

    private String phone;
    private String classHour;
    //申请课程老师
    private String applyTeacher;
    //申请课程类型
    private String applyCourseType;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getUg_Major() {
        return ug_Major;
    }

    public void setUg_Major(String ug_Major) {
        this.ug_Major = ug_Major;
    }

    public String getG_Major() {
        return g_Major;
    }

    public void setG_Major(String g_Major) {
        this.g_Major = g_Major;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClassHour() {
        return classHour;
    }

    public void setClassHour(String classHour) {
        this.classHour = classHour;
    }

    public String getApplyTeacher() {
        return applyTeacher;
    }

    public void setApplyTeacher(String applyTeacher) {
        this.applyTeacher = applyTeacher;
    }

    public String getApplyCourseType() {
        return applyCourseType;
    }

    public void setApplyCourseType(String applyCourseType) {
        this.applyCourseType = applyCourseType;
    }
}
