package cn.edu.cqu.ngtl.dao.UT;

import cn.edu.cqu.ngtl.dataobject.UT.UTDepartment;
import java.util.List;


public interface UTDepartmentDao {
	
	UTDepartment getUTDepartmentById(Integer id);
	
	List<UTDepartment> getAllUTDepartments();
	
	UTDepartment getUTDepartmentByDepartmentName(String name);
	
	UTDepartment getEXMDepartmentByDepartmentCodeAndDepartmentName(String deptcode, String name);
	
	UTDepartment saveEXMDepartment(UTDepartment exmDepartment);
	
	UTDepartment getUTDepartmentByCode(String deptCode);
	
	void deleteEXMDepartment(UTDepartment exmDepartment);
}
