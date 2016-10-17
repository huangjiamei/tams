package cn.edu.cqu.ngtl.dataobject.cm;

import cn.edu.cqu.ngtl.dataobject.ut.UTDepartment;
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

	private static final long serialVersionUID = -1543515532501167011L;

	@Id
	@Column(name = "UNIQUEID")
	@GeneratedValue(generator="cmProgramSeq")
    @SequenceGenerator(name="cmProgramSeq",sequenceName="CM_PROGRAM_ID_S",allocationSize=1)
	private Integer id;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="DEPARTMENT_ID", insertable=false, updatable=false)
	private UTDepartment department;

	@Column(name="DEPARTMENT_ID")
	private Integer departmentId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CREDIT")
	@UifValidCharactersConstraintBeanName("NumericPatternConstraint")
	private Integer credit;

	@Column(name = "DURATION")
	private Integer duration;
	
	@Column(name = "DEGREE")
	private Integer degree;
	
	@Column(name = "CODE")
	private String code;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
