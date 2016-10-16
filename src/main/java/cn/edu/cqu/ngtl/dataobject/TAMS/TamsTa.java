package cn.edu.cqu.ngtl.dataobject.TAMS;

/**
 * Created by 金祖增 on 2016/10/16.
 */

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViewType;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViews;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Timestamp;


@Entity
@Table(name = "TAMS_TA")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class TamsTa extends DataObjectBase implements Serializable {

    //private static final long serialVersionUID = -1543515532501167011L;

    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator="tamsTaSeq")
    @SequenceGenerator(name="tamsTaSeq",sequenceName="?",allocationSize=1)
    private Integer id;
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }


    @Column(name="TA_TYPE")
    private String type;
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "TA_CLASS")
    private String taClass;
    public String getTaClass() {
        return this.taClass;
    }
    public void setTaClass(String taclass) {
        this.taClass = taClass;
    }

    @Column(name = "TA_ID")
    private String taId;
    public String getTaId() {
        return this.taId;
    }
    public void setTaId(String taId) {
        this.taId = taId;
    }

    @Column(name = "TA_START_TIME")
    private Timestamp startTime;
    public Timestamp getTaStartTime() {
        return this.startTime;
    }
    public void setTaStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Column(name = "TA_END_TIME")
    private Timestamp endTime;
    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

}
