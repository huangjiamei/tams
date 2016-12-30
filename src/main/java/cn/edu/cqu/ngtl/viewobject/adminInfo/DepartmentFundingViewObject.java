package cn.edu.cqu.ngtl.viewobject.adminInfo;

import java.beans.Transient;

/**
 * Created by wjy on 16-11-11.
 */
public class DepartmentFundingViewObject {

    String sessionName;

    String department;

    String planFunding;

    String applyFunding;

    String actualFunding;

    String phdFunding;


    String bonus;


    String trafficFunding;

    String total;

    Integer departmentId;


    @Transient
    public static String[] getAttrTittles() {
        String[] attrTittles = {
                "批次","学院","预算经费 ","申报经费","批准经费","博士经费","奖励经费","交通补贴","总经费"
        };
        return attrTittles;
    }

    @Transient
    public  String[] getContents(){
        String[] contents=new String[9];
        contents[0]=getSessionName();
        contents[1]=getDepartment();
        contents[2]=getPlanFunding();
        contents[3]=getApplyFunding();
        contents[4]=getActualFunding();
        contents[5]=getBonus();
        contents[6]=getPhdFunding();
        contents[7]=getTrafficFunding();
        contents[8]=getTotal();
        return contents;
    }



    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getPlanFunding() {
        return planFunding;
    }

    public void setPlanFunding(String planFunding) {
        this.planFunding = planFunding;
    }

    public String getActualFunding() {
        return actualFunding;
    }

    public void setActualFunding(String actualFunding) {
        this.actualFunding = actualFunding;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTrafficFunding() {
        return trafficFunding;
    }

    public void setTrafficFunding(String trafficFunding) {
        this.trafficFunding = trafficFunding;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}
