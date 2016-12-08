package cn.edu.cqu.ngtl.dataobject.ut;

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.Label;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 此类用于Class与Instructor关联辅助
 * 保留此类以备后用
 */
@Entity
@Table(name = "UNITIME_CLASS_INSTRUCTOR")
public class UTClassInstructor extends DataObjectBase implements Serializable{

	private static final long serialVersionUID = -8255539470163746200L;

	@Id
	@Column(name = "UNIQUEID")
	private String id;

//	@ManyToOne
//	@JoinColumn(name = "CLASS_ID",updatable=false, insertable=false)
//	private UTClass utClass;

	@Column(name = "CLASS_ID")
	private String classId;

	@Column(name = "INSTRUCTOR_ID")
	private String instructorId;

//	@ManyToOne
//	@JoinColumn(name = "INSTRUCTOR_ID",updatable=false, insertable=false)
//	private UTInstructor utInstructor;

	@Column(name = "PERCENT_SHARE")
	@Label("PERCENT_SHARE")
	private Integer percentShare;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public UTClass getUtClass() {
//		return utClass;
//	}
//
//	public void setUtClass(UTClass utClass) {
//		this.utClass = utClass;
//	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(String instructorId) {
		this.instructorId = instructorId;
	}

//	public UTInstructor getUtInstructor() {
//		return utInstructor;
//	}
//
//	public void setUtInstructor(UTInstructor utInstructor) {
//		this.utInstructor = utInstructor;
//	}

	public Integer getPercentShare() {
		return percentShare;
	}

	public void setPercentShare(Integer percentShare) {
		this.percentShare = percentShare;
	}
}
