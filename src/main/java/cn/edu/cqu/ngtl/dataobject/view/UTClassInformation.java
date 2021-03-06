package cn.edu.cqu.ngtl.dataobject.view;

import cn.edu.cqu.ngtl.tools.converter.UnitimeDayOfWeekConverter;
import cn.edu.cqu.ngtl.tools.converter.UnitimeTimeSlotConverter;
import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by awake on 2016-10-18.
 *
 * 绑定视图的实体，方法和绑定一般的表格一样  JPA mapping View
 */

@Entity
@Table(name="UNITIME_CLASS_INFORMATION")
public class UTClassInformation extends DataObjectBase implements Serializable {

    @Column(name="LIMIT")
    private Integer limit;

    @Column(name = "MIN_PER_WK")
    private Integer minPerWeek;


    @Column(name="CLASS_NBR")
    private String classNumber;

    @Id
    @Column(name="UNIQUEID")
    private String id;

    @Convert(converter = UnitimeDayOfWeekConverter.class)
    @Column(name = "ASSIGNMENT_DAY")
    private String dayOfWeek;

    @Column(name = "ASSIGNMENT_TIME")
    @Convert(converter = UnitimeTimeSlotConverter.class)
    private String timeSlot;

    @Column(name = "ROOM_ID")
    private Integer roomId;

    @Column(name = "COURSEOFFERING_ID")
    private String courseOfferingId;

    @Column(name = "CLASS_TYPE")
    private String classType;


    @Column(name = "SESSION_ID")
    private Integer sessionId;

    @Column(name = "DATE_PATTERN_ID")
    private Integer datePatternId;

    @Column(name = "SUBPART_ID")
    private Integer subpartId;

    @Column(name ="COURSE_ID")
    private Integer courseId;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "COURSE_NBR")
    private Integer  courseNumber;

    @Column(name = "COURSE_NAME")
    private String courseName;

    @Column(name = "DEPARTMENT_ID")
    private Integer departmentId;

    @Column(name = "CREDIT")
    private String credit;

    @Column(name = "COURSE_CODE")
    private String courseCode;

    @Column(name = "COURSE_HOUR")
    private String hour;

    @Column(name = "DEPT_NAME")
    private String deptName;

    @Column(name = "SHORTNAME")
    private String shortName;

    @Column(name = "DEPT_CODE")
    private String deptcode;

    @Column(name = "UNIVERSITY_ID")
    private String universityId;

    @Column(name = "UNIVERSITY_NAME")
    private String uniName;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "WORKFLOW_STATUS")
    private String statusName;

    @Column(name = "STATUS_ORDER")
    private String order;

    @Column(name = "ROOM_NAME")
    private String roomName;

    @Column(name = "TEACHING_WEEK")
    private String teachingWeek;

    @Transient
    private String instructorName;

    @Transient
    private String workHour;

    private boolean isCheckBox;


    public String getWorkHour() {
        return workHour;
    }

    public void setWorkHour(String workHour) {
        this.workHour = workHour;
    }

    public boolean isCheckBox() {
        return isCheckBox;
    }

    public void setCheckBox(boolean checkBox) {
        isCheckBox = checkBox;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getMinPerWeek() {
        return minPerWeek;
    }

    public void setMinPerWeek(Integer minPerWeek) {
        this.minPerWeek = minPerWeek;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getDatePatternId() {
        return datePatternId;
    }

    public void setDatePatternId(Integer datePatternId) {
        this.datePatternId = datePatternId;
    }

    public Integer getSubpartId() {
        return subpartId;
    }

    public void setSubpartId(Integer subpartId) {
        this.subpartId = subpartId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(Integer courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getTeachingWeek() {
        return teachingWeek;
    }

    public void setTeachingWeek(String teachingWeek) {
        this.teachingWeek = teachingWeek;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
}
