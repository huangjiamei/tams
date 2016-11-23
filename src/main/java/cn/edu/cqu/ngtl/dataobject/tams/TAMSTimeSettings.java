package cn.edu.cqu.ngtl.dataobject.tams;

import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.tools.converter.StringDateConverter;

import javax.persistence.*;

/**
 * Created by tangjing on 16-11-23.
 */
@Entity
@Table(name = "TAMS_TIMESETTINGS")
public class TAMSTimeSettings {
    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator = "tamsTimeSettings")
    @SequenceGenerator(name = "tamsTimeSettings", sequenceName = "TAMS_TIMESETTINGS_S", allocationSize = 1)
    private String id;

    @Column(name = "START_TIME")
    @Convert(converter = StringDateConverter.class)
    private String startTime;

    @Column(name = "END_TIME")
    @Convert(converter = StringDateConverter.class)
    private String endTime;

    @Column(name = "EDIT_TIME")
    @Convert(converter = StringDateConverter.class)
    private String editTIme;

    @Column(name = "TIMESETTING_TYPE")
    private String timeSettingTypeId;

    @ManyToOne
    @JoinColumn(name="TIMESETTING_TYPE",insertable = false, updatable = false)
    private TAMSTimeSettingType timeSettingType;

    @Column(name = "SESSION_ID")
    private String sessionId;

    @ManyToOne
    @JoinColumn(name="SESSION_ID",insertable = false, updatable = false)
    private UTSession session;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEditTIme() {
        return editTIme;
    }

    public void setEditTIme(String editTIme) {
        this.editTIme = editTIme;
    }

    public String getTimeSettingTypeId() {
        return timeSettingTypeId;
    }

    public void setTimeSettingTypeId(String timeSettingTypeId) {
        this.timeSettingTypeId = timeSettingTypeId;
    }

    public TAMSTimeSettingType getTimeSettingType() {
        return timeSettingType;
    }

    public void setTimeSettingType(TAMSTimeSettingType timeSettingType) {
        this.timeSettingType = timeSettingType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UTSession getSession() {
        return session;
    }

    public void setSession(UTSession session) {
        this.session = session;
    }
}
