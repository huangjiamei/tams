package cn.edu.cqu.ngtl.form.tamanagement;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaTravelSubsidy;
import cn.edu.cqu.ngtl.form.BaseForm;
import cn.edu.cqu.ngtl.viewobject.tainfo.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-10-12.
 */
public class TaInfoForm extends BaseForm implements Serializable {

    private static final long serialVersionUID = 8220820300868876320L;
    Boolean checkedTaListAll;
    /**
     * 教师评价相关
     */

    private List<AppraisalDetailViewObject> appraisalDetail;
    private List<IssueViewObject> allIssues;
    private String evaluateDetail;
    private String evaluate;
    /**
     * '我的助教'管理相关
     */

    private List<MyTaViewObject> allMyTa;
    private List<MyTaViewObject> allApplication;
    private MyTaViewObject selectedTa = new MyTaViewObject(); // 添加助教申请人时会用到，在搜索得到的助教列表中点击'查看'，把查询得到的ta数据放到这个变量中
    private List<MyTaViewObject> conditionTAList = new ArrayList<>(); // 查询时返回符合条件的talist，存储到这个list中
    private String indexTaListPage;//页面table的index
    //添加申请人时的输入
    private String StudentName;
    private String StudentNumber;
    /**
     * 助教列表页面相关
     */

    private List<TaInfoViewObject> allTaInfo;
    private String taDeApplyReason;
    private String taIdForDetailpage;
    private String classIdForDetailPage;
    private TaInfoViewObject selectedTaInfo;
    private String taCategoryName;
    private boolean canApprise;
    private String revocationReason;//撤销
    private String appraiseReason;//评优
    private String revocationReasonOptionFinder;//撤销optionfinder
    private String appraiseReasonOptionFinder;//评优optionfinder
    /**
     * 工作台相关
     */
    private List<WorkBenchViewObject> workbench;
    private List<TAMSTaTravelSubsidy> travelSubsidies;
    private List<MyClassViewObject> myClassViewObjects;
    private String taUniqueId;
    private String travelTime;
    private String travelNote;
    private String curClassId;
    private String travelTimeD;
    private String totalTravelSubsidy;

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
    /*
    助教页面搜索框
     */
    private String taCourseName;
    private String taCourseCode;
    private String taClassNumber;
    private String taTeacherName;
    private String taAssitantName;
    private String taAssitantIDNumber;
    private String taTeacherAppraise;
    private String taStuAppraise;
    private String taScore;
    private String taStatus;

    {
        // 赋初始空值测试
        conditionTAList.add(new MyTaViewObject());
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentNumber() {
        return StudentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        StudentNumber = studentNumber;
    }

    /**
     * Getter and Setter
     **/

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

    public void setAllApplication(List<MyTaViewObject> allApplication) {
        this.allApplication = allApplication;
    }

    public MyTaViewObject getSelectedTa() {
        return selectedTa;
    }

    public void setSelectedTa(MyTaViewObject selectedTa) {
        this.selectedTa = selectedTa;
    }

    public List<MyTaViewObject> getConditionTAList() {
        return conditionTAList;
    }

    public void setConditionTAList(List<MyTaViewObject> conditionTAList) {
        this.conditionTAList = conditionTAList;
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

    public String getTaDeApplyReason() {
        return taDeApplyReason;
    }

    public void setTaDeApplyReason(String taDeApplyReason) {
        this.taDeApplyReason = taDeApplyReason;
    }

    public String getTaIdForDetailpage() {
        return taIdForDetailpage;
    }

    public void setTaIdForDetailpage(String taIdForDetailpage) {
        this.taIdForDetailpage = taIdForDetailpage;
    }

    public String getClassIdForDetailPage() {
        return classIdForDetailPage;
    }

    public void setClassIdForDetailPage(String classIdForDetailPage) {
        this.classIdForDetailPage = classIdForDetailPage;
    }

    public TaInfoViewObject getSelectedTaInfo() {
        return selectedTaInfo;
    }

    public void setSelectedTaInfo(TaInfoViewObject selectedTaInfo) {
        this.selectedTaInfo = selectedTaInfo;
    }

    public List<AppraisalDetailViewObject> getAppraisalDetail() {
        return appraisalDetail;
    }

    public void setAppraisalDetail(List<AppraisalDetailViewObject> appraisalDetail) {
        this.appraisalDetail = appraisalDetail;
    }

    public List<WorkBenchViewObject> getWorkbench() {
        return workbench;
    }

    public void setWorkbench(List<WorkBenchViewObject> workbench) {
        this.workbench = workbench;
    }

    public List<TAMSTaTravelSubsidy> getTravelSubsidies() {
        return travelSubsidies;
    }

    public void setTravelSubsidies(List<TAMSTaTravelSubsidy> travelSubsidies) {
        this.travelSubsidies = travelSubsidies;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getTravelNote() {
        return travelNote;
    }

    public void setTravelNote(String travelNote) {
        this.travelNote = travelNote;
    }

    public String getTaUniqueId() {
        return taUniqueId;
    }

    public void setTaUniqueId(String taUniqueId) {
        this.taUniqueId = taUniqueId;
    }

    public String getCurClassId() {
        return curClassId;
    }

    public void setCurClassId(String curClassId) {
        this.curClassId = curClassId;
    }

    public String getTaCourseName() {
        return taCourseName;
    }

    public void setTaCourseName(String taCourseName) {
        this.taCourseName = taCourseName;
    }

    public String getTaCourseCode() {
        return taCourseCode;
    }

    public void setTaCourseCode(String taCourseCode) {
        this.taCourseCode = taCourseCode;
    }

    public String getTaClassNumber() {
        return taClassNumber;
    }

    public void setTaClassNumber(String taClassNumber) {
        this.taClassNumber = taClassNumber;
    }

    public String getTaTeacherName() {
        return taTeacherName;
    }

    public void setTaTeacherName(String taTeacherName) {
        this.taTeacherName = taTeacherName;
    }

    public String getTaAssitantName() {
        return taAssitantName;
    }

    public void setTaAssitantName(String taAssitantName) {
        this.taAssitantName = taAssitantName;
    }

    public String getTaAssitantIDNumber() {
        return taAssitantIDNumber;
    }

    public void setTaAssitantIDNumber(String taAssitantIDNumber) {
        this.taAssitantIDNumber = taAssitantIDNumber;
    }


    public String getTaTeacherAppraise() {
        return taTeacherAppraise;
    }

    public void setTaTeacherAppraise(String taTeacherAppraise) {
        this.taTeacherAppraise = taTeacherAppraise;
    }

    public String getTaStuAppraise() {
        return taStuAppraise;
    }

    public void setTaStuAppraise(String taStuAppraise) {
        this.taStuAppraise = taStuAppraise;
    }

    public String getTaScore() {
        return taScore;
    }

    public void setTaScore(String taScore) {
        this.taScore = taScore;
    }

    public String getTaStatus() {
        return taStatus;
    }

    public void setTaStatus(String taStatus) {
        this.taStatus = taStatus;
    }

    public String getRevocationReason() {
        return revocationReason;
    }

    public void setRevocationReason(String revocationReason) {
        this.revocationReason = revocationReason;
    }

    public String getAppraiseReason() {
        return appraiseReason;
    }

    public void setAppraiseReason(String appraiseReason) {
        this.appraiseReason = appraiseReason;
    }

    public List<MyClassViewObject> getMyClassViewObjects() {
        return myClassViewObjects;
    }

    public void setMyClassViewObjects(List<MyClassViewObject> myClassViewObjects) {
        this.myClassViewObjects = myClassViewObjects;
    }

    public String getRevocationReasonOptionFinder() {
        return revocationReasonOptionFinder;
    }

    public void setRevocationReasonOptionFinder(String revocationReasonOptionFinder) {
        this.revocationReasonOptionFinder = revocationReasonOptionFinder;
    }

    public String getAppraiseReasonOptionFinder() {
        return appraiseReasonOptionFinder;
    }

    public void setAppraiseReasonOptionFinder(String appraiseReasonOptionFinder) {
        this.appraiseReasonOptionFinder = appraiseReasonOptionFinder;
    }

    public Boolean getCheckedTaListAll() {
        return checkedTaListAll;
    }

    public void setCheckedTaListAll(Boolean checkedTaListAll) {
        this.checkedTaListAll = checkedTaListAll;
    }

    public String getIndexTaListPage() {
        return indexTaListPage;
    }

    public void setIndexTaListPage(String indexTaListPage) {
        this.indexTaListPage = indexTaListPage;
    }

    public String getTaCategoryName() {
        return taCategoryName;
    }

    public void setTaCategoryName(String taCategoryName) {
        this.taCategoryName = taCategoryName;
    }

    public String getTravelTimeD() {
        return travelTimeD;
    }

    public void setTravelTimeD(String travelTimeD) {
        this.travelTimeD = travelTimeD;
    }

    public boolean isCanApprise() {
        return canApprise;
    }

    public void setCanApprise(boolean canApprise) {
        this.canApprise = canApprise;
    }

    public String getTotalTravelSubsidy() {
        return totalTravelSubsidy;
    }

    public void setTotalTravelSubsidy(String totalTravelSubsidy) {
        this.totalTravelSubsidy = totalTravelSubsidy;
    }
}
