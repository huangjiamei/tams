package cn.edu.cqu.ngtl.dataobject.tams;

import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by awake on 2016/11/25.
 */


@Entity
@Table(name = "TAMS_CLASS_FUNDING_DRAFT")
public class TAMSClassFundingDraft extends DataObjectBase implements Serializable {

    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator="tamsClassFunding")
    @SequenceGenerator(name="tamsClassFunding",sequenceName="TAMS_CLASS_FUNDING_S",allocationSize=1)
    private String id;

    @Column(name = "CLASS_ID")
    private String classId;

    @Transient
    private UTClassInformation classInformation;

    @Transient
    private UTSession session;

    @Column(name = "ASSIGNED_FUNDING")
    private String assignedFunding;

    @Column(name = "PHD_FUNDING")
    private String phdFunding;

    @Column(name = "APPLY_FUNDING")
    private String applyFunding;

    @Column(name = "BONUS")
    private String bonus;

    @Column(name = "TRAVEL_SUBSIDY")
    private String travelSubsidy;

    @Column(name = "SESSION_ID")
    private String sessionId;

    @OneToOne
    @JoinColumn(name = "SESSION_ID", updatable = false, insertable = false)
    private UTSession utSession;


    public UTSession getSession() {
        return session;
    }

    public void setSession(UTSession session) {
        this.session = session;
    }

    public UTClassInformation getClassInformation() {
        return classInformation;
    }

    public void setClassInformation(UTClassInformation classInformation) {
        this.classInformation = classInformation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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

    public String getTravelSubsidy() {
        return travelSubsidy;
    }

    public void setTravelSubsidy(String travelSubsidy) {
        this.travelSubsidy = travelSubsidy;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UTSession getUtSession() {
        return utSession;
    }

    public void setUtSession(UTSession utSession) {
        this.utSession = utSession;
    }
}
