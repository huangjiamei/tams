package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTCampusDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTCampus;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component("UTCampusDaoJpa")
public class UTCampusDaoJpa implements UTCampusDao {

	@Override
	public UTCampus getCampusById(Integer id) {

		return KradDataServiceLocator.getDataObjectService().find(UTCampus.class, id);

	}

}
