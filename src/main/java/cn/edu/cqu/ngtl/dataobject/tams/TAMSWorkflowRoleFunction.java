package cn.edu.cqu.ngtl.dataobject.tams;

import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_T;
import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wjy on 16-11-7.
 */
@Entity
@Table(name = "TAMS_WORKFLOW_ROLE_FUNCTION")
public class TAMSWorkflowRoleFunction extends DataObjectBase implements Serializable {
    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator = "tamsworkflowrolefunction")
    @SequenceGenerator(name = "tamsworkflowrolefunction", sequenceName = "TAMS_WORKFLOW_ROLE_FUNCTION_S", allocationSize = 1)
    private String id;

    @Column(name = "ROLE_ID")
    private String roleId;

    @Column(name = "WORKFLOW_FUNCTION_ID")
    private String workflowFunctionId;

    @ManyToOne
    @JoinColumn(name = "WORKFLOW_FUNCTION_ID", insertable = false, updatable = false)
    private TAMSWorkflowFunctions workflowFunction;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID",insertable = false,updatable = false)
    private KRIM_ROLE_T role;

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

    public String getWorkflowFunctionId() {
        return workflowFunctionId;
    }

    public TAMSWorkflowFunctions getWorkflowFunction() {
        return workflowFunction;
    }

    public void setWorkflowFunction(TAMSWorkflowFunctions workflowFunction) {
        this.workflowFunction = workflowFunction;
    }

    public KRIM_ROLE_T getRole() {
        return role;
    }

    public void setRole(KRIM_ROLE_T role) {
        this.role = role;
    }

    public void setWorkflowFunctionId(String workflowFunctionId) {
        this.workflowFunctionId = workflowFunctionId;

    }
}
