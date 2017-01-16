package cn.edu.cqu.ngtl.viewobject.adminInfo;

import javax.persistence.Transient;

/**
 * Created by awake on 2016/11/29.
 */
public class TaFundingViewObject {

    private String sessionName;

    private String classId;

    private String stuId;

    private String taName;

    private String departmentName;

    private String taType;

    private String classNbr; //converter中会用到

    private String instrucotrName;

    private String courseName;

    private String courseCode;

    private String assignedFunding;

    private String phdFunding;

    private String travelSubsidy;

    private String bonus;

    private String total;

    private boolean checked;


    @Transient
    public static String[] getAttrTittles(){
        String[] attrTittles={
                "学院","助教","学号","助教类别","课程","课程代码",
                "教学班号","分配经费","博士津贴","交通补贴","奖励经费","总经费"

        };
        return attrTittles;
    }

    @Transient
    public String[] getContents(){
        String[] contents=new String[12];
        contents[0]=getDepartmentName();
        contents[1]=getTaName();
        contents[2]=getStuId();
        contents[3]=getTaType();
        contents[4]=getCourseName();
        contents[5]=getCourseCode();
        contents[6]=getClassNbr();
        contents[7]=getAssignedFunding();
        contents[8]=getPhdFunding();
        contents[9]=getTravelSubsidy();
        contents[10]=getBonus();
        contents[11]=getTotal();
        return contents;
    }

    public String getClassNbr() {
        return classNbr;
    }

    public void setClassNbr(String classNbr) {
        this.classNbr = classNbr;
    }

    public String getInstrucotrName() {
        return instrucotrName;
    }

    public void setInstrucotrName(String instrucotrName) {
        this.instrucotrName = instrucotrName;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }


    public String getTaName() {
        return taName;
    }

    public void setTaName(String taName) {
        this.taName = taName;
    }

    public String getTaType() {
        return taType;
    }

    public void setTaType(String taType) {
        this.taType = taType;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public String getTravelSubsidy() {
        return travelSubsidy;
    }

    public void setTravelSubsidy(String travelSubsidy) {
        this.travelSubsidy = travelSubsidy;
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

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
