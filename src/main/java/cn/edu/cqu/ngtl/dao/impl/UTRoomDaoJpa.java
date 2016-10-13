package cn.edu.cqu.ngtl.dao.impl;

import cn.edu.cqu.ngtl.dao.UTRoomDao;
import cn.edu.cqu.ngtl.dataobject.UTRoom;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

public class UTRoomDaoJpa implements UTRoomDao {
	@Override
	public List<UTRoom> getAllRooms(){
		QueryResults<UTRoom> qr =  KradDataServiceLocator.getDataObjectService().findAll(UTRoom.class);
		return qr.getResults();
	}

	@Override
	public List<UTRoom> getRoomsByCampusIdForExam(Integer campusId) {
		// TODO Auto-generated method stub
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("building.campusId" , campusId));
		QueryResults<UTRoom> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTRoom.class, criteria.build());
		return qr.getResults().isEmpty()?null:qr.getResults();
	}
}
