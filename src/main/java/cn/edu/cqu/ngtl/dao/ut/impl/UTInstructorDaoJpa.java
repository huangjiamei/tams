package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTInstructorDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;
import org.kuali.rice.core.api.criteria.OrderByField;
import org.kuali.rice.core.api.criteria.OrderDirection;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.like;

@Repository
@Component("UTInstructorDaoJpa")
public class UTInstructorDaoJpa implements UTInstructorDao {

	EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();

	@Override
	public List<UTInstructor> getAllInstructorsByDepartmentId(Integer departmentId){

		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates(equal("departmentId" , departmentId));

		List<OrderByField> orderByFields = new ArrayList<OrderByField>();
		orderByFields.add(OrderByField.Builder.create("name", OrderDirection.ASCENDING).build());
		criteria.setOrderByFields(orderByFields);

		QueryResults<UTInstructor> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTInstructor.class, criteria.build());

		return qr.getResults();
	}

	@Override
	public List<UTInstructor> getAllInstructors(){

		QueryResults<UTInstructor> qr =  KradDataServiceLocator.getDataObjectService().findAll(UTInstructor.class);

		return qr.getResults();

	}

	@Override
	public List<UTInstructor> getAllInstructorsByEM(){
		Query query = em.createNativeQuery("SELECT * FROM UNITIME_INSTRUCTOR",UTInstructor.class);
		return query.getResultList();
	}


	@Override
	public  List<UTInstructor> getInstructorByName(String name){
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates((like("name" , "%"+ name + '%')));
		QueryResults<UTInstructor> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTInstructor.class, criteria.build());
		return  qr.getResults();

	}

	@Override
	public UTInstructor getInstructorByCode( String code) {

		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates((like("code" , "%"+ code + '%')));
		QueryResults<UTInstructor> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTInstructor.class, criteria.build());

		return qr.getResults().isEmpty()?null:qr.getResults().get(0);
	}

	@Override
	public UTInstructor getInstructorByIdWithoutCache(String Id) {
		return KradDataServiceLocator.getDataObjectService().find(UTInstructor.class, Id);
	}
}
