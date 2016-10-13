package cn.edu.cqu.ngtl.dao;

import cn.edu.cqu.ngtl.dataobject.UTDepartment;
import java.util.List;


public interface UTDepartmentDao {
	
	public UTDepartment getUTDepartmentById(Integer id);
	
	public List<UTDepartment> getAllUTDepartments(); 
	
	public UTDepartment getUTDepartmentByDepartmentName(String name);
	
	public UTDepartment getEXMDepartmentByDepartmentCodeAndDepartmentName(String deptcode, String name);
	
	public UTDepartment saveEXMDepartment(UTDepartment exmDepartment);
	
	public UTDepartment getUTDepartmentByCode(String deptCode);
	
	public void deleteEXMDepartment(UTDepartment exmDepartment);
}
