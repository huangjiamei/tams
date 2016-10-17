package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTDepartmentDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTDepartment;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component("UTDepartmentDaoJpa")
public class UTDepartmentDaoJpa implements UTDepartmentDao {

	@Override
	public UTDepartment getUTDepartmentById(Integer id) {

		return KradDataServiceLocator.getDataObjectService().find(UTDepartment.class, id);

	}

	@Override
	public List<UTDepartment> getAllUTDepartments() {

		return KradDataServiceLocator.getDataObjectService().findAll(UTDepartment.class).getResults();

	}

	@Override
	public void deleteEXMDepartment(UTDepartment exmDepartment){

		KradDataServiceLocator.getDataObjectService().delete(exmDepartment);

	}
}
