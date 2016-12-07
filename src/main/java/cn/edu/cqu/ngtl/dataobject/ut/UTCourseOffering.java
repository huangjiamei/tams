package cn.edu.cqu.ngtl.dataobject.ut;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "UNITIME_COURSE_OFFERING")
public class UTCourseOffering extends DataObjectBase implements Serializable {

	@Id
	@Column(name = "UNIQUEID")
//    @GeneratedValue(generator="utCoSeq")
//    @SequenceGenerator(name="utCoSeq",sequenceName="UNITIME_COURSE_OFFERING_S",allocationSize=1)
	private Integer id;

	@ManyToOne
	@JoinColumn(name="COURSE_ID", updatable=false, insertable=false)
	private UTCourse course;

	@Column(name = "COURSE_ID")
	private Integer courseId;

	@Column(name = "SESSION_ID")
	private Integer sessionId;

	@ManyToOne
	@JoinColumn(name="SESSION_ID", updatable=false, insertable=false)
	private UTSession session;

	@OneToMany(fetch= FetchType.EAGER, mappedBy="courseOffering" )
	private List<UTClass> klasses;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UTCourse getCourse() {
        return course;
    }

    public void setCourse(UTCourse course) {
        this.course = course;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
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

    public List<UTClass> getKlasses() {
        return klasses;
    }

    public void setKlasses(List<UTClass> klasses) {
        this.klasses = klasses;
    }
}
