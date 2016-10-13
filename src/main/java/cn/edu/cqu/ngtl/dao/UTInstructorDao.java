package cn.edu.cqu.ngtl.dao;


import cn.edu.cqu.ngtl.dataobject.UTInstructor;

import java.util.List;

public interface UTInstructorDao {

	 public List<UTInstructor> getAllInstructorsByDepartmentId(Integer departmentId);

	 public UTInstructor getInstructorByIdWithoutCache(String Id);
	 
	 public UTInstructor getInstructorByCodeOrAuthID(String code, String AuthID);

	 public UTInstructor save(UTInstructor uTInstructor);
	 
	 public void  delUtInstructor(UTInstructor uTInstructor);

	 public List<UTInstructor> getAllInstructors();
	 
	 public List<UTInstructor> getInstructorsByName(String name);
	 
	 public List<UTInstructor> getInstructorsByCode(String code);
	 
	 public UTInstructor getInstructorByNameAndCode(String name, String code);
	 
	public List<UTInstructor> getUTInstructorByCondition(String userRoleDepartmentId, String userRoleName,
                                                         String userRoleGender, String userRoleCode, String userRoleIdNumber);
}
