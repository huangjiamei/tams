package cn.edu.cqu.ngtl.viewobject.tainfo;

/**
 * Created by damei on 16/11/29.
 */
public class WorkBenchViewObject {
    private String dept;
    private String courseName;
    private String courseCode;
    private String classNbr;
    private String teacher;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
/*
    public List<UTInstructor> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<UTInstructor> teachers) {
        this.teachers = teachers;
    }


    private List<UTInstructor> teachers;
    */
    private String hours;
    private String major;
    private String status;

    private String classId;
    private String taId;


    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
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

    public String getClassNbr() {
        return classNbr;
    }

    public void setClassNbr(String classNbr) {
        this.classNbr = classNbr;
    }



    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTaId() {
        return taId;
    }

    public void setTaId(String taId) {
        this.taId = taId;
    }
}
