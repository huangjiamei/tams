package cn.edu.cqu.ngtl.dao.UT;


import cn.edu.cqu.ngtl.dataobject.UT.UTInstructor;

import java.util.List;

public interface UTInstructorDao {

	List<UTInstructor> getAllInstructorsByDepartmentId(Integer departmentId);

	UTInstructor getInstructorByIdWithoutCache(String Id);
	 
	UTInstructor getInstructorByCodeOrAuthID(String code, String AuthID);

	UTInstructor save(UTInstructor uTInstructor);
	 
	void delUtInstructor(UTInstructor uTInstructor);

	List<UTInstructor> getAllInstructors();
	 
	List<UTInstructor> getInstructorsByName(String name);
	 
	List<UTInstructor> getInstructorsByCode(String code);
	 
	UTInstructor getInstructorByNameAndCode(String name, String code);
	 
	List<UTInstructor> getUTInstructorByCondition(String userRoleDepartmentId, String userRoleName,
												  String userRoleGender, String userRoleCode,
												  String userRoleIdNumber);

}
