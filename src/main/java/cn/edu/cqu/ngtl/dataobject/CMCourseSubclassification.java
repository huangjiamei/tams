package cn.edu.cqu.ngtl.dataobject;
import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViewType;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViews;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CM_COURSE_SUBCLASSIFICATION")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class CMCourseSubclassification extends DataObjectBase implements Serializable{

	/**
	 * @author oumingliao
	 */
	private static final long serialVersionUID = 4033961810312107465L;

	@Id
	@Column(name = "UNIQUEID")
	private Integer id;
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME")
	private String name;
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name="CLASSIFICATION_ID", insertable=false, updatable=false)
	private CMCourseClassification classification;
	public CMCourseClassification getClassification() {
		return this.classification;
	}
	public void setClassification(CMCourseClassification classification) {
		this.classification = classification;
	}

	@Column(name = "CLASSIFICATION_ID")
	private Integer classificationId;
	public Integer getClassificationId() {
		return this.classificationId;
	}
	public void setClassificationId(Integer classificationId) {
		this.classificationId = classificationId;
	}


}
