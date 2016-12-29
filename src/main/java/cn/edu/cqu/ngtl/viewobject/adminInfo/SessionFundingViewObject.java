package cn.edu.cqu.ngtl.viewobject.adminInfo;

import java.beans.Transient;

/**
 * Created by tangjing on 16-11-9.
 */
public class SessionFundingViewObject {

    String sessionName;

    String planFunding;

    String actualFunding;

    String phdFunding;

    String applyFunding;

    String bonus;

    String trafficFunding;

    String total;

    @Transient
    public static String[] getAttrTittles() {
        String[] attrTittles = {
                "批次","预算经费","申报经费","批准经费","博士津贴","奖励经费","交通补贴","总经费"
        };
        return attrTittles;
    }

    @Transient
    public  String[] getContents(){
        String[] contents=new String[8];
        contents[0]=getSessionName();
        contents[1]=getPlanFunding();
        contents[2]=getApplyFunding();
        contents[3]=getActualFunding();
        contents[4]=getPhdFunding();
        contents[5]=getBonus();
        contents[6]=getTrafficFunding();
        contents[7]=getTotal();
        return contents;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public String getTrafficFunding() {
        return trafficFunding;
    }

    public void setTrafficFunding(String trafficFunding) {
        this.trafficFunding = trafficFunding;
    }
}
