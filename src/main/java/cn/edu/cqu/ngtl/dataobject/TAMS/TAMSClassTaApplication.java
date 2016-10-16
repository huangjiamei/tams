package cn.edu.cqu.ngtl.dataobject.TAMS;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by long2ice on 2016/10/15.
 */
@Entity
@Table(name = "TAMS_CLASS_TA_APPLICATION")
public class TAMSClassTaApplication extends DataObjectBase implements Serializable {

    @Id
    @Column(name = "UNIQUEID")
    private Integer id;

    @Column(name = "APPLICANT_ID")
    private Integer applicantId;

    @Column(name = "APPLICATION_CLASS_ID")
    private Integer applicationClassId;

    @Column(name = "TA_NUMBER")
    private String taNumber;

    @Column(name = "APPLICATION_TIME")
    private String applicationTime;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "OBJ_ID")
    private Integer objId;

    @Column(name = "VER_NBR")
    private String verNbr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
    }

    public Integer getApplicationClassId() {
        return applicationClassId;
    }

    public void setApplicationClassId(Integer applicationClassId) {
        this.applicationClassId = applicationClassId;
    }

    public String getTaNumber() {
        return taNumber;
    }

    public void setTaNumber(String taNumber) {
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

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public String getVerNbr() {
        return verNbr;
    }

    public void setVerNbr(String verNbr) {
        this.verNbr = verNbr;
    }
}

