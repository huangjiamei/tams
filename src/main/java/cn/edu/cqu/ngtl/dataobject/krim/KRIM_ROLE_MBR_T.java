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
@Table(name = "KRIM_ROLE_MBR_T")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class KRIM_ROLE_MBR_T extends DataObjectBase implements Serializable {

	@Id
	@Column(name = "ROLE_MBR_ID")
    @GeneratedValue(generator="krimRoleMbrSeq")
    @SequenceGenerator(name="krimRoleMbrSeq",sequenceName="KRIM_ROLE_MBR_ID_S",allocationSize=1)
	private String id;
	
	@Column(name = "ROLE_ID")
	private String roleId;

	@ManyToOne
	@JoinColumn(name="ROLE_ID",insertable = false, updatable = false)
	private KRIM_ROLE_T krimRoleT;
	
	@Column(name = "MBR_ID")
	private String mbrId;
	
	@Column(name = "MBR_TYP_CD")
	private String mriTypCd;
	
	@Column(name = "ACTV_FRM_DT")
	private Date actvFrmDt;
	
	@Column(name = "ACTV_TO_DT")
	private Date actvToDt;
	
	@Column(name = "LAST_UPDT_DT")
	private Date lastUpdtDt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMbrId() {
		return mbrId;
	}

	public void setMbrId(String mbrId) {
		this.mbrId = mbrId;
	}

	public String getMriTypCd() {
		return mriTypCd;
	}

	public void setMriTypCd(String mriTypCd) {
		this.mriTypCd = mriTypCd;
	}

	public Date getActvFrmDt() {
		return actvFrmDt;
	}

	public void setActvFrmDt(Date actvFrmDt) {
		this.actvFrmDt = actvFrmDt;
	}

	public Date getActvToDt() {
		return actvToDt;
	}

	public void setActvToDt(Date actvToDt) {
		this.actvToDt = actvToDt;
	}

	public Date getLastUpdtDt() {
		return lastUpdtDt;
	}

	public void setLastUpdtDt(Date lastUpdtDt) {
		this.lastUpdtDt = lastUpdtDt;
	}

	public KRIM_ROLE_T getKrimRoleT() {
		return krimRoleT;
	}

	public void setKrimRoleT(KRIM_ROLE_T krimRoleT) {
		this.krimRoleT = krimRoleT;
	}
}
