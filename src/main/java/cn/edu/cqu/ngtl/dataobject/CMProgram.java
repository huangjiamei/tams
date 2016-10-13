package cn.edu.cqu.ngtl.dataobject;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViewType;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViews;
import org.kuali.rice.krad.data.provider.annotation.UifValidCharactersConstraintBeanName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CM_PROGRAM")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class CMProgram extends DataObjectBase implements Serializable {

	/**
	 * @author liuxiao
	 */
	private static final long serialVersionUID = -1543515532501167011L;

	@Id
	@Column(name = "UNIQUEID")
	@GeneratedValue(generator="cmProgramSeq")
    @SequenceGenerator(name="cmProgramSeq",sequenceName="CM_PROGRAM_ID_S",allocationSize=1)
	private Integer id;
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="DEPARTMENT_ID", insertable=false, updatable=false)
	private UTDepartment department;
	public UTDepartment getDepartment() {
		return this.department;
	}
	public void setDepartment(UTDepartment department) {
		this.department = department;
	}

	@Column(name="DEPARTMENT_ID")
	private Integer departmentId;
	public Integer getDepartmentId() {
		return this.departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "NAME")
	private String name;
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CREDIT")
	@UifValidCharactersConstraintBeanName("NumericPatternConstraint")
	private Integer credit;
	public Integer getCredit() {
		return this.credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	@Column(name = "DURATION")
	private Integer duration;
	public Integer getDuration() {
		return this.duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	@Column(name = "DEGREE")
	private Integer degree;
	public Integer getDegree() {
		return degree;
	}
	public void setDegree(Integer degree) {
		this.degree = degree;
	}
	
	@Column(name = "CODE")
	private String code;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
