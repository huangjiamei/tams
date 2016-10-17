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
@Table(name = "KRIM_PRNCPL_T")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class KRIM_PRNCPL_T extends DataObjectBase implements Serializable {

	@Id
	@Column(name = "PRNCPL_ID")
    @GeneratedValue(generator="krimPrncplSeq")
    @SequenceGenerator(name="krimPrncplSeq",sequenceName="KRIM_PRNCPL_ID_S",allocationSize=1)
	private String id;
	
	@Column(name = "PRNCPL_NM")
	private String prncplNm;

	@Column(name = "ENTITY_ID")
	private String entId;
	
	@OneToOne
	@JoinColumn(name="ENTITY_ID",insertable = false, updatable = false)
	private KRIM_ENTITY_T krimEntityT;
	
	@Column(name = "PRNCPL_PSWD")
	private String prncplPswd;
	
	@Column(name = "ACTV_IND")
	private String actvInd;
	
	@Column(name = "LAST_UPDT_DT")
	private Date lastUpdtDt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrncplNm() {
		return prncplNm;
	}

	public void setPrncplNm(String prncplNm) {
		this.prncplNm = prncplNm;
	}

	public String getEntId() {
		return entId;
	}

	public void setEntId(String entId) {
		this.entId = entId;
	}

	public String getPrncplPswd() {
		return prncplPswd;
	}

	public void setPrncplPswd(String prncplPswd) {
		this.prncplPswd = prncplPswd;
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

	public KRIM_ENTITY_T getKrimEntityT() {
		return krimEntityT;
	}

	public void setKrimEntityT(KRIM_ENTITY_T krimEntityT) {
		this.krimEntityT = krimEntityT;
	}
}
