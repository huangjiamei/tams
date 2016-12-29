package cn.edu.cqu.ngtl.dataobject.tams;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by awake on 2016/12/29.
 */
@Entity
@Table(name = "TAMS_TA_BLACKLIST")
public class TAMSTaBlackList extends DataObjectBase implements Serializable {

    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator = "tamsTaBlackList")
    @SequenceGenerator(name = "tamsTaBlackList", sequenceName = "TAMS_TA_BLACKLIST_S", allocationSize = 1)
    private String id;

    @Column(name = "TA_ID")
    private String taId;

    @Column(name = "BEEN_FIRED_TIME")
    private String beenFiredTime;


    @Column(name = "FIRED_BY")
    private String firedBy;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaId() {
        return taId;
    }

    public void setTaId(String taId) {
        this.taId = taId;
    }

    public String getBeenFiredTime() {
        return beenFiredTime;
    }

    public void setBeenFiredTime(String beenFiredTime) {
        this.beenFiredTime = beenFiredTime;
    }

    public String getFiredBy() {
        return firedBy;
    }

    public void setFiredBy(String firedBy) {
        this.firedBy = firedBy;
    }
}
