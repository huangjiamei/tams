package cn.edu.cqu.ngtl.dataobject.ut;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by awake on 2016/12/12.
 */

@Entity
@Table(name = "UNITIME_STUDENT_TIMETABLE")
public class UTStudentTimetable extends DataObjectBase implements Serializable {

    @Id
    @Column(name = "UNIQUEID")
	@GeneratedValue(generator="utStudentTimetableSeq")
	@SequenceGenerator(name="utStudentTimetableSeq",sequenceName="UNITIME_STUDENT_TIMETABLE_S",allocationSize=1)
    private String id;

    @Column(name = "STUDENT_ID")
    private String studentId;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID", insertable = false, updatable = false)
    private UTStudent utStudent;

    @Column(name = "CLASS_ID")
    private String classId;

    @Column(name = "SESSION_ID")
    private Integer sessionId;

    @ManyToOne
    @JoinColumn(name="SESSION_ID", updatable=false, insertable=false)
    private UTSession session;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public UTStudent getUtStudent() {
        return utStudent;
    }

    public void setUtStudent(UTStudent utStudent) {
        this.utStudent = utStudent;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public UTSession getSession() {
        return session;
    }

    public void setSession(UTSession session) {
        this.session = session;
    }
}
