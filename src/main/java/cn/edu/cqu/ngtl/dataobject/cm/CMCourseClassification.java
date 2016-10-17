package cn.edu.cqu.ngtl.dataobject.cm;
import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViewType;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViews;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "CM_COURSE_CLASSIFICATION")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class CMCourseClassification extends DataObjectBase implements Serializable{

	private static final long serialVersionUID = 4033961810312107465L;

	@Id
	@Column(name = "UNIQUEID")
	private Integer id;

	@Column(name = "NAME")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
