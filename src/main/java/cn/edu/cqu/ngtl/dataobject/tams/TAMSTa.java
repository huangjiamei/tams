package cn.edu.cqu.ngtl.dataobject.tams;

import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by 金祖增 on 2016/10/16.
 */
@Entity
@Table(name = "TAMS_TA")
public class TAMSTa extends DataObjectBase implements Serializable {
    //private static final long serialVersionUID = -1543515532501167011L;
    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator="tamsTa")
    @SequenceGenerator(name="tamsTa",sequenceName="TAMS_TA_S",allocationSize=1)
    private String id;

    @Column(name="TA_TYPE")
    private String type;

    @Column(name = "TA_CLASS")
    private String taClassId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="TA_CLASS", insertable=false, updatable=false)
    private UTClass taClass;

    @Column(name = "TA_ID")
    private String taId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="TA_ID", insertable=false, updatable=false)
    private UTStudent ta;

    @Column(name = "TA_START_TIME")
    private String startTime;

    @Column(name = "TA_END_TIME")
    private String endTime;

    @Column(name = "SESSION_ID")
    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "SESSION_ID", insertable = false, updatable = false)
    private UTSession curSession;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PAY_DAY")
    private String payDay;

    @Column(name = "APPLICATION_NOTE")
    private String applicationNote;


    @Column(name = "EVALUATION")
    private String evalutation;

    @Column(name = "EVALUATION_DETAIL")
    private String evaluationDetail;


    @Column(name = "ASSIGNED_FUNDING")
    private String assignedFunding;

    @Column(name = "PHD_FUNDING")
    private String phdFunding;


    @Column(name = "TRAVEL_SUBSIDY")
    private String travelSubsidy;

    @Column(name = "BONUS")
    private String bonus;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayDay() {
        return payDay;
    }

    public void setPayDay(String payDay) {
        this.payDay = payDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTaClassId() {
        return taClassId;
    }

    public void setTaClassId(String taClassId) {
        this.taClassId = taClassId;
    }

    public UTClass getTaClass() {
        return taClass;
    }

    public void setTaClass(UTClass taClass) {
        this.taClass = taClass;
    }

    public String getTaId() {
        return taId;
    }

    public void setTaId(String taId) {
        this.taId = taId;
    }

    public UTStudent getTa() {
        return ta;
    }

    public void setTa(UTStudent ta) {
        this.ta = ta;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UTSession getCurSession() {
        return curSession;
    }

    public void setCurSession(UTSession curSession) {
        this.curSession = curSession;
    }

    public String getApplicationNote() {
        return applicationNote;
    }

    public void setApplicationNote(String applicationNote) {
        this.applicationNote = applicationNote;
    }

    public String getEvalutation() {
        return evalutation;
    }

    public void setEvalutation(String evalutation) {
        this.evalutation = evalutation;
    }

    public String getEvaluationDetail() {
        return evaluationDetail;
    }

    public void setEvaluationDetail(String evaluationDetail) {
        this.evaluationDetail = evaluationDetail;
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
}
