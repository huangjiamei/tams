package cn.edu.cqu.ngtl.dataobject.tams;

import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.tools.converter.StringDateConverter;
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

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="TA_TYPE", insertable=false, updatable=false)
    private TAMSTaCategory tamsTaCategory;

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
    @Convert(converter = StringDateConverter.class)
    private String startTime;

    @Column(name = "TA_END_TIME")
    @Convert(converter = StringDateConverter.class)
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
    private String evaluation;

    @Column(name = "EVALUATION_DETAIL")
    private String evaluationDetail;

    @Column(name = "STUDENT_EVALUATION")
    private String stuEva;

    @Column(name = "ASSIGNED_FUNDING")
    private String assignedFunding;

    @Column(name = "PHD_FUNDING")
    private String phdFunding;

    @Column(name = "TRAVEL_SUBSIDY")
    private String travelSubsidy;

    @Column(name = "BONUS")
    private String bonus;

    @Column(name = "OUTSTANDING_TA")
    private String outStandingTaWorkflowStatusId;

    @Column(name = "MONTH_1")
    private String month1;

    @Column(name = "MONTH_2")
    private String month2;

    @Column(name = "MONTH_3")
    private String month3;

    @Column(name = "MONTH_4")
    private String month4;

    @Column(name = "MONTH_5")
    private String month5;

    @Column(name = "MONTH_6")
    private String month6;

    @Column(name = "MONTH_7")
    private String month7;

    @Column(name = "MONTH_8")
    private String month8;

    @Column(name = "MONTH_9")
    private String month9;

    @Column(name = "MONTH_10")
    private String month10;

    @Column(name = "MONTH_11")
    private String month11;

    @Column(name = "MONTH_12")
    private String month12;

    @Column(name = "OUTSTANDING_NOTE")
    private String osNote;

    @Column(name = "PHONE_NUMBER")
    private String phoneNbr;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "BANK_NUMBER")
    private String bankNbr;


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNbr() {
        return bankNbr;
    }

    public void setBankNbr(String bankNbr) {
        this.bankNbr = bankNbr;
    }

    @ManyToOne(cascade = CascadeType.ALL) //更新级联
    @JoinColumn(name = "OUTSTANDING_TA", insertable = false, updatable = false)
    private TAMSWorkflowStatus outStandingTaWorkflowStatus;

    public String getOutStandingTaWorkflowStatusId() {
        return outStandingTaWorkflowStatusId;
    }

    public void setOutStandingTaWorkflowStatusId(String outStandingTaWorkflowStatusId) {
        this.outStandingTaWorkflowStatusId = outStandingTaWorkflowStatusId;
    }

    public TAMSWorkflowStatus getOutStandingTaWorkflowStatus() {
        return outStandingTaWorkflowStatus;
    }

    public void setOutStandingTaWorkflowStatus(TAMSWorkflowStatus outStandingTaWorkflowStatus) {
        this.outStandingTaWorkflowStatus = outStandingTaWorkflowStatus;
    }

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

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
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

    public String getStuEva() {
        return stuEva;
    }

    public void setStuEva(String stuEva) {
        this.stuEva = stuEva;
    }

    public String getOsNote() {
        return osNote;
    }

    public void setOsNote(String osNote) {
        this.osNote = osNote;
    }

    public String getMonth1() {
        return month1;
    }

    public void setMonth1(String month1) {
        this.month1 = month1;
    }

    public String getMonth2() {
        return month2;
    }

    public void setMonth2(String month2) {
        this.month2 = month2;
    }

    public String getMonth3() {
        return month3;
    }

    public void setMonth3(String month3) {
        this.month3 = month3;
    }

    public String getMonth4() {
        return month4;
    }

    public void setMonth4(String month4) {
        this.month4 = month4;
    }

    public String getMonth5() {
        return month5;
    }

    public void setMonth5(String month5) {
        this.month5 = month5;
    }

    public String getMonth6() {
        return month6;
    }

    public void setMonth6(String month6) {
        this.month6 = month6;
    }

    public String getMonth7() {
        return month7;
    }

    public void setMonth7(String month7) {
        this.month7 = month7;
    }

    public String getMonth8() {
        return month8;
    }

    public void setMonth8(String month8) {
        this.month8 = month8;
    }

    public String getMonth9() {
        return month9;
    }

    public void setMonth9(String month9) {
        this.month9 = month9;
    }

    public String getMonth10() {
        return month10;
    }

    public void setMonth10(String month10) {
        this.month10 = month10;
    }

    public String getMonth11() {
        return month11;
    }

    public void setMonth11(String month11) {
        this.month11 = month11;
    }

    public String getMonth12() {
        return month12;
    }

    public void setMonth12(String month12) {
        this.month12 = month12;
    }

    public TAMSTaCategory getTamsTaCategory() {
        return tamsTaCategory;
    }

    public void setTamsTaCategory(TAMSTaCategory tamsTaCategory) {
        this.tamsTaCategory = tamsTaCategory;
    }

    public String getPhoneNbr() {
        return phoneNbr;
    }

    public void setPhoneNbr(String phoneNbr) {
        this.phoneNbr = phoneNbr;
    }
}
