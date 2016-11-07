package cn.edu.cqu.ngtl.dataobject.tams;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wjy on 16-11-7.
 */
@Entity
@Table(name = "TAMS_WORKFLOW_FUNCTIONS")
public class TAMSWorkflowFunctions extends DataObjectBase implements Serializable {
    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator = "tamsworkflowstatus")
    @SequenceGenerator(name = "tamsworkflowstatus", sequenceName = "TAMS_WORKFLOW_STATUS", allocationSize = 1)
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
