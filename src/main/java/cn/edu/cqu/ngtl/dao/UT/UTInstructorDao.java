package cn.edu.cqu.ngtl.dao.UT;


import cn.edu.cqu.ngtl.dataobject.UT.UTInstructor;

import java.util.List;

public interface UTInstructorDao {

	List<UTInstructor> getAllInstructorsByDepartmentId(Integer departmentId);

	List<UTInstructor> getAllInstructors();
	 
	UTInstructor getInstructorByNameAndCode(String name, String code);

}
