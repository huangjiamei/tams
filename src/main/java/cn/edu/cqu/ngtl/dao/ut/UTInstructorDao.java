package cn.edu.cqu.ngtl.dao.ut;


import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;

import java.util.List;

public interface UTInstructorDao {

	List<UTInstructor> getAllInstructorsByDepartmentId(Integer departmentId);

	List<UTInstructor> getAllInstructors();
	 
	UTInstructor getInstructorByNameAndCode(String name, String code);

	UTInstructor getInstructorByIdWithoutCache(String Id);

}