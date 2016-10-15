package cn.edu.cqu.ngtl.dataobject.UT;

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.Label;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViewType;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViews;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 此类用于Class与Instructor关联辅助
 * 保留此类以备后用
 */
@Entity
@Table(name = "UNITIME_CLASS_INSTRUCTOR")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class UTClassInstructor extends DataObjectBase implements Serializable{

	private static final long serialVersionUID = -8255539470163746200L;

	@Id
	@Column(name = "UNIQUEID")
	@Label("uniqueid")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "CLASS_ID",updatable=false, insertable=false)
	private UTClass utClass;

	@Column(name = "CLASS_ID")
	@Label("CLASS_ID")
	private Integer classId;

	@Column(name = "INSTRUCTOR_ID")
	@Label("INSTRUCTOR_ID")
	private String instructorId;

	@ManyToOne
	@JoinColumn(name = "INSTRUCTOR_ID",updatable=false, insertable=false)
	private UTInstructor utInstructor;

	@Column(name = "PERCENT_SHARE")
	@Label("PERCENT_SHARE")
	private Integer percentShare;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UTClass getUtClass() {
		return utClass;
	}

	public void setUtClass(UTClass utClass) {
		this.utClass = utClass;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(String instructorId) {
		this.instructorId = instructorId;
	}

	public UTInstructor getUtInstructor() {
		return utInstructor;
	}

	public void setUtInstructor(UTInstructor utInstructor) {
		this.utInstructor = utInstructor;
	}

	public Integer getPercentShare() {
		return percentShare;
	}

	public void setPercentShare(Integer percentShare) {
		this.percentShare = percentShare;
	}
}
