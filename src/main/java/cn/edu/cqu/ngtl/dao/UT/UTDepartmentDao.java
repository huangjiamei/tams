package cn.edu.cqu.ngtl.dao.UT;

import cn.edu.cqu.ngtl.dataobject.UT.UTDepartment;
import java.util.List;


public interface UTDepartmentDao {
	
	UTDepartment getUTDepartmentById(Integer id);
	
	List<UTDepartment> getAllUTDepartments();
	
	void deleteEXMDepartment(UTDepartment exmDepartment);
}
