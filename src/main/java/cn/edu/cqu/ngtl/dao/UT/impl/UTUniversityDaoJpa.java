package cn.edu.cqu.ngtl.dao.UT.impl;

import cn.edu.cqu.ngtl.dao.UT.UTUniversityDao;
import cn.edu.cqu.ngtl.dataobject.UT.UTUniversity;
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
