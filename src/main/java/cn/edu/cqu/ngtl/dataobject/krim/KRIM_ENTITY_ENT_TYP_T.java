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
@Table(name = "KRIM_ENTITY_ENT_TYP_T")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class KRIM_ENTITY_ENT_TYP_T extends DataObjectBase implements Serializable {
	
	@Id
	@Column(name = "ENT_TYP_CD")
	private String entTypCd;

	@Id
	@Column(name = "ENTITY_ID")
	private String entId;
	
	@OneToOne
	@JoinColumn(name="ENTITY_ID",insertable = false, updatable = false)
	private KRIM_ENTITY_T krimEntityT;
	
	@Column(name = "ACTV_IND")
	private String actvInd;
	
	@Column(name = "LAST_UPDT_DT")
	private Date lastUpdtDt;

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

	public String getEntTypCd() {
		return entTypCd;
	}

	public void setEntTypCd(String entTypCd) {
		this.entTypCd = entTypCd;
	}

	public String getEntId() {
		return entId;
	}

	public void setEntId(String entId) {
		this.entId = entId;
	}

	public KRIM_ENTITY_T getKrimEntityT() {
		return krimEntityT;
	}

	public void setKrimEntityT(KRIM_ENTITY_T krimEntityT) {
		this.krimEntityT = krimEntityT;
	}
	
}
