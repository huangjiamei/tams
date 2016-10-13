/**
 * 教师的dataobject，通过JPA与数据库关联
 *
 * @author 洪明坚
 */
package cn.edu.cqu.ngtl.dataobject;

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.Label;

import javax.persistence.*;
import java.beans.Transient;
import java.io.Serializable;

@Entity
@Table(name = "UNITIME_INSTRUCTOR")
public class UTInstructor extends DataObjectBase implements Serializable {

	private static final long serialVersionUID = 7747911361695259867L;

	@Id
    @GeneratedValue(generator="utInstructorSeq")
    @SequenceGenerator(name="utInstructorSeq", sequenceName="UNITIME_INSTRUCTOR_S", allocationSize = 1)
	@Column(name = "UNIQUEID")
	private String id;

	@Column(name = "NAME")
	@Label("name")
	private String name;

	@Column(name = "CODE")
	@Label("CODE")
	private String code;
	
	@Column(name = "GENDER")
	@Label("GENDER")
	private String gender;

	@Column(name = "PHONE_NUMBER")
	@Label("PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name = "ID_TYPE")
	@Label("ID_TYPE")
	private Integer idType;
	
	@Column(name = "ID_NUMBER")
	@Label("ID_NUMBER")
	private String idNumber;
	
	@Column(name = "POSITION")
	@Label("POSITION")
	private String position;
	
	@Column(name = "DEPARTMENT_ID")
	private Integer departmentId;

	@ManyToOne
	@JoinColumn(name="DEPARTMENT_ID",insertable = false, updatable = false)
	private UTDepartment department;

	@Override
	@Transient
	public String toString()
	{
		if(this.name==null)
			return "名称为空";
		else if(this.department==null)
			return "学院为空"+" 姓名:" + this.name +" 身份认证号：" + this.getIdNumber();
		else if(this.getIdNumber()==null)
			return "身份认证号为空";
		else
			return "姓名:" + this.name + ", 学院:" + this.department.getName()+" 身份认证号：" + this.getIdNumber();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public UTDepartment getDepartment() {
		return department;
	}

	public void setDepartment(UTDepartment department) {
		this.department = department;
	}
}
