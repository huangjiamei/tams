package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTUniversityDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTUniversity;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component("UTUniversityDaoJpa")
public class UTUniversityDaoJpa implements UTUniversityDao {

	@Override
	public List<UTUniversity> getAllUniversities(){

		return KradDataServiceLocator.getDataObjectService().findAll(UTUniversity.class).getResults();

	}

}
