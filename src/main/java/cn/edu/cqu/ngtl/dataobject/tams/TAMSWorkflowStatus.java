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
    @SequenceGenerator(name="tamsWorlflowStatus",sequenceName="TAMS_WORKFLOW_STATUS_S",allocationSize=1)
    private String id;

    @Column(name = "WORKFLOW_STATUS")
    private String workflowStatus;

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
