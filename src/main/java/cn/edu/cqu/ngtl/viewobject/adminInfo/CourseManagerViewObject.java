package cn.edu.cqu.ngtl.viewobject.adminInfo;

import javax.persistence.Transient;

/**
 * Created by awake on 2016-10-26.
 */
public class CourseManagerViewObject {

    private String id ;
    private String courseNm;
    private String courseNmb;
    private String courseManager;
    private String InstructorCode;
    private String courseId;
    @Transient
    public static String[] getAttrTittles(){
        String[] attrTittles={
                "课程"
                ,"课程代码"
                ,"课程负责人"
                ,"职工号"

        };
        return attrTittles;
    }

    @Transient
    public String[] getContents(){
        String[] contents=new String[4];
        contents[0]=getCourseNm();
        contents[1]=getCourseNmb();
        contents[2]=getCourseManager();
        contents[3]=getInstructorCode();

        return contents;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
