package cn.edu.cqu.ngtl.viewobject.adminInfo;

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
}
