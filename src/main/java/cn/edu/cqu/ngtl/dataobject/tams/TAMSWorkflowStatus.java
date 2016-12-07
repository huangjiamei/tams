package cn.edu.cqu.ngtl.dataobject.tams;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by liusijia on 2016/10/25.
 */
@Entity
@Table(name = "TAMS_WORKFLOW_STATUS")
public class TAMSWorkflowStatus extends DataObjectBase implements Serializable {
    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator="tamsWorkflowStatus")
    @SequenceGenerator(name="tamsWorkflowStatus",sequenceName="TAMS_WORKFLOW_STATUS_S",allocationSize=1)
    private String id;

    @Column(name = "WORKFLOW_STATUS")
    private String workflowStatus;

    @Column(name = "\"ORDER\"") //order为sql关键字
    private Integer order;


    public String getWorkflowFunctionId() {
        return workflowFunctionId;
    }

    public void setWorkflowFunctionId(String workflowFunctionId) {
        this.workflowFunctionId = workflowFunctionId;
    }

    @Column(name = "WORKFLOW_FUNCTION_ID")
    private String workflowFunctionId;


    @ManyToOne
    @JoinColumn(name = "WORKFLOW_FUNCTION_ID", insertable = false, updatable = false)
    private TAMSWorkflowFunctions workflowFunction;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {this.order = order;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
    }
}
