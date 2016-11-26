package cn.edu.cqu.ngtl.dataobject.tams;

import cn.edu.cqu.ngtl.dataobject.ut.UTDepartment;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by awake on 2016/11/25.
 */
@Entity
@Table(name = "TAMS_DEPT_FUNDING_DRAFT")
public class TAMSDeptFundingDraft extends DataObjectBase implements Serializable {

    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator="tamsDeptFunding")
    @SequenceGenerator(name="tamsDeptFunding",sequenceName="TAMS_DEPT_FUNDING_S",allocationSize=1)
    private String id;

    @Column(name = "PLAN_FUNDING")
    private String planFunding;

    @Column(name = "ACTUAL_FUNDING")
    private String actualFunding;

    @Column(name = "PHD_FUNDING")
    private String phdFunding;

    @Column(name = "APPLY_FUNDING")
    private String applyFunding;

    @Column(name = "BONUS")
    private String bonus;

    @Column(name = "DEPARTMENT_ID")
    private String departmentId;

    @Column(name = "TRAVEL_SUBSIDY")
    private String travelSubsidy;

    @OneToOne
    @JoinColumn(name = "DEPARTMENT_ID",updatable=false, insertable=false)
    private UTDepartment department;

    @Column(name = "SESSION_ID")
    private String sessionId;

    @OneToOne
    @JoinColumn(name = "SESSION_ID",updatable=false, insertable=false)
    private UTSession session;

    public String getTravelSubsidy() {
        return travelSubsidy;
    }

    public void setTravelSubsidy(String travelSubsidy) {
        this.travelSubsidy = travelSubsidy;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UTSession getSession() {
        return session;
    }

    public void setSession(UTSession session) {
        this.session = session;
    }

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

    public String getActualFunding() {
        return actualFunding;
    }

    public void setActualFunding(String actualFunding) {
        this.actualFunding = actualFunding;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public UTDepartment getDepartment() {
        return department;
    }

    public void setDepartment(UTDepartment department) {
        this.department = department;
    }




}
