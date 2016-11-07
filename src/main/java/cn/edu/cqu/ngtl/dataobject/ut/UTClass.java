/**
 * 教学班的dataobject，通过JPA与数据库关联
 *
 * @author 洪明坚
 */

package cn.edu.cqu.ngtl.dataobject.ut;

import cn.edu.cqu.ngtl.dataobject.cm.CMProgramCourse;
import cn.edu.cqu.ngtl.tools.converter.UnitimeDayOfWeekConverter;
import cn.edu.cqu.ngtl.tools.converter.UnitimeTimeSlotConverter;
import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "UNITIME_CLASS")
public class UTClass extends DataObjectBase implements Serializable {

	private static final long serialVersionUID = -4704516462361035393L;

	@Id
	@Column(name = "UNIQUEID")
	private Integer id;

	@Column(name = "COURSEOFFERING_ID")
	private Integer courseOfferingId;

    @ManyToOne
    @JoinColumn(name = "COURSEOFFERING_ID", insertable = false, updatable = false)
    private UTCourseOffering courseOffering;

    @Column(name = "CLASS_NBR")
    private String classNumber;

	@Column(name = "SUBPART_ID")
	private Integer subpartId;

	@ManyToOne
	@JoinColumn(name = "SUBPART_ID", insertable = false, updatable = false)
	private UTSubpart subpart;

	@Column(name = "DATE_PATTERN_ID")
	private Integer datePatternId;

	@Column(name = "ASSIGNMENT_DAY")
	@Convert(converter = UnitimeDayOfWeekConverter.class)
	private String dayOfWeek;

	@Column(name = "ASSIGNMENT_TIME")
	@Convert(converter = UnitimeTimeSlotConverter.class)
	private String timeSlot;

	@Column(name = "ROOM_ID")
	private Integer roomId;

	@ManyToOne
	@JoinColumn(name = "ROOM_ID",updatable=false, insertable=false)
	private UTRoom room;

	@Column(name = "LIMIT")
	private Integer limit;

	@OneToMany
	@JoinTable(name = "UNITIME_CLASS_INSTRUCTOR", joinColumns = @JoinColumn(name = "CLASS_ID"), inverseJoinColumns = @JoinColumn(name = "INSTRUCTOR_ID"))
	@OrderBy("id")
	private List<UTInstructor> utInstructors;

    @Transient
    private CMProgramCourse programCourse;

	/**
	 * 注：此链接方法和上一个链接方法作用一致，此方法使用ClassInstructor中间类做辅助
	 * 两者取其一即可
	 */
	/*
	@OneToMany
	@JoinColumn(name = "CLASS_ID",updatable=false, insertable=false)
	private List<UTClassInstructor> classInstructors;
	*/

	/** 最小开课人数 */
	@Column(name = "MIN_PER_WK")
	private Integer minPerWeek;

    public CMProgramCourse getProgramCourse() {
        return programCourse;
    }

    public void setProgramCourse(CMProgramCourse programCourse) {
        this.programCourse = programCourse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(Integer courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public UTCourseOffering getCourseOffering() {
        return courseOffering;
    }

    public void setCourseOffering(UTCourseOffering courseOffering) {
        this.courseOffering = courseOffering;
    }

    public Integer getSubpartId() {
        return subpartId;
    }

    public void setSubpartId(Integer subpartId) {
        this.subpartId = subpartId;
    }

    public UTSubpart getSubpart() {
        return subpart;
    }

    public void setSubpart(UTSubpart subpart) {
        this.subpart = subpart;
    }

    public Integer getDatePatternId() {
        return datePatternId;
    }

    public void setDatePatternId(Integer datePatternId) {
        this.datePatternId = datePatternId;
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

    public UTRoom getRoom() {
        return room;
    }

    public void setRoom(UTRoom room) {
        this.room = room;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<UTInstructor> getUtInstructors() {
        return utInstructors;
    }

    public void setUtInstructors(List<UTInstructor> utInstructors) {
        this.utInstructors = utInstructors;
    }

    public Integer getMinPerWeek() {
        return minPerWeek;
    }

    public void setMinPerWeek(Integer minPerWeek) {
        this.minPerWeek = minPerWeek;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }
}
