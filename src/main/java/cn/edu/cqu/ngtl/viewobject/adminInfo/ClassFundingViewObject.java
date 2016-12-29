package cn.edu.cqu.ngtl.viewobject.adminInfo;

import javax.persistence.Transient;

/**
 * Created by liusijia on 2016/11/16.
 */
public class ClassFundingViewObject {

    String department;

    String courseName;

    String courseCode;

    String classNumber;

    //converter中会用到
    String classId;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    String instructorName;

    String planFunding;

    String assignedFunding;

    String phdFunding;

    String applyFunding;

    String bonus;

    String total;

    String travelSubsidy;

    @Transient
    public static String[] getAttrTittles(){
        String[] attrTittles={
                "学院","课程","课程代码","教学班号","教师",
                "申报经费","批准经费","博士津贴","奖励津贴",
                "交通补贴","总经费"

        };
        return attrTittles;
    }

    @Transient
    public String[] getContents(){
        String[] contents=new String[11];
        contents[0]=getDepartment();
        contents[1]=getCourseName();
        contents[2]=getCourseCode();
        contents[3]=getClassNumber();
        contents[4]=getInstructorName();
        contents[5]=getApplyFunding();
        contents[6]=getTotal();
        contents[7]=getPhdFunding();
        contents[8]=getBonus();
        contents[9]=getTravelSubsidy();
        contents[10]=getTotal();
        return contents;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getPlanFunding() {
        return planFunding;
    }

    public void setPlanFunding(String planFunding) {
        this.planFunding = planFunding;
    }

    public String getAssignedFunding() {
        return assignedFunding;
    }

    public void setAssignedFunding(String assignedFunding) {
        this.assignedFunding = assignedFunding;
    }

    public String getPhdFunding() {
        return phdFunding;
    }

    public void setPhdFunding(String phdFunding) {
        this.phdFunding = phdFunding;
    }

    public String getApplyFunding() {
        return applyFunding;
    }

    public void setApplyFunding(String applyFunding) {
        this.applyFunding = applyFunding;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTravelSubsidy() {
        return travelSubsidy;
    }

    public void setTravelSubsidy(String travelSubsidy) {
        this.travelSubsidy = travelSubsidy;
    }
}
