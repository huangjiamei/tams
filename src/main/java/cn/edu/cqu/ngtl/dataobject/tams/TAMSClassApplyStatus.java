package cn.edu.cqu.ngtl.dataobject.tams;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tangjing on 16-11-26.
 */
@Entity
@Table(name = "TAMS_CLASS_APPLY_STATUS")
public class TAMSClassApplyStatus extends DataObjectBase implements Serializable {

    @Id
    @Column(name = "UNIQUEID")
    String id;

    @Column(name = "CLASS_ID")
    String classId;

    @Column(name = "STATUS")
    String workflowStatusId;

    @ManyToOne
    @JoinColumn(name = "STATUS", insertable = false, updatable = false)
    TAMSWorkflowStatus workflowStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getWorkflowStatusId() {
        return workflowStatusId;
    }

    public void setWorkflowStatusId(String workflowStatusId) {
        this.workflowStatusId = workflowStatusId;
    }

    public TAMSWorkflowStatus getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(TAMSWorkflowStatus workflowStatus) {
        this.workflowStatus = workflowStatus;
    }
}
