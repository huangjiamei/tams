package cn.edu.cqu.ngtl.dataobject.tams;

import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.tools.converter.StringDateConverter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by CQU-CST-WuErli on 2016/10/16.
 */

@Entity
@Table(name = "TAMS_TA_APPLICATION")
public class TAMSTaApplication extends DataObjectBase implements Serializable{

//    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator="tamsTaApplicationSeq")
    @SequenceGenerator(name="tamsTaApplicationSeq",sequenceName="TAMS_TA_APPLICATION_S",allocationSize=1)
    private String id;

    @Column(name = "APPLICANT_ID")
    private String applicationId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="APPLICANT_ID", insertable=false, updatable=false)
    private UTStudent applicant;

    @Column(name = "APPLICATION_CLASS_ID")
    private String applicationClassId;

    @Column(name = "APPLICATION_STATUS")
    private String applicationStatus;

    @Column(name = "APPLICATION_TIME")
    @Convert(converter = StringDateConverter.class)
    private String applicationTime;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "EDU_BACKGROUND")
    private String eduBackground;

    @Column(name = "SESSION_ID")
    private Integer sessinId;

    @ManyToOne
    @JoinColumn(name = "SESSION_ID", insertable = false, updatable = false)
    private UTSession curSession;

    public UTStudent getApplicant() {
        return applicant;
    }

    public void setApplicant(UTStudent applicant) {
        this.applicant = applicant;
    }

    public Integer getSessinId() {
        return sessinId;
    }

    public void setSessinId(Integer sessinId) {
        this.sessinId = sessinId;
    }

    public UTSession getCurSession() {
        return curSession;
    }

    public void setCurSession(UTSession curSession) {
        this.curSession = curSession;
    }

    public String getEduBackground() {
        return eduBackground;
    }

    public void setEduBackground(String eduBackground) {
        this.eduBackground = eduBackground;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationClassId() {
        return applicationClassId;
    }

    public void setApplicationClassId(String applicationClassId) {
        this.applicationClassId = applicationClassId;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
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
