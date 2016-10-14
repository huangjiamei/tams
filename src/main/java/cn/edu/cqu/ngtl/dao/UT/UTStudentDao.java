package cn.edu.cqu.ngtl.dao.UT;


import cn.edu.cqu.ngtl.dataobject.UT.UTStudent;

import java.util.List;


public interface UTStudentDao {

	UTStudent getUTStudentById(String id);
	
	UTStudent saveUTStudent(UTStudent utStudent);
	
	void delUTStudent(UTStudent utStudent);
	
	List<UTStudent> getAllUTStudent();
	
	List<UTStudent> getUTStudentWithCase(String campusId, String departmentId, String programId, String grade);
	
	Boolean inUcOrByDepartment(UTStudent utStudent);
	
	List<UTStudent> getUtStudentByCondition(String exmStudentId, String exmStudentAuthId,
											String exmStudentName, String exmStudentGender,
											String exmStudentDepartmentId, String exmStudentProgram,
											String exmStudentGrade, String exmStudentIdNumber,
											String exmStudentVirtual, String exmStudentCurrent);
	
	List<UTStudent> getUtStudentByDepartment(Integer departmentId);
	
	List<UTStudent> getUtStudentByDepartmentAndEnrolled(Integer departmentId, String enrolled);
	
	List<UTStudent> getUtStudentByEnrolled(String enrolled);

}
