package cn.edu.cqu.ngtl.viewobject.adminInfo;

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
