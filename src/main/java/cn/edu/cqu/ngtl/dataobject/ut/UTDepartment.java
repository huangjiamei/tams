/**
 * 学院的dataobject，通过JPA与数据库关联
 *
 * @author 洪明坚
 */
package cn.edu.cqu.ngtl.dataobject.ut;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "UNITIME_DEPARTMENT")
public class UTDepartment extends DataObjectBase implements Serializable {

	private static final long serialVersionUID = 5141119396887080869L;

	@Id
	@Column(name = "UNIQUEID")
	@GeneratedValue(generator="utDepartmentSeq")
    @SequenceGenerator(name="utDepartmentSeq",sequenceName="UNITIME_DEPARTMENT_S",allocationSize=1)
	private Integer id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CODE")
	private String deptcode;

	@Column(name = "SHORTNAME")
	private String shortName;
	
	@Column(name = "UNIVERSITY_ID")
	private String universityId;
	
	@ManyToOne
	@JoinColumn(name="UNIVERSITY_ID", insertable = false, updatable = false)
	private UTUniversity utUniversity;


	@Column(name = "HASCOURSE")
	private String hasCourse;
	
//	@OneToMany(mappedBy="department")
//	@JsonIgnore
//	private List<CMProgram> programs;
	
//	@OneToMany(mappedBy="department")
//	private List<UTInstructor> utInstructors;
	
//	@OneToMany(mappedBy="department")
//	@JsonIgnore
//	private List<UTStudent> students;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getUniversityId() {
		return universityId;
	}

	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}

	public UTUniversity getUtUniversity() {
		return utUniversity;
	}

	public void setUtUniversity(UTUniversity utUniversity) {
		this.utUniversity = utUniversity;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getHasCourse() {
		return hasCourse;
	}

	public void setHasCourse(String hasCourse) {
		this.hasCourse = hasCourse;
	}

	//	public List<CMProgram> getPrograms() {
//		return programs;
//	}
//
//	public void setPrograms(List<CMProgram> programs) {
//		this.programs = programs;
//	}
//
//	public List<UTInstructor> getUtInstructors() {
//		return utInstructors;
//	}
//
//	public void setUtInstructors(List<UTInstructor> utInstructors) {
//		this.utInstructors = utInstructors;
//	}
//
//	public List<UTStudent> getStudents() {
//		return students;
//	}
//
//	public void setStudents(List<UTStudent> students) {
//		this.students = students;
//	}
}
