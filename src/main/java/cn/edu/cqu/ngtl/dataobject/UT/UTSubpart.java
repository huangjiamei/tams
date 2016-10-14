package cn.edu.cqu.ngtl.dataobject.UT;

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.Label;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViewType;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViews;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "UNITIME_SUBPART")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class UTSubpart extends DataObjectBase implements Serializable {

	private static final long serialVersionUID = -5887523683834231299L;

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
	@Label("name")
	private String name;
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 复写equals方法，用于判断两个UTSubpart是否一样
	 * @author hmj
	 */
	@Override
    public boolean equals(Object obj){
		UTSubpart subpart = (UTSubpart)obj;
		if(subpart == null)
			return false;
    	return subpart.getId().equals(this.id);
    }
}
