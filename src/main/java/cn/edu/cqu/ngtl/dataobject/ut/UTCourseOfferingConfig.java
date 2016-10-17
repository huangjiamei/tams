package cn.edu.cqu.ngtl.dataobject.ut;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UNITIME_COURSE_OFFERING_CONFIG")
public class UTCourseOfferingConfig extends DataObjectBase implements Serializable{

	private static final long serialVersionUID = 1421778150066946005L;

	@Id
	@Column(name = "UNIQUEID")
	private Integer id;

	@Column(name = "COURSEOFFERING_ID")
	private Integer courseOfferingId;

	@ManyToOne
	@JoinColumn(name = "COURSEOFFERING_ID", insertable = false, updatable = false)
	private UTCourseOffering courseOffering;

	@Column(name = "CONFIGURATION_NAME")
	private String configName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCourseOfferingId() {
		return courseOfferingId;
	}

	public void setCourseOfferingId(Integer courseOfferingId) {
		this.courseOfferingId = courseOfferingId;
	}

	public UTCourseOffering getCourseOffering() {
		return courseOffering;
	}

	public void setCourseOffering(UTCourseOffering courseOffering) {
		this.courseOffering = courseOffering;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}
}
