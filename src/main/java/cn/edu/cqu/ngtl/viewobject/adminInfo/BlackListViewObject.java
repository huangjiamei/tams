package cn.edu.cqu.ngtl.viewobject.adminInfo;

import javax.persistence.Transient;

/**
 * Created by awake on 2016/12/29.
 */
public class BlackListViewObject {

    private String stuName ;

    private String stuId;

    private String joinTime;

    private String executorName;

    private String executorRole;

    @Transient
    public static String[] getAttrTittles(){
        String[] attrTittles={
                "名称","学号","加入时间","操作执行者名称"

        };
        return attrTittles;
    }

    @Transient
    public String[] getContents(){
        String[] contents=new String[4];
        contents[0]=getStuName();
        contents[1]=getStuId();
        contents[2]=getJoinTime();
        contents[3]=getExecutorName();

        return contents;
    }
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
