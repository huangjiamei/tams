package cn.edu.cqu.ngtl.form.adminmanagement;

import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PERM_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_T;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSIssueType;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaCategory;
import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;
import cn.edu.cqu.ngtl.form.BaseForm;
import cn.edu.cqu.ngtl.viewobject.adminInfo.CourseManagerViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.TermManagerViewObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by awake on 2016-10-21.
 */
public class AdminInfoForm extends BaseForm implements Serializable {


    private static final long serialVersionUID = -1974217788100313751L;

    /**
     * 批次管理页面
     */

    private List<TermManagerViewObject> allTerms;

    private TermManagerViewObject newTerm;

    /**
     * 任务类别管理页面
     */

    private List<TAMSIssueType> allIssueTypes;

    private TAMSIssueType newIssueType;

    /**
     * 课程类别管理页面
     */

    private List<CMCourseClassification> allClassifications;

    private String newClassification;

    private CMCourseClassification oldClassification;

    /**
     * 助教类别管理页面
     */

    private List<TAMSTaCategory> allTaCategories;

    private TAMSTaCategory taCategoryObj;

    private TAMSTaCategory newTaCategory;

    private TAMSTaCategory oldTaCategory;
    /**
     * 课程负责人管理页面
     */
    private String courseNm;
    private String courseNmb;
    private String courseManager;
    private String InstructorCode;
    private List<CourseManagerViewObject> courseManagerViewObjects;
    private String searchCourseNm;
    private String searchCourseNmb;
    private String searchCourseManager;
    private String searchCourseInsCode;
    private CourseManagerViewObject selectedCourseManagerObject;
    private Integer courseManagerIndex;


    /**
     * 页面错误信息
     */
    private String errMsg;

    /**
     * 角色权限相关页面
     */

    private List<KRIM_ROLE_T> RMPkrimRoleTs;
    private KRIM_ROLE_T RMPkrimRoleT;
    private List<KRIM_PERM_T> RMPkrimPermTs;
    private String permissionNM;
    private String permissionContent;
    private String permissionStatus;
    private Integer permissionIndex;

    /**
     * 用户角色管理页面
     */

    private List<KRIM_ROLE_T> URMPkrimRoleTs;
    private List<UTInstructor> URMutInstructors;
    private UTInstructor URMutInstructor;
    private String URMsearchDepartmentId;


    public TAMSTaCategory getTaCategoryObj() {
        return taCategoryObj;
    }

    public void setTaCategoryObj(TAMSTaCategory taCategoryObj) {
        this.taCategoryObj = taCategoryObj;
    }

    public TermManagerViewObject getNewTerm() {
        return newTerm;
    }

    public void setNewTerm(TermManagerViewObject newTerm) {
        this.newTerm = newTerm;
    }

    public List<TermManagerViewObject> getAllTerms() {
        return allTerms;
    }

    public void setAllTerms(List<TermManagerViewObject> allTerms) {
        this.allTerms = allTerms;
    }

    public TAMSIssueType getNewIssueType() {
        return newIssueType;
    }

    public void setNewIssueType(TAMSIssueType newIssueType) {
        this.newIssueType = newIssueType;
    }

    public List<TAMSIssueType> getAllIssueTypes() {
        return allIssueTypes;
    }

    public void setAllIssueTypes(List<TAMSIssueType> allIssueTypes) {
        this.allIssueTypes = allIssueTypes;
    }

    public TAMSTaCategory getNewTaCategory() {
        return newTaCategory;
    }

    public void setNewTaCategory(TAMSTaCategory newTaCategory) {
        this.newTaCategory = newTaCategory;
    }

    public TAMSTaCategory getOldTaCategory() {
        return oldTaCategory;
    }

    public void setOldTaCategory(TAMSTaCategory oldTaCategory) {
        this.oldTaCategory = oldTaCategory;
    }

    public List<TAMSTaCategory> getAllTaCategories() {
        return allTaCategories;
    }

    public void setAllTaCategories(List<TAMSTaCategory> allTaCategories) {
        this.allTaCategories = allTaCategories;
    }

    public CMCourseClassification getOldClassification() {
        return oldClassification;
    }

    public void setOldClassification(CMCourseClassification oldClassification) {
        this.oldClassification = oldClassification;
    }

    public String getNewClassification() {
        return newClassification;
    }

    public void setNewClassification(String newClassification) {
        this.newClassification = newClassification;
    }

    public List<CMCourseClassification> getAllClassifications() {
        return allClassifications;
    }

    public void setAllClassifications(List<CMCourseClassification> allClassifications) {
        this.allClassifications = allClassifications;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<KRIM_ROLE_T> getRMPkrimRoleTs() {
        return RMPkrimRoleTs;
    }

    public void setRMPkrimRoleTs(List<KRIM_ROLE_T> RMPkrimRoleTs) {
        this.RMPkrimRoleTs = RMPkrimRoleTs;
    }

    public KRIM_ROLE_T getRMPkrimRoleT() {
        return RMPkrimRoleT;
    }

    public void setRMPkrimRoleT(KRIM_ROLE_T RMPkrimRoleT) {
        this.RMPkrimRoleT = RMPkrimRoleT;
    }

    public List<KRIM_PERM_T> getRMPkrimPermTs() {
        return RMPkrimPermTs;
    }

    public void setRMPkrimPermTs(List<KRIM_PERM_T> RMPkrimPermTs) {
        this.RMPkrimPermTs = RMPkrimPermTs;
    }

    public List<KRIM_ROLE_T> getURMPkrimRoleTs() {
        return URMPkrimRoleTs;
    }

    public void setURMPkrimRoleTs(List<KRIM_ROLE_T> URMPkrimRoleTs) {
        this.URMPkrimRoleTs = URMPkrimRoleTs;
    }

    public List<UTInstructor> getURMutInstructors() {
        return URMutInstructors;
    }

    public void setURMutInstructors(List<UTInstructor> URMutInstructors) {
        this.URMutInstructors = URMutInstructors;
    }

    public UTInstructor getURMutInstructor() {
        return URMutInstructor;
    }

    public void setURMutInstructor(UTInstructor URMutInstructor) {
        this.URMutInstructor = URMutInstructor;
    }

    public String getURMsearchDepartmentId() {
        return URMsearchDepartmentId;
    }

    public void setURMsearchDepartmentId(String URMsearchDepartmentId) {
        this.URMsearchDepartmentId = URMsearchDepartmentId;
    }

    public String getCourseNm() {
        return courseNm;
    }

    public void setCourseNm(String courseNm) {
        this.courseNm = courseNm;
    }

    public String getCourseNmb() {
        return courseNmb;
    }

    public void setCourseNmb(String courseNmb) {
        this.courseNmb = courseNmb;
    }

    public String getCourseManager() {
        return courseManager;
    }

    public void setCourseManager(String courseManager) {
        this.courseManager = courseManager;
    }

    public String getInstructorCode() {
        return InstructorCode;
    }

    public void setInstructorCode(String instructorCode) {
        InstructorCode = instructorCode;
    }

    public List<CourseManagerViewObject> getCourseManagerViewObjects() {
        return courseManagerViewObjects;
    }

    public void setCourseManagerViewObjects(List<CourseManagerViewObject> courseManagerViewObjects) {
        this.courseManagerViewObjects = courseManagerViewObjects;
    }

    public String getSearchCourseNm() {
        return searchCourseNm;
    }

    public void setSearchCourseNm(String searchCourseNm) {
        this.searchCourseNm = searchCourseNm;
    }

    public String getSearchCourseNmb() {
        return searchCourseNmb;
    }

    public void setSearchCourseNmb(String searchCourseNmb) {
        this.searchCourseNmb = searchCourseNmb;
    }

    public String getSearchCourseManager() {
        return searchCourseManager;
    }

    public void setSearchCourseManager(String searchCourseManager) {
        this.searchCourseManager = searchCourseManager;
    }

    public String getSearchCourseInsCode() {
        return searchCourseInsCode;
    }

    public void setSearchCourseInsCode(String searchCourseInsCode) {
        this.searchCourseInsCode = searchCourseInsCode;
    }

    public CourseManagerViewObject getSelectedCourseManagerObject() {
        return selectedCourseManagerObject;
    }

    public void setSelectedCourseManagerObject(CourseManagerViewObject selectedCourseManagerObject) {
        this.selectedCourseManagerObject = selectedCourseManagerObject;
    }

    public Integer getCourseManagerIndex() {
        return courseManagerIndex;
    }

    public void setCourseManagerIndex(Integer courseManagerIndex) {
        this.courseManagerIndex = courseManagerIndex;
    }

    public String getPermissionNM() {
        return permissionNM;
    }

    public void setPermissionNM(String permissionNM) {
        this.permissionNM = permissionNM;
    }

    public String getPermissionContent() {
        return permissionContent;
    }

    public void setPermissionContent(String permissionContent) {
        this.permissionContent = permissionContent;
    }

    public String getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(String permissionStatus) {
        this.permissionStatus = permissionStatus;
    }

    public Integer getPermissionIndex() {
        return permissionIndex;
    }

    public void setPermissionIndex(Integer permissionIndex) {
        this.permissionIndex = permissionIndex;
    }
}
