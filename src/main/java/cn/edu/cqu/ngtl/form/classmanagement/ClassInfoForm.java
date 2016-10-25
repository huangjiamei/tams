package cn.edu.cqu.ngtl.form.classmanagement;

import cn.edu.cqu.ngtl.form.BaseForm;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyAssistantViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassDetailInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by awake on 2016-10-21.
 */
public class ClassInfoForm extends BaseForm implements Serializable {

    private static final long serialVersionUID = 230347454225549981L;


    /*
    班级管理隐藏搜索框
     */
    String condDepartmentName;
    String condCourseName;
    String condCourseCode;
    String condClassNumber;
    String condInstructorName;

    String condJudgeStatus;
    String condCourseHour;
    String condCourseCredit;
    String condIsRequired;
    String condCourseClassification;
    String condSessionYear;
    String condProgramName;

    /** 页面上的学历选项 */
    String eduBackground;

    public String getEduBackground() {
        return eduBackground;
    }

    public void setEduBackground(String eduBackground) {
        this.eduBackground = eduBackground;
    }

    /**********/

    public void setCondDepartmentName(String condDepartmentName) {
        this.condDepartmentName = condDepartmentName;
    }

    public void setCondCourseName(String condCourseName) {
        this.condCourseName = condCourseName;
    }

    public void setCondCourseCode(String condCourseCode) {
        this.condCourseCode = condCourseCode;
    }

    public void setCondClassNumber(String condClassNumber) {
        this.condClassNumber = condClassNumber;
    }

    public void setCondInstructorName(String condInstructorName) {
        this.condInstructorName = condInstructorName;
    }



    public void setCondJudgeStatus(String condJudgeStatus) {
        this.condJudgeStatus = condJudgeStatus;
    }

    public void setCondCourseHour(String condCourseHour) {
        this.condCourseHour = condCourseHour;
    }

    public void setCondCourseCredit(String condCourseCredit) {
        this.condCourseCredit = condCourseCredit;
    }

    public void setCondIsRequired(String condIsRequired) {
        this.condIsRequired = condIsRequired;
    }

    public void setCondCourseClassification(String condCourseClassification) {
        this.condCourseClassification = condCourseClassification;
    }

    public void setCondSessionYear(String condSessionYear) {
        this.condSessionYear = condSessionYear;
    }

    public void setCondProgramName(String condProgramName) {
        this.condProgramName = condProgramName;
    }



    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public String getCondDepartmentName() {
        return condDepartmentName;
    }

    public String getCondCourseName() {
        return condCourseName;
    }

    public String getCondCourseCode() {
        return condCourseCode;
    }

    public String getCondClassNumber() {
        return condClassNumber;
    }

    public String getCondInstructorName() {
        return condInstructorName;
    }


    public String getCondJudgeStatus() {
        return condJudgeStatus;
    }

    public String getCondCourseHour() {
        return condCourseHour;
    }

    public String getCondCourseCredit() {
        return condCourseCredit;
    }

    public String getCondIsRequired() {
        return condIsRequired;
    }

    public String getCondCourseClassification() {
        return condCourseClassification;
    }

    public String getCondSessionYear() {
        return condSessionYear;
    }

    public String getCondProgramName() {
        return condProgramName;
    }


    private List<ClassTeacherViewObject> collection = new ArrayList<>();

    private List<ClassTeacherViewObject> classList = new ArrayList<>();

    private ClassDetailInfoViewObject detailInfoViewObject;

    /**
     * 学生申请助教页面相关
     */

    private ApplyAssistantViewObject applyAssistantViewObject;

    private String applyReason;

    /**
     * 学生申请助教页面结束
     */

    /**
     * 老师提交申请页面相关
     */

    private ApplyViewObject applyViewObject;

    /**
     * 结束
     */

    public ApplyViewObject getApplyViewObject() {
        return applyViewObject;
    }

    public void setApplyViewObject(ApplyViewObject applyViewObject) {
        this.applyViewObject = applyViewObject;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public ApplyAssistantViewObject getApplyAssistantViewObject() {
        return applyAssistantViewObject;
    }

    public void setApplyAssistantViewObject(ApplyAssistantViewObject applyAssistantViewObject) {
        this.applyAssistantViewObject = applyAssistantViewObject;
    }

    public List<ClassTeacherViewObject> getClassList() {
        return classList;
    }

    public void setClassList(List<ClassTeacherViewObject> classList) {
        this.classList = classList;
    }

    public ClassDetailInfoViewObject getDetailInfoViewObject() {
        return detailInfoViewObject;
    }

    public void setDetailInfoViewObject(ClassDetailInfoViewObject detailInfoViewObject) {
        this.detailInfoViewObject = detailInfoViewObject;
    }
}
