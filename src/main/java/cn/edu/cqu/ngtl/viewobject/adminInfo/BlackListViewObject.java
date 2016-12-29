package cn.edu.cqu.ngtl.viewobject.adminInfo;

/**
 * Created by awake on 2016/12/29.
 */
public class BlackListViewObject {

    private String stuName ;

    private String stuId;

    private String joinTime;

    private String executorName;

    private String executorRole;


    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getExecutorRole() {
        return executorRole;
    }

    public void setExecutorRole(String executorRole) {
        this.executorRole = executorRole;
    }
}
