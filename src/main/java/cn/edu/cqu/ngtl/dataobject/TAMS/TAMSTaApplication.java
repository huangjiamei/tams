package cn.edu.cqu.ngtl.dataobject.TAMS;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private String id;

    @Column(name = "APPLICATION_ID")
    private String applicationId;

    @Column(name = "APPLICATION_CALSS_ID")
    private String applicationClassId;

    @Column(name = "APPLICATION_STATUS")
    private String applicationStatus;

    @Column(name = "APPLICATION_TIME")
    private String applicationTime;

    @Column(name = "NOTE")
    private String note;

    private String objId;

    private Integer verNbr;

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

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public Integer getVerNbr() {
        return verNbr;
    }

    public void setVerNbr(Integer verNbr) {
        this.verNbr = verNbr;
    }
}
