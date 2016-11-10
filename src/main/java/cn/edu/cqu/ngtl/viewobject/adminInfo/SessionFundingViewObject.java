package cn.edu.cqu.ngtl.viewobject.adminInfo;

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

    String total;

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
}
