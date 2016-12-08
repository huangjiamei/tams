package cn.edu.cqu.ngtl.dataobject.ut;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UNITIME_OFFERING_CONFIG")
public class UTCourseOfferingConfig extends DataObjectBase implements Serializable{

	private static final long serialVersionUID = 1421778150066946005L;

	@Id
	@Column(name = "UNIQUEID")
//	@GeneratedValue(generator="utCoConfigSeq")
//	@SequenceGenerator(name="utCoConfigSeq",sequenceName="UNITIME_OFFERING_CONFIG_S",allocationSize=1)
	private String id;

	@Column(name = "COURSEOFFERING_ID")
	private String courseOfferingId;

	@ManyToOne
	@JoinColumn(name = "COURSEOFFERING_ID", insertable = false, updatable = false)
	private UTCourseOffering courseOffering;

	@Column(name = "CONFIGURATION_NAME")
	private String configName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCourseOfferingId() {
		return courseOfferingId;
	}

	public void setCourseOfferingId(String courseOfferingId) {
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
