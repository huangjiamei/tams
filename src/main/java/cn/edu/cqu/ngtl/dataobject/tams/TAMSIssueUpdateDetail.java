package cn.edu.cqu.ngtl.dataobject.tams;

import org.kuali.rice.krad.bo.DataObjectBase;
import org.omg.dds.PRESENTATION_QOS_POLICY_ID;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by liusijia on 2016/10/22.
 */
@Entity
@Table(name = "TAMS_ISSUE_UPDATE_DETAIL")
public class TAMSIssueUpdateDetail extends DataObjectBase implements Serializable{

    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator="tamsIssueUpdateDetail")
    @SequenceGenerator(name="tamsIssueUpdateDetail",sequenceName="TAMS_ISSUE_UPDATE_DETAILS",allocationSize=1)
    private String id;

    @Column(name = "ISSUE_UPDATE_ID")
    private String issueUpdateId;

    @Column(name = "UPDATE_ATTRIBUTE")
    private String updateAttribute;

    @Column(name = "OLD_VALUE")
    private String oldValue;

    @Column(name = "NEW_VALUE")
    private String newValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssueUpdateId() {
        return issueUpdateId;
    }

    public void setIssueUpdateId(String issueUpdateId) {
        this.issueUpdateId = issueUpdateId;
    }

    public String getUpdateAttribute() {
        return updateAttribute;
    }

    public void setUpdateAttribute(String updateAttribute) {
        this.updateAttribute = updateAttribute;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
