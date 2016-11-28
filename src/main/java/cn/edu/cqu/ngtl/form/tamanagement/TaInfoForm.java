package cn.edu.cqu.ngtl.form.tamanagement;

import cn.edu.cqu.ngtl.form.BaseForm;
import cn.edu.cqu.ngtl.viewobject.tainfo.IssueViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.MyTaViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-10-12.
 */
public class TaInfoForm extends BaseForm implements Serializable {

    private static final long serialVersionUID = 8220820300868876320L;

    /**
     * 教师评价相关
     */

    private List<IssueViewObject> allIssues;

    private String evaluateDetail;

    private String evaluate;

    /**
     * '我的助教'管理相关
     */

    private List<MyTaViewObject> allMyTa;

    private List<MyTaViewObject> allApplication;

    private MyTaViewObject selectedTa=new MyTaViewObject(); // 添加助教申请人时会用到，在搜索得到的助教列表中点击'查看'，把查询得到的ta数据放到这个变量中
    private List<MyTaViewObject> conditionTAList=new ArrayList<>(); // 查询时返回符合条件的talist，存储到这个list中

    /**
     * 助教列表相关
     */

    private List<TaInfoViewObject> allTaInfo;

    /**
     * 任务相关
     */
    private String taskCategory;

    private String theme;

    private String assignment;

    private String descriptionAndAttachement;

    private String startTime;

    private String endTime;

    private String completionDegree;

    private String singleTimeConsume;

    private String totalTimeConsume;

    /** Getter and Setter **/

    public List<IssueViewObject> getAllIssues() {
        return allIssues;
    }

    public void setAllIssues(List<IssueViewObject> allIssues) {
        this.allIssues = allIssues;
    }

    public String getEvaluateDetail() {
        return evaluateDetail;
    }

    public void setEvaluateDetail(String evaluateDetail) {
        this.evaluateDetail = evaluateDetail;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public List<MyTaViewObject> getAllApplication() {
        return allApplication;
    }

    public MyTaViewObject getSelectedTa() {
//        if(selectedTa==null){
//            selectedTa=new MyTaViewObject();
//        }
        return selectedTa;
    }

    public List<MyTaViewObject> getConditionTAList() {
        return conditionTAList;
    }

    public void setConditionTAList(List<MyTaViewObject> conditionTAList) {
        this.conditionTAList = conditionTAList;
    }

    public void setSelectedTa(MyTaViewObject selectedTa) {
        this.selectedTa = selectedTa;
    }

    public void setAllApplication(List<MyTaViewObject> allApplication) {
        this.allApplication = allApplication;
    }

    public List<TaInfoViewObject> getAllTaInfo() {
        return allTaInfo;
    }

    public void setAllTaInfo(List<TaInfoViewObject> allTaInfo) {
        this.allTaInfo = allTaInfo;
    }

    public List<MyTaViewObject> getAllMyTa() {
        return allMyTa;
    }

    public void setAllMyTa(List<MyTaViewObject> allMyTa) {
        this.allMyTa = allMyTa;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getDescriptionAndAttachement() {
        return descriptionAndAttachement;
    }

    public void setDescriptionAndAttachement(String descriptionAndAttachement) {
        this.descriptionAndAttachement = descriptionAndAttachement;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCompletionDegree() {
        return completionDegree;
    }

    public void setCompletionDegree(String completionDegree) {
        this.completionDegree = completionDegree;
    }

    public String getSingleTimeConsume() {
        return singleTimeConsume;
    }

    public void setSingleTimeConsume(String singleTimeConsume) {
        this.singleTimeConsume = singleTimeConsume;
    }

    public String getTotalTimeConsume() {
        return totalTimeConsume;
    }

    public void setTotalTimeConsume(String totalTimeConsume) {
        this.totalTimeConsume = totalTimeConsume;
    }
}
