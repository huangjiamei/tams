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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Map getAllInstructorNameIdMap(){
		Map instructorMap = new HashMap();
		Query query = em.createNativeQuery("SELECT t.NAME,t.UNIQUEID FROM UNITIME_INSTRUCTOR t");
		List<Object> columns = query.getResultList();
		for (Object column : columns) {
			Object[] instructors = (Object[]) column;
			instructorMap.put(instructors[1].toString(),instructors[0].toString());
		}

	return instructorMap;
	}


	@Override
	public List<UTInstructor> getInstructorByConditions(Map<String,String> conditions){
		List<UTInstructor> instructorsByconditions = new ArrayList<>();
		int countNull = 0;
		//加通配符
		for (Map.Entry<String, String> entry : conditions.entrySet()) {
			if (entry.getValue() == null) {
				conditions.put(entry.getKey(), "%");
				countNull++;
			} else
				conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
		}
		if (countNull != 5) {
			Query query = em.createNativeQuery("SELECT * FROM UNITIME_INSTRUCTOR t " +
					"where t.NAME LIKE '" +conditions.get("userName")
					+"' AND t.GENDER LIKE '"+conditions.get("userGender")
					+"' AND  t.CODE LIKE '" + conditions.get("userCode")
					+"' AND  t.ID_NUMBER LIKE '" + conditions.get("userAuthId")
					+"' AND  t.DEPARTMENT_ID LIKE '"+conditions.get("departmentId") +"'",UTInstructor.class);
			instructorsByconditions = query.getResultList();
		}else{
			instructorsByconditions = this.getAllInstructors();
		}
		if(instructorsByconditions.size()!=0||instructorsByconditions!=null) {
			return instructorsByconditions;
		}else{
			instructorsByconditions.add(new UTInstructor());
			return instructorsByconditions;
		}
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
