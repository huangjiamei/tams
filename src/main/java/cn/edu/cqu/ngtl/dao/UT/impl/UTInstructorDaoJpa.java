package cn.edu.cqu.ngtl.dao.UT.impl;

import cn.edu.cqu.ngtl.dao.UT.UTInstructorDao;
import cn.edu.cqu.ngtl.dataobject.UT.UTInstructor;
import org.kuali.rice.core.api.criteria.OrderByField;
import org.kuali.rice.core.api.criteria.OrderDirection;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.*;

@Repository
@Component("UTInstructorDaoJpa")
public class UTInstructorDaoJpa implements UTInstructorDao {

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
	public UTInstructor getInstructorByIdWithoutCache(String Id) {

		return KradDataServiceLocator.getDataObjectService().find(UTInstructor.class, Id);

	}

	@Override
	public UTInstructor save(UTInstructor uTInstructor){

		return KradDataServiceLocator.getDataObjectService().save(uTInstructor);

	}

	@Override
	public List<UTInstructor> getAllInstructors(){

		QueryResults<UTInstructor> qr =  KradDataServiceLocator.getDataObjectService().findAll(UTInstructor.class);

		return qr.getResults();

	}

	@Override
	public UTInstructor getInstructorByNameAndCode(String name, String code) {

		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates((like("code" , "%"+ code + '%')));
		QueryResults<UTInstructor> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTInstructor.class, criteria.build());

		return qr.getResults().isEmpty()?null:qr.getResults().get(0);
	}
	
	@Override
	public List<UTInstructor> getInstructorsByName(String name) {

		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates(like("name",  "%"+name + '%'));
		QueryResults<UTInstructor> qr = KradDataServiceLocator
				.getDataObjectService().findMatching(UTInstructor.class,
						criteria.build());

		return qr.getResults();

	}

	@Override
	public List<UTInstructor> getInstructorsByCode(String code) {

		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates(like("code", "%" + code + "%"));
		QueryResults<UTInstructor> qr = KradDataServiceLocator
				.getDataObjectService().findMatching(UTInstructor.class,
						criteria.build());

		return qr.getResults();
	}
	
	@Override
	public List<UTInstructor> getUTInstructorByCondition(String userRoleDepartmentId, String userRoleName,
			String userRoleGender, String userRoleCode, String userRoleIdNumber){
		if(userRoleDepartmentId==null){
			userRoleDepartmentId = "*";
		}

		if(userRoleGender==null||userRoleGender.equalsIgnoreCase("0")){
			userRoleGender = "*";
		}

		if(userRoleCode==null){
			userRoleCode =  "*";
		}else{
			userRoleCode =  "*" + userRoleCode + "*";
		}

		if(userRoleName==null){
			userRoleName = "*";
		}else{
			userRoleName = "*" + userRoleName + "*";
		}

		if(userRoleIdNumber==null){
			userRoleIdNumber = "*";
		}else{
			userRoleIdNumber =  "*" + userRoleIdNumber + "*";
		}

		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates( and(like("department.id" , userRoleDepartmentId),
									like("name" , userRoleName),
									like("gender" , userRoleGender),
									like("code" , userRoleCode),
									like("idNumber" , userRoleIdNumber)));
		QueryResults<UTInstructor> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTInstructor.class, criteria.build());

		return qr.getResults();

	}

	@Override
	public void delUtInstructor(UTInstructor uTInstructor) {

		if(uTInstructor!=null)
			KradDataServiceLocator.getDataObjectService().delete(uTInstructor);

	}

	@Override
	public UTInstructor getInstructorByCodeOrAuthID(String code, String AuthID) {

		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates( or(equal("code" , code),
									equal("idNumber" , AuthID))
						);
		QueryResults<UTInstructor> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTInstructor.class, criteria.build());

		return qr.getResults().isEmpty()? null:qr.getResults().get(0);

	}
}
