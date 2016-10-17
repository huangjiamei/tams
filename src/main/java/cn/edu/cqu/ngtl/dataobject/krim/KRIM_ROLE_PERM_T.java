package cn.edu.cqu.ngtl.dataobject.krim;

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.jpa.converters.BooleanYNConverter;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViewType;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViews;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "KRIM_ROLE_PERM_T")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class KRIM_ROLE_PERM_T extends DataObjectBase implements Serializable {
	@Id
    @Column(name = "ROLE_PERM_ID")
    @GeneratedValue(generator="krimRolePermSeq")
    @SequenceGenerator(name="krimRolePermSeq",sequenceName="KRIM_ROLE_PERM_ID_S",allocationSize=1)
    private String id;

    @Column(name = "ROLE_ID")
    private String roleId;

    @Column(name = "PERM_ID")
    private String permissionId;
    
	@ManyToOne
	@JoinColumn(name="PERM_ID",insertable = false, updatable = false)
	private KRIM_PERM_T krimPermT;	
    
    @Column(name = "ACTV_IND")
    @Convert(converter = BooleanYNConverter.class)
    private boolean active;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return this.permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public boolean getActive() {
        return this.active;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

	public KRIM_PERM_T getKrimPermT() {
		return krimPermT;
	}

	public void setKrimPermT(KRIM_PERM_T krimPermT) {
		this.krimPermT = krimPermT;
	}
}
