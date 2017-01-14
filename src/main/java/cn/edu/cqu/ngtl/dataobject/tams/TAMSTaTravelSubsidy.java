package cn.edu.cqu.ngtl.dataobject.tams;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by awake on 2016/12/3.
 */

@Entity
@Table(name = "TAMS_TA_TRAVELSUBSIDY")
public class TAMSTaTravelSubsidy extends DataObjectBase implements Serializable {

    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator = "tamsTaTravelSubsidy")
    @SequenceGenerator(name = "tamsTaTravelSubsidy", sequenceName = "TAMS_TA_TRAVELSUBSIDY_S", allocationSize = 1)
    private String id;


    @Column(name = "TAMS_TA_ID")
    private String tamsTaId;


    @Column(name = "TRAVEL_TIME")
    private String travelTime;


    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "WORKFLOW_STATUS_ID")
    private String workflowStatusId;


    @ManyToOne
    @JoinColumn(name = "TAMS_TA_ID", insertable = false, updatable = false)
    private TAMSTa tamsTa;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTamsTaId() {
        return tamsTaId;
    }

    public void setTamsTaId(String tamsTaId) {
        this.tamsTaId = tamsTaId;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TAMSTa getTamsTa() {
        return tamsTa;
    }

    public void setTamsTa(TAMSTa tamsTa) {
        this.tamsTa = tamsTa;
    }

    public String getWorkflowStatusId() {
        return workflowStatusId;
    }

    public void setWorkflowStatusId(String workflowStatusId) {
        this.workflowStatusId = workflowStatusId;
    }
}
