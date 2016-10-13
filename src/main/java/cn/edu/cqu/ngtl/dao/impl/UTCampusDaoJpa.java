package cn.edu.cqu.ngtl.dao.impl;

import cn.edu.cqu.ngtl.dao.UTCampusDao;
import cn.edu.cqu.ngtl.dataobject.UTCampus;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

public class UTCampusDaoJpa implements UTCampusDao {

	@Override
	public List<UTCampus> getAllCampuses() {
		QueryResults<UTCampus> qr =  KradDataServiceLocator.getDataObjectService().findAll(UTCampus.class);
		return qr.getResults();
	}

	@Override
	public UTCampus getCampusById(Integer id) {
		// TODO Auto-generated method stub
		return KradDataServiceLocator.getDataObjectService().find(UTCampus.class, id);
	}

	@Override
	public UTCampus getCampusByShortName(String shortName) {
		// TODO Auto-generated method stub
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("shortName" , shortName));
		UTCampus qr = KradDataServiceLocator.getDataObjectService().findUnique(
				UTCampus.class, criteria.build());
		return qr;
	}

}
