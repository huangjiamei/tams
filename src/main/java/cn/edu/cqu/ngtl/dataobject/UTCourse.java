/**
 * 课程的dataobject，通过JPA与数据库关联
 *
 * @author 洪明坚
 */
package cn.edu.cqu.ngtl.dataobject;

import cn.edu.cqu.ngtl.dataobject.enums.CM_COURSE;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.data.provider.annotation.Label;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViewType;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViews;
import org.kuali.rice.krad.data.provider.annotation.UifValidCharactersConstraintBeanName;

import javax.persistence.*;
import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

@Entity
@Table(name = "UNITIME_COURSE", uniqueConstraints = {@UniqueConstraint(columnNames={"SUBJECT_ID", "COURSE_NBR"})})
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class UTCourse extends DataObjectBase implements Serializable {

	private static final long serialVersionUID = 1093561351610847631L;

	@Id
	@Column(name = "UNIQUEID")
	private Integer id;
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "SUBJECT")
	@Label("Subject area")
	@UifValidCharactersConstraintBeanName("AlphaPatternConstraint")
	private String subject;
	public String getSubject() {
		return this.subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "COURSE_NBR")
	@Label("Course number")
	@UifValidCharactersConstraintBeanName("NumericPatternConstraint")
	private Integer number;
	public Integer getNumber() {
		return this.number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}

	@Column(name = "NAME")
	@Label("Course name")
	private String name;
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CREDIT")
	@Label("Credit")
	@UifValidCharactersConstraintBeanName("NumericPatternConstraint")
	private Integer credit;
	public Integer getCredit() {
		return this.credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	@Column(name = "CODE")
	private String codeR;

	public String getCodeR() {
		return this.codeR;
	}
	public void setCodeR(String codeR) {
		this.codeR = codeR;
	}

	@ManyToOne
	@JoinColumn(name="DEPARTMENT_ID" , insertable = false, updatable = false)
	private UTDepartment department;
	public UTDepartment getDepartment() {
		return this.department;
	}
	public void setDepartment(UTDepartment department) {
		this.department = department;
	}


	@Column(name = "DEPARTMENT_ID")
	private Integer departmentId;
	public Integer getDepartmentId() {
		return this.departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	/**
	 * 复写equals方法，用于判断两个UTCourse是否一样
	 * @author hmj
	 */
	@Override
    public boolean equals(Object obj){
    	UTCourse course = (UTCourse)obj;
    	if(course == null)
    		return false;
    	return course.getId().equals(this.id);
    }

	/*
	 * 请勿在此处添加fetctype = eager
	 */
	@OneToMany
    @JoinTable(
            name="UNITIME_COURSE_SUBPART",
            joinColumns = @JoinColumn( name="COURSE_ID"),
            inverseJoinColumns = @JoinColumn( name="SUBPART_ID")
    )
	@OrderBy(value="id")
	private List<UTSubpart> subparts;
    public void setSubparts(List<UTSubpart> subparts) {
		this.subparts = subparts;
	}
	public List<UTSubpart> getSubparts() {
		return this.subparts;
	}

	/**
	 * 获取课程代码
	 *
	 * @return 课程代码
	 */
	@Transient
	public String getCode() {
		return this.subject+this.number;
	}

	/**
	 * 返回该课程在某个专业中是否必修
	 *
	 * @param programId 专业ID
	 * @return true是，false否
	 * @author hmj
	 */
	@Transient
	public boolean isRequired(Integer programId) {
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates(and(equal("courseId", this.id),
						           equal("programId", programId)));
		QueryResults<CMProgramCourse> qr = KradDataServiceLocator
				.getDataObjectService().findMatching(CMProgramCourse.class,
						criteria.build());

		if(qr.getResults().isEmpty())
			return false;
		else
			return qr.getResults().get(0).getRequired().intValue()== CM_COURSE.REQUIRED;
	}

	/**
	 * 获取课程基本信息
	 * @author zhouxiaowen
	 */
	@Override
	@Transient
	public String toString()
	{
		return "代码:"+this.subject+this.number+ ", 名称:" + this.name + ", 开课学院:" + this.department.getName();
	}


}
