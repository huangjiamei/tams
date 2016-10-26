package cn.edu.cqu.ngtl.dataobject.tams;

import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;
import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by awake on 2016-10-26.
 */

@Entity
@Table(name = "TAMS_COURSE_MANAGER")
public class TAMSCourseManager extends DataObjectBase implements Serializable {


    private static final long serialVersionUID = -913167359936944326L;

    @Id
    @Column(name = "UNIQUEID")
    private String id;

    @Column(name = "COURSE_ID")
    private Integer courseId;

    @Column(name = "COURSE_MANAGER_ID")
    private String courseManagerId;

    @ManyToOne
    @JoinColumn(name = "COURSE_MANAGER_ID",insertable = false, updatable = false)
    private UTInstructor utInstructor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseManagerId() {
        return courseManagerId;
    }

    public void setCourseManagerId(String courseManagerId) {
        this.courseManagerId = courseManagerId;
    }

    public UTInstructor getUtInstructor() {
        return utInstructor;
    }

    public void setUtInstructor(UTInstructor utInstructor) {
        this.utInstructor = utInstructor;
    }
}
