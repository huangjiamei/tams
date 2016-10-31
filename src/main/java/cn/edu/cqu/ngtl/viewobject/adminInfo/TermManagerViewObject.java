package cn.edu.cqu.ngtl.viewobject.adminInfo;

/**
 * Created by tangjing on 16-10-27.
 */
public class TermManagerViewObject {

    private Integer id;

    private String termName;

    /**
     * 批次年份
     */
    private String termYear;

    /**
     * 批次季度
     */
    private String termTerm;

    private Integer budget;

    private String startDate;

    private String endDate;

    private String active;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTermYear() {
        return termYear;
    }

    public void setTermYear(String termYear) {
        this.termYear = termYear;
    }

    public String getTermTerm() {
        return termTerm;
    }

    public void setTermTerm(String termTerm) {
        this.termTerm = termTerm;
    }

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
