package cn.edu.cqu.ngtl.dao.ut;


import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;

import java.util.List;

public interface UTInstructorDao {

	List<UTInstructor> getAllInstructorsByDepartmentId(Integer departmentId);

	List<UTInstructor> getAllInstructors();

	List<UTInstructor> getAllInstructorsByEM();
	 
	UTInstructor getInstructorByCode(String code);

	List<UTInstructor> getInstructorByName(String name);


	UTInstructor getInstructorByIdWithoutCache(String Id);

}
