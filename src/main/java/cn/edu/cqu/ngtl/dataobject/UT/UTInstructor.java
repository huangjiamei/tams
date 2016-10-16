/**
 * 教师的dataobject，通过JPA与数据库关联
 *
 * @author 洪明坚
 */
package cn.edu.cqu.ngtl.dataobject.UT;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
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
	private String name;

	@Column(name = "CODE")
	private String code;
	
	@Column(name = "GENDER")
	private String gender;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name = "ID_TYPE")
	private Integer idType;
	
	@Column(name = "ID_NUMBER")
	private String idNumber;
	
	@Column(name = "POSITION")
	private String position;
	
	@Column(name = "DEPARTMENT_ID")
	private Integer departmentId;

	@ManyToOne
	@JoinColumn(name="DEPARTMENT_ID",insertable = false, updatable = false)
	private UTDepartment department;

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
