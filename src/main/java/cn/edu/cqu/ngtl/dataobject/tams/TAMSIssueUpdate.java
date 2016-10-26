package cn.edu.cqu.ngtl.dataobject.tams;

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.uif.UifConstants;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by liusijia on 2016/10/22.
 */
@Entity
@Table(name = "TAMS_ISSUE_UPDATE")
public class TAMSIssueUpdate extends DataObjectBase implements Serializable{

    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator="tamsIssueUpdate")
    @SequenceGenerator(name="tamsIssueUpdate",sequenceName="TAMS_ISSUE_UPDATE_S",allocationSize=1)
    private String id;

    @Column(name = "ISSUE_ID")
    private String issueId;

    @Column(name = "UPDATER_ID")
    private String updaterId;

    @Column(name = "UPDATE_CONTENT")
    private String updateContent;

    @Column(name = "UPDATE_TIME")
    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
