/**
 * 课程的dataobject，通过JPA与数据库关联
 *
 * @author 洪明坚
 */
package cn.edu.cqu.ngtl.dataobject.ut;

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.Label;
import org.kuali.rice.krad.data.provider.annotation.UifValidCharactersConstraintBeanName;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "UNITIME_COURSE", uniqueConstraints = {@UniqueConstraint(columnNames={"SUBJECT_ID", "COURSE_NBR"})})
public class UTCourse extends DataObjectBase implements Serializable {

	private static final long serialVersionUID = 1093561351610847631L;

	@Id
	@Column(name = "UNIQUEID")
	private Integer id;

	@Column(name = "SUBJECT")
	@Label("Subject area")
	@UifValidCharactersConstraintBeanName("AlphaPatternConstraint")
	private String subject;

	@Column(name = "COURSE_NBR")
	@Label("Course number")
	@UifValidCharactersConstraintBeanName("NumericPatternConstraint")
	private Integer number;

	@Column(name = "NAME")
	@Label("Course name")
	private String name;

	@Column(name="COURSE_HOUR")
	private String hour;

	@Column(name = "CREDIT")
	@Label("Credit")
	private String credit;

	@Column(name = "CODE")
	private String codeR;

	@ManyToOne
	@JoinColumn(name="DEPARTMENT_ID" , insertable = false, updatable = false)
	private UTDepartment department;


	@Column(name = "DEPARTMENT_ID")
	private Integer departmentId;

	@OneToMany
    @JoinTable(
            name="UNITIME_COURSE_SUBPART",
            joinColumns = @JoinColumn( name="COURSE_ID"),
            inverseJoinColumns = @JoinColumn( name="SUBPART_ID")
    )
	@OrderBy(value="id")
	private List<UTSubpart> subparts;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getCodeR() {
		return codeR;
	}

	public void setCodeR(String codeR) {
		this.codeR = codeR;
	}

	public UTDepartment getDepartment() {
		return department;
	}

	public void setDepartment(UTDepartment department) {
		this.department = department;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public List<UTSubpart> getSubparts() {
		return subparts;
	}

	public void setSubparts(List<UTSubpart> subparts) {
		this.subparts = subparts;
	}
}
