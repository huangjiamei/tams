package cn.edu.cqu.ngtl.dao.impl;

import cn.edu.cqu.ngtl.dao.UTDepartmentDao;
import cn.edu.cqu.ngtl.dataobject.UTDepartment;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

@Repository
public class UTDepartmentDaoJpa implements UTDepartmentDao {

	@Override
	public UTDepartment getUTDepartmentById(Integer id) {
		// TODO Auto-generated method stub
		return KradDataServiceLocator.getDataObjectService().find(UTDepartment.class, id);

	}

	@Override
	public List<UTDepartment> getAllUTDepartments() {
		// TODO Auto-generated method stub
		return KradDataServiceLocator.getDataObjectService().findAll(UTDepartment.class).getResults();
	}
  
	@Override
	public UTDepartment getUTDepartmentByDepartmentName(String name){
		// TODO Auto-generated method stub
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("name" , name));
		QueryResults<UTDepartment> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTDepartment.class, criteria.build());
		return qr.getResults().isEmpty()?null:qr.getResults().get(0);
	}
	@Override
	public UTDepartment getEXMDepartmentByDepartmentCodeAndDepartmentName(String deptcode,String name){
			// TODO Auto-generated method stub
			QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(and(equal("deptcode" , deptcode),equal("name" , name)));
			QueryResults<UTDepartment> qr = KradDataServiceLocator.getDataObjectService().findMatching(
					UTDepartment.class, criteria.build());
			return qr.getResults().isEmpty()?null:qr.getResults().get(0);
	}
	@Override
	public UTDepartment saveEXMDepartment(UTDepartment exmDepartment){
		// TODO Auto-generated method stub
		return KradDataServiceLocator.getDataObjectService().save(exmDepartment);
	}
	@Override
	public void deleteEXMDepartment(UTDepartment exmDepartment){
		KradDataServiceLocator.getDataObjectService().delete(exmDepartment);
	}

	@Override
	public UTDepartment getUTDepartmentByCode(String deptCode) {
		// TODO Auto-generated method stub
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("deptcode" , deptCode));
		UTDepartment qr = KradDataServiceLocator.getDataObjectService().findUnique(
				UTDepartment.class, criteria.build());
		return qr;
	}
}
