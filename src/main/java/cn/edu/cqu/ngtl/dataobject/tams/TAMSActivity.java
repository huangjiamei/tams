package cn.edu.cqu.ngtl.dataobject.tams;

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
    @GeneratedValue(generator="tamsActivity")
    @SequenceGenerator(name="tamsActivity",sequenceName="TAMS_ACTIVITY_S",allocationSize=1)
    private String id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TEACH_CALENDAR_ID")
    private String teachCalendarId;

    @ManyToOne
    @JoinColumn(name = "TEACH_CALENDAR_ID", insertable = false, updatable = false)
    private TAMSTeachCalendar teachCalendar;

    @Column(name = "CREATE_TIME")
    private String createTime;

    @Column(name = "LAST_UPDATE_TIME")
    private String lastUpdateTime;

    @ManyToOne
    @JoinColumn(name = "STATUS", insertable = false, updatable = false)
    private TAMSIssueStatus status;

    @ManyToOne
    @JoinColumn(name = "ACTIVITY_TYPE", insertable = false, updatable =false)
    private TAMSIssueType activityType;

    @Column(name = "ATTACHMENT")
    private boolean attachment;

    public String getId() {
        return id;
    }

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

    public TAMSTeachCalendar getTeachCalendar() {
        return teachCalendar;
    }

    public void setTeachCalendar(TAMSTeachCalendar teachCalendar) {
        this.teachCalendar = teachCalendar;
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

    public TAMSIssueStatus getStatus() {
        return status;
    }

    public void setStatus(TAMSIssueStatus status) {
        this.status = status;
    }

    public TAMSIssueType getActivityType() {
        return activityType;
    }

    public void setActivityType(TAMSIssueType activityType) {
        this.activityType = activityType;
    }

    public boolean isAttachment() {
        return attachment;
    }

    public void setAttachment(boolean attachment) {
        this.attachment = attachment;
    }
}
