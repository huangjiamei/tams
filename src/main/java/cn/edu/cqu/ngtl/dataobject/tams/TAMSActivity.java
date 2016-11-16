package cn.edu.cqu.ngtl.dataobject.tams;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Id;

/**
 * Created by damei on 16/11/15.
 */
@Entity
@Table(name = "TAMS_ACTIVITY")
public class TAMSActivity {

    @Id
    @Column(name = "UNIQUEID")
    private String id;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "TEACH_CALENDAR_ID", insertable = false, updatable = false)
    private String teachCalendarId;

    @Column(name = "CREATE_TIME")
    private String createTime;

    @Column(name = "LAST_UPDATE_TIME")
    private String lastUpdateTime;

    @ManyToOne
    @Column(name = "STATUS", insertable = false, updatable = false)
    private String status;

    @ManyToOne
    @Column(name = "ACTIVITY_TYPE", insertable = false, updatable =false)
    private String activityType;

    @Column(name = "OBJ_ID")
    private String objId;

    @Column(name = "VER_NBR")
    private String verNbr;

    @Column(name = "ATTACHMENT")
    private String attachment;

    public String getId() {return id;}

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeachCalendarId() {
        return teachCalendarId;
    }

    public void setTeachCalendarId(String teachCalendarId) {
        this.teachCalendarId = teachCalendarId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getVerNbr() {
        return verNbr;
    }

    public void setVerNbr(String verNbr) {
        this.verNbr = verNbr;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
