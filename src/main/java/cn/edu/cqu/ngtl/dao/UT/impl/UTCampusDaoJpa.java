package cn.edu.cqu.ngtl.dao.UT.impl;

import cn.edu.cqu.ngtl.dao.UT.UTCampusDao;
import cn.edu.cqu.ngtl.dataobject.UT.UTCampus;
import org.kuali.rice.krad.data.KradDataServiceLocator;

public class UTCampusDaoJpa implements UTCampusDao {

	@Override
	public UTCampus getCampusById(Integer id) {

		return KradDataServiceLocator.getDataObjectService().find(UTCampus.class, id);

	}

}
