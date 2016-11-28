package cn.edu.cqu.ngtl.dataobject.tams;

import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.tools.converter.StringDateConverter;
import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by long2ice
 * Date on 2016/10/15
 */
@Entity
@Table(name = "TAMS_CLASS_TA_APPLICATION")
public class TAMSClassTaApplication extends DataObjectBase implements Serializable {

    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator="tamsClassTaApplication")
    @SequenceGenerator(name="tamsClassTaApplication",sequenceName="TAMS_CLASS_TA_APPLICATION_S",allocationSize=1)
    private String id;

    @Column(name = "APPLICANT_ID")
    private String applicantId;

    @Column(name = "APPLICATION_CLASS_ID")
    private String applicationClassId;

    @Column(name = "TA_NUMBER")
    private Integer taNumber;

    @Column(name = "APPLICATION_TIME")
    @Convert(converter = StringDateConverter.class)
    private String applicationTime;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "SESSION_ID")
    private Integer sessionId;

    @ManyToOne
    @JoinColumn(name = "SESSION_ID", insertable = false, updatable = false)
    private UTSession curSession;

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public UTSession getCurSession() {
        return curSession;
    }

    public void setCurSession(UTSession curSession) {
        this.curSession = curSession;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicationClassId() {
        return applicationClassId;
    }

    public void setApplicationClassId(String applicationClassId) {
        this.applicationClassId = applicationClassId;
    }

    public Integer getTaNumber() {
        return taNumber;
    }

    public void setTaNumber(Integer taNumber) {
        this.taNumber = taNumber;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

