package cn.edu.cqu.ngtl.dataobject.tams;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by liusijia on 2016/10/25.
 */

@Entity
@Table(name = "TAMS_WORKFLOW_STATUS_R")
public class TAMSWorkflowStatusR extends DataObjectBase implements Serializable {
    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator="tamsWorkflowStatusR")
    @SequenceGenerator(name="tamsWorkflowStatusR",sequenceName="TAMS_WORKFLOW_STATUS_R_S",allocationSize=1)
    private String id;

    @Column(name = "STATUS_ID_1")
    private String statusId1;

    @Column(name = "STATUS_ID_2")
    private String statusId2;

    @Column(name = "ROLE_FUNCTION_ID")
    private String roleFunctionId;

    @ManyToOne
    @JoinColumn(name = "STATUS_ID_1",insertable = false, updatable = false)
    private TAMSWorkflowStatus status1;

    @ManyToOne
    @JoinColumn(name = "STATUS_ID_2",insertable = false, updatable = false)
    private TAMSWorkflowStatus status2;

    @ManyToOne
    @JoinColumn(name = "ROLE_FUNCTION_ID",insertable = false,updatable = false)
    private TAMSWorkflowRoleFunction roleFunction;

    public String getRoleFunctionId() {
        return roleFunctionId;
    }

    public void setRoleFunctionId(String roleFunctionId) {
        this.roleFunctionId = roleFunctionId;
    }

    public TAMSWorkflowRoleFunction getRoleFunction() {
        return roleFunction;
    }

    public void setRoleFunction(TAMSWorkflowRoleFunction roleFunction) {
        this.roleFunction = roleFunction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatusId1() {
        return statusId1;
    }

    public void setStatusId1(String statusId1) {
        this.statusId1 = statusId1;
    }

    public String getStatusId2() {
        return statusId2;
    }

    public void setStatusId2(String statusId2) {
        this.statusId2 = statusId2;
    }

    public TAMSWorkflowStatus getStatus1() {
        return status1;
    }

    public void setStatus1(TAMSWorkflowStatus status1) {
        this.status1 = status1;
    }

    public TAMSWorkflowStatus getStatus2() {
        return status2;
    }

    public void setStatus2(TAMSWorkflowStatus status2) {
        this.status2 = status2;
    }
}
