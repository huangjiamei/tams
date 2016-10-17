package cn.edu.cqu.ngtl.dao.ut;

import cn.edu.cqu.ngtl.dataobject.ut.UTDepartment;
import java.util.List;


public interface UTDepartmentDao {
	
	UTDepartment getUTDepartmentById(Integer id);
	
	List<UTDepartment> getAllUTDepartments();
	
	void deleteEXMDepartment(UTDepartment exmDepartment);
}
