package cn.edu.cqu.ngtl.dao;


import cn.edu.cqu.ngtl.dataobject.UTStudent;

import java.util.List;


public interface UTStudentDao {
	public UTStudent getUTStudentById(String id);
	
	public UTStudent saveUTStudent(UTStudent utStudent);
	
	public void delUTStudent(UTStudent utStudent);
	
	public List<UTStudent> getAllUTStudent();
	
	public List<UTStudent> getUTStudentWithCase(String campusId, String departmentId, String programId, String grade);
	
	public Boolean inEnglishProgram(UTStudent utStudent);
	
	public Boolean inSmallLanguageProgram(UTStudent utStudent);
	
	public Boolean inUcOrByDepartment(UTStudent utStudent);
	
	public List<UTStudent> getUtStudentByCondition(String exmStudentId, String exmStudentAuthId,
                                                   String exmStudentName, String exmStudentGender, String exmStudentDepartmentId, String exmStudentProgram,
                                                   String exmStudentGrade, String exmStudentIdNumber, String exmStudentVirtual, String exmStudentCurrent);
	
	public List<UTStudent> getUtStudentByDepartment(Integer departmentId);
	
	public List<UTStudent> getUtStudentByDepartmentAndEnrolled(Integer departmentId, String enrolled);
	
	public List<UTStudent> getUtStudentByEnrolled(String enrolled);
}
