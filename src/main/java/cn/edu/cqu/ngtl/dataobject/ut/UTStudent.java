package cn.edu.cqu.ngtl.dataobject.ut;

/**
 * 学生的dataobject，通过JPA与数据库关联
 *
 * @author 洪明坚
 */

import cn.edu.cqu.ngtl.dataobject.cm.CMProgram;
import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "UNITIME_STUDENT")
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
	private String name;
	
	@Column(name = "PROGRAM_ID")
	private Integer programId;

	@ManyToOne
	@JoinColumn(name="PROGRAM_ID", insertable = false, updatable = false)
	private CMProgram program;
	
	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "ID_TYPE")
	private String idType;
	
	@Column(name = "ID_NUMBER")
	private String idNumber;
	
	@Column(name = "AUTH_ID")
	private String authId;
	
	@Column(name = "DEPARTMENT_ID")
	private Integer departmentId;
	
	@ManyToOne
	@JoinColumn(name="DEPARTMENT_ID",insertable = false, updatable = false)
	private UTDepartment department;
	
	@Column(name = "GRADE")
	private String grade;
	
	@Column(name = "VIRTUAL")
	private String virtual;
	
	@Column(name = "ADMISSION")
	private String admission;
	
	@Column(name = "ADMINUNIT")
	private String adminunit;
	
	@Column(name = "CAMPUS_ID")
	private Integer campusId;
	
	@Column(name = "E_MAIL")
	private String email;
	
	@Column(name = "ENROLLED")
	private String enrolled;

	@OneToOne
	@JoinColumn(name="CAMPUS_ID",insertable = false, updatable = false)
	private UTCampus utCampus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getProgram2Id() {
		return program2Id;
	}

	public void setProgram2Id(Integer program2Id) {
		this.program2Id = program2Id;
	}

	public Integer getProgramfId() {
		return programfId;
	}

	public void setProgramfId(Integer programfId) {
		this.programfId = programfId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	public CMProgram getProgram() {
		return program;
	}

	public void setProgram(CMProgram program) {
		this.program = program;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getVirtual() {
		return virtual;
	}

	public void setVirtual(String virtual) {
		this.virtual = virtual;
	}

	public String getAdmission() {
		return admission;
	}

	public void setAdmission(String admission) {
		this.admission = admission;
	}

	public String getAdminunit() {
		return adminunit;
	}

	public void setAdminunit(String adminunit) {
		this.adminunit = adminunit;
	}

	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(String enrolled) {
		this.enrolled = enrolled;
	}

	public UTCampus getUtCampus() {
		return utCampus;
	}

	public void setUtCampus(UTCampus utCampus) {
		this.utCampus = utCampus;
	}
}
