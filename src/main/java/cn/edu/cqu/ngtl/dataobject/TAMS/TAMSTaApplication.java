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
    private Integer id;

    @Column(name = "APPLICATION_ID")
    private Integer applicationId;

    @Column(name = "APPLICATION_CALSS_ID")
    private Integer applicationClassId;

    @Column(name = "APPLICATION_STATUS")
    private String applicationStatus;

    @Column(name = "APPLICATION_TIME")
    private String applicationTime;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "OBJ_ID")
    private Integer objId;

    @Column(name = "VER_NBR")
    private Integer verNbr;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getApplicationId() { return applicationId; }

    public void setApplicationId(Integer applicationId) { this.applicationId = applicationId; }

    public Integer getApplicationClassId() { return applicationClassId; }

    public void setApplicationClassId(Integer applicationClassId) { this.applicationClassId = applicationClassId; }

    public String getApplicationStatus() { return applicationTime; }

    public void setApplicationStatus(String applicationStatus) { this.applicationStatus = applicationStatus; }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

    public Integer getObjId() { return objId; }

    public void setObjId(Integer objId) { this.objId = objId; }

    public Integer getVerNbr() { return verNbr; }

    public void setVerNbr(Integer verNbr) { this.verNbr = verNbr; }
}
