package cn.edu.cqu.ngtl.viewobject.tainfo;

/**
 * Created by tangjing on 16-11-3.
 */
public class IssueViewObject {

    private String issueType;

    private String issueCount;

    private String completion;

    private String likeRate;

    private String commentCount;

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getIssueCount() {
        return issueCount;
    }

    public void setIssueCount(String issueCount) {
        this.issueCount = issueCount;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public String getLikeRate() {
        return likeRate;
    }

    public void setLikeRate(String likeRate) {
        this.likeRate = likeRate;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }
}
