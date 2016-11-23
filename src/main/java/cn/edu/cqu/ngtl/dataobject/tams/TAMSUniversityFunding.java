package cn.edu.cqu.ngtl.dataobject.tams;

import cn.edu.cqu.ngtl.dataobject.ut.UTSession;

import javax.persistence.*;

/**
 * Created by damei on 16/11/20.
 */
@Entity
@Table(name = "TAMS_UNIVERSITY_FUNDING")
public class TAMSUniversityFunding {
    @Id
    @Column(name = "UNIQUEID")
    private String id;

    @Column(name = "PLAN_FUNDING")
    private String planFunding;

    @Column(name = "APPLY_FUNDING")
    private String applyFunding;

    @Column(name = "ACTUAL_FUNDING")
    private String actualFunding;

    @Column(name = "PHD_FUNDING")
    private String phdFunding;

    @Column(name = "BONUS")
    private String bonus;

    @Column(name = "TRAVEL_SUBSIDY")
    private String travelSubsidy;

    @Column(name = "UNIVERSITY_ID")
    private String universityId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanFunding() {
        return planFunding;
    }

    public void setPlanFunding(String planFunding) {
        this.planFunding = planFunding;
    }

    public String getApplyFunding() {
        return applyFunding;
    }

    public void setApplyFunding(String applyFunding) {
        this.applyFunding = applyFunding;
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

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getTravelSubsidy() {
        return travelSubsidy;
    }

    public void setTravelSubsidy(String travelSubsidy) {
        this.travelSubsidy = travelSubsidy;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public UTSession getUtSession() {
        return utSession;
    }

    public void setUtSession(UTSession utSession) {
        this.utSession = utSession;
    }

    @OneToOne
    @JoinColumn(name = "SESSION_ID", updatable = false, insertable = false)
    private UTSession utSession;
}
