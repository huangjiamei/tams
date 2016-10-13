package cn.edu.cqu.ngtl.dataobject;

/**
 * 学生的dataobject，通过JPA与数据库关联
 *
 * @author 洪明坚
 */

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.Label;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViewType;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViews;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "UNITIME_STUDENT")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class UTStudent extends DataObjectBase implements Serializable {

	private static final long serialVersionUID = 5887523683868231299L;

	@Id
	@Column(name = "UNIQUEID")
	private String id;
	
	@Column(name = "PROGRAM2_ID")
	private Integer program2Id;
	@Column(name = "PROGRAMF_ID")
	private Integer programfId;
	
	@Column(name = "NAME")
	@Label("name")
	private String name;
	
	@Column(name = "PROGRAM_ID")
	private Integer programId;

	@ManyToOne
	@JoinColumn(name="PROGRAM_ID", insertable = false, updatable = false)
	private CMProgram program;
	public CMProgram getProgram() {
		return this.program;
	}
	public void setProgram(CMProgram program) {
		this.program = program;
	}
	
	@Column(name = "GENDER")
	@Label("gender")
	private String gender;
	
	@Column(name = "ID_TYPE")
	@Label("ID_TYPE")
	private String idType;
	
	@Column(name = "ID_NUMBER")
	@Label("ID_NUMBER")
	private String idNumber;
	
	@Column(name = "AUTH_ID")
	@Label("AUTH_ID")
	private String authId;
	
	@Column(name = "DEPARTMENT_ID")
	@Label("DEPARTMENT_ID")
	private Integer departmentId;
	
	@ManyToOne
	@JoinColumn(name="DEPARTMENT_ID",insertable = false, updatable = false)
	private UTDepartment department;
	public UTDepartment getDepartment() {
		return this.department;
	}
	public void setDepartment(UTDepartment department) {
		this.department = department;
	}
	
	@Column(name = "GRADE")
	@Label("grade")
	private String grade;
	
	@Column(name = "VIRTUAL")
	@Label("VIRTUAL")
	private String virtual;
	
	@Column(name = "ADMISSION")
	@Label("ADMISSION")
	private String admission;
	
	@Column(name = "ADMINUNIT")
	@Label("ADMINUNIT")
	private String adminunit;
	
	@Column(name = "CAMPUS_ID")
	@Label("CAMPUS_ID")
	private Integer campusId;
	
	@Column(name = "E_MAIL")
	@Label("E_MAIL")
	private String email;
	
	@Column(name = "ENROLLED")
	@Label("ENROLLED")
	private String enrolled;
	
	public String getEnrolled() {
		return enrolled;
	}
	public void setEnrolled(String enrolled) {
		this.enrolled = enrolled;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@OneToOne
	@JoinColumn(name="CAMPUS_ID",insertable = false, updatable = false)
	private UTCampus utCampus;
	
	public String getGrade() {
		return this.grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getProgramId() {
		return this.programId;
	}
	public void setProgramId(Integer programId) {
		this.programId = programId;
	}
	public Integer getProgram2Id() {
		return this.program2Id;
	}
	public void setProgram2Id(Integer program2Id) {
		this.program2Id = program2Id;
	}
	public Integer getProgramfId() {
		return this.programfId;
	}
	public void setProgramfId(Integer programfId) {
		this.programfId = programfId;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getVirtual() {
		return virtual;
	}
	public void setVirtual(String virtual) {
		this.virtual = virtual;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getAdminunit() {
		return adminunit;
	}
	public void setAdminunit(String adminunit) {
		this.adminunit = adminunit;
	}
	public String getAdmission() {
		return admission;
	}
	public void setAdmission(String admission) {
		this.admission = admission;
	}
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	public Integer getCampusId() {
		return campusId;
	}
	
	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}
	public UTCampus getUtCampus() {
		return utCampus;
	}
	public void setUtCampus(UTCampus utCampus) {
		this.utCampus = utCampus;
	}
	
	@Override
	public String toString() {
		return "UTStudent [id=" + id + ", program2Id=" + program2Id + ", programfId=" + programfId + ", name=" + name
				+ ", programId=" + programId + ", program=" + program + ", gender=" + gender + ", idType=" + idType
				+ ", idNumber=" + idNumber + ", authId=" + authId + ", departmentId=" + departmentId + ", department="
				+ department + ", grade=" + grade + ", virtual=" + virtual + ", admission=" + admission + ", adminunit="
				+ adminunit + ", campusId=" + campusId + ", email=" + email + ", utCampus=" + utCampus + "]";
	}
}
