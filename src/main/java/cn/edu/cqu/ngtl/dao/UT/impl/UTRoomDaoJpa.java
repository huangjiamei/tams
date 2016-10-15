package cn.edu.cqu.ngtl.dao.UT.impl;

import cn.edu.cqu.ngtl.dao.UT.UTRoomDao;
import cn.edu.cqu.ngtl.dataobject.UT.UTRoom;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

@Repository
@Component("UTRoomDaoJpa")
public class UTRoomDaoJpa implements UTRoomDao {
	@Override
	public List<UTRoom> getAllRooms(){

		QueryResults<UTRoom> qr =  KradDataServiceLocator.getDataObjectService().findAll(UTRoom.class);

		return qr.getResults();

	}

	@Override
	public List<UTRoom> getRoomsByCampusIdForExam(Integer campusId) {

		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("building.campusId" , campusId));
		QueryResults<UTRoom> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTRoom.class, criteria.build());

		return qr.getResults().isEmpty()?null:qr.getResults();

	}
}
