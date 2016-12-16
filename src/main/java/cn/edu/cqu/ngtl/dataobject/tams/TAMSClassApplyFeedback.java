package cn.edu.cqu.ngtl.dataobject.tams;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by awake on 2016/12/15.
 */
@Entity
@Table(name = "TAMS_CLASS_APPLY_FEEDBACK")
public class TAMSClassApplyFeedback extends DataObjectBase implements Serializable {
    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator="tamsclassfeedbacks")
    @SequenceGenerator(name="tamsclassfeedbacks",sequenceName="TAMS_CLASS_APPLY_FEEDBACK_S",allocationSize=1)
    private String id;


    @Column(name = "CLASS_ID")
    private String classId;

    @Column(name = "FEEDBACK")
    private String feedback;

    @Column(name = "FEEDBACK_TIME")
    private String feedbackTime;

    @Column(name = "FEEDBACK_UID")
    private String feedbackUid;

    @Column(name = "OLD_STATUS")
    private String oldStatus;

    @Column(name = "NEW_STATUS")
    private String newStatus;

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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(String feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public String getFeedbackUid() {
        return feedbackUid;
    }

    public void setFeedbackUid(String feedbackUid) {
        this.feedbackUid = feedbackUid;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
}
