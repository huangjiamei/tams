package cn.edu.cqu.ngtl.dataobject.tams;

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.uif.UifConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by liusijia on 2016/10/22.
 */
@Entity
@Table(name = "TAMS_ISSUE_UPDATE")
public class TAMSIssueUpdate extends DataObjectBase implements Serializable{

    @Id
    @Column(name = "UNIQUEID")
    private String id;

    @Column(name = "ISSUE_ID")
    private String issueId;

    @Column(name = "UPDATER_ID")
    private String updaterId;

    @Column(name = "UPDATER_CONTENT")
    private String updaterContent;

    @Column(name = "UPDATER_TIME")
    private String updaterTime;

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

    public String getUpdaterContent() {
        return updaterContent;
    }

    public void setUpdaterContent(String updaterContent) {
        this.updaterContent = updaterContent;
    }

    public String getUpdaterTime() {
        return updaterTime;
    }

    public void setUpdaterTime(String updaterTime) {
        this.updaterTime = updaterTime;
    }
}
