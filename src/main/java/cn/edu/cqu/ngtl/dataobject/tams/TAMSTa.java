package cn.edu.cqu.ngtl.dataobject.tams;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by 金祖增 on 2016/10/16.
 */
@Entity
@Table(name = "TAMS_CLASS_TA_APPLICATION")
public class TAMSTa extends DataObjectBase implements Serializable {
    //private static final long serialVersionUID = -1543515532501167011L;
    @Id
    @Column(name = "UNIQUEID")
    private String id;

    @Column(name="TA_TYPE")
    private String type;

    @Column(name = "TA_CLASS")
    private String taClass;

    @Column(name = "TA_ID")
    private String taId;

    @Column(name = "TA_START_TIME")
    private String startTime;

    @Column(name = "TA_END_TIME")
    private String endTime;

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

    public String getTaClass() {
        return taClass;
    }

    public void setTaClass(String taClass) {
        this.taClass = taClass;
    }

    public String getTaId() {
        return taId;
    }

    public void setTaId(String taId) {
        this.taId = taId;
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
}
