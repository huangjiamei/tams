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
@Table(name = "TAMS_ISSUE")
public class TAMSIssue extends DataObjectBase implements Serializable{

    @Id
    @Column(name = "UNIQUEID")
    private String id;

    @Column(name = "ISSUE_TYPE")
    private String issueType;

    @Column(name = "CLASS_ID")
    private String classId;

    @Column(name = "ISSUE_THEME")
    private String issueTheme;

    @Column(name = "ISSUE_DESCRIPTION")
    private String issueDescription;

    @Column(name = "END_TIME")
    private String endTime;

    @Column(name = "ISSUE_STATUS")
    private String issueStatus;

    @Column(name = "ASSIGNED_TO_DO")
    private String assignedToDo;

    @Column(name = "PRIORITY_ID")
    private String priorityID;

    @Column(name = "CREATE_TIME")
    private String createTime;

    @Column(name = "UPDATE_TIME")
    private String updateTime;

    @Column(name = "START_TIME")
    private String startTime;

    @Column(name = "DONE_RATIO")
    private String doneRatio;

    @Column(name = "ESTIMATED_HOUR")
    private String estimatedHour;

    @Column(name = "PARENT_ID")
    private String parentId;

    @Column(name = "CLOSED_TIME")
    private String closedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getIssueTheme() {
        return issueTheme;
    }

    public void setIssueTheme(String issueTheme) {
        this.issueTheme = issueTheme;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getAssignedToDo() {
        return assignedToDo;
    }

    public void setAssignedToDo(String assignedToDo) {
        this.assignedToDo = assignedToDo;
    }

    public String getPriorityID() {
        return priorityID;
    }

    public void setPriorityID(String priorityID) {
        this.priorityID = priorityID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDoneRatio() {
        return doneRatio;
    }

    public void setDoneRatio(String doneRatio) {
        this.doneRatio = doneRatio;
    }

    public String getEstimatedHour() {
        return estimatedHour;
    }

    public void setEstimatedHour(String estimatedHour) {
        this.estimatedHour = estimatedHour;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(String closedTime) {
        this.closedTime = closedTime;
    }
}
