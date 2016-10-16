package cn.edu.cqu.ngtl.dao.UT.impl;

import cn.edu.cqu.ngtl.dao.UT.UTStudentDao;
import cn.edu.cqu.ngtl.dataobject.UT.UTStudent;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component("UTStudentDaoJpa")
public class UTStudentDaoJpa implements UTStudentDao {

	@Override
	public UTStudent getUTStudentById(String id) {

		return KradDataServiceLocator.getDataObjectService().find(UTStudent.class, id);

	}

	@Override
	public UTStudent saveUTStudent(UTStudent utStudent) {

		return KradDataServiceLocator.getDataObjectService().save(utStudent);

	}

}
