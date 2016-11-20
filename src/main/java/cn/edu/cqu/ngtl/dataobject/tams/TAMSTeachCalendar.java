package cn.edu.cqu.ngtl.dataobject.tams;

import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.tools.converter.StringDateConverter;

import javax.persistence.*;

/**
 * Created by tangjing on 16-11-15.
 */
@Entity
@Table(name = "TAMS_TEACH_CALENDAR")
public class TAMSTeachCalendar {
    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator = "tamsTeachCalendar")
    @SequenceGenerator(name = "tamsTeachCalendar", sequenceName = "TAMS_TEACH_CALENDAR_S", allocationSize = 1)
    private String id;

    @Column(name = "THEME")
    private String theme;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TA_TASK")
    private String taTask;

    @Column(name = "START_TIME")
    @Convert(converter = StringDateConverter.class)
    private String startTime;

    @Column(name = "END_TIME")
    @Convert(converter = StringDateConverter.class)
    private String endTime;

    @Column(name = "ELAPSED_TIME")
    private String elapsedTime;

    @Column(name = "ATTACHMENT")
    private boolean hasAttachment;

    @Column(name = "CLASS_ID")
    private String classId;

    @Transient
    private UTClassInformation classInformation;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public UTClassInformation getClassInformation() {
        return classInformation;
    }

    public void setClassInformation(UTClassInformation classInformation) {
        this.classInformation = classInformation;
    }

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

    public String getTaTask() {
        return taTask;
    }

    public void setTaTask(String taTask) {
        this.taTask = taTask;
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

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public boolean isHasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachment(boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
    }
}
