package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTDepartmentDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTDepartment;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

@Repository
@Component("UTDepartmentDaoJpa")
public class UTDepartmentDaoJpa implements UTDepartmentDao {

	@Override
	public UTDepartment getUTDepartmentById(Integer id) {

		return KradDataServiceLocator.getDataObjectService().find(UTDepartment.class, id);

	}


	@Override
	public List<UTDepartment> getAllUTDepartments() {

		return KradDataServiceLocator.getDataObjectService().findAll(UTDepartment.class).getResults();

	}


	@Override
	public List<UTDepartment> getAllHasCourseDepartment() {

		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
				and(
						equal("hasCourse", "1")
				)
		);

		List<UTDepartment> list = KradDataServiceLocator.getDataObjectService().findMatching(UTDepartment.class, criteria.build()).getResults();
		return list==null?null:list;

	}

	@Override
	public void deleteEXMDepartment(UTDepartment exmDepartment){

		KradDataServiceLocator.getDataObjectService().delete(exmDepartment);

	}
}
