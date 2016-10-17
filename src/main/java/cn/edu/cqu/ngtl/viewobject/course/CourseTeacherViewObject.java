package cn.edu.cqu.ngtl.viewobject.course;

import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;

import java.util.List;

/**
 * Created by tangjing on 16-10-15.
 */
public class CourseTeacherViewObject {

    private String departmentName;

    private String courseName;

    private String courseCode;

    private List<UTInstructor> instructors;

    private Integer courseCredit;

    private String courseHour;

    private String isRequired;

    private String courseClassification;

    private String sessionYear;

    private String programName;

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

    public List<UTInstructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<UTInstructor> instructors) {
        this.instructors = instructors;
    }

    public Integer getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(Integer courseCredit) {
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
