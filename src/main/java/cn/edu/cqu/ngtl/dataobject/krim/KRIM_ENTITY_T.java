package cn.edu.cqu.ngtl.dataobject.krim;

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViewType;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViews;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author Bill
 */

@Entity
@Table(name = "KRIM_ENTITY_T")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class KRIM_ENTITY_T extends DataObjectBase implements Serializable {

	@Id
	@Column(name = "ENTITY_ID")
    @GeneratedValue(generator="krimEntitySeq")
    @SequenceGenerator(name="krimEntitySeq",sequenceName="KRIM_ENTITY_ID_S",allocationSize=1)
	private String id;

	@Column(name = "ACTV_IND")
	private String actvInd;
	
	@Column(name = "LAST_UPDT_DT")
	private Date lastUpdtDt;
	
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getActvInd() {
		return actvInd;
	}
	public void setActvInd(String actvInd) {
		this.actvInd = actvInd;
	}
	public Date getLastUpdtDt() {
		return lastUpdtDt;
	}
	public void setLastUpdtDt(Date lastUpdtDt) {
		this.lastUpdtDt = lastUpdtDt;
	}
}
