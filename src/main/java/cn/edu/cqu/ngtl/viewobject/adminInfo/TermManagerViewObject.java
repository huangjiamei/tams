package cn.edu.cqu.ngtl.viewobject.adminInfo;

/**
 * Created by tangjing on 16-10-27.
 */
public class TermManagerViewObject {

    private String termName;

    private Integer budget;

    private String startDate;

    private String endDate;

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
