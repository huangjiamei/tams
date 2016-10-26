package cn.edu.cqu.ngtl.dao.krim.impl;

import cn.edu.cqu.ngtl.dao.krim.KRIM_PERM_T_Dao;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PERM_T;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

@Repository
public class KRIM_PERM_T_DaoJpa implements KRIM_PERM_T_Dao {
	
	private static String nameSpace ="KR_TAMS";
	
	@Override
	public List<KRIM_PERM_T> getAllPermissions() {
		// TODO Auto-generated method stub
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates(and(equal("namespaceCode" , nameSpace)));
		QueryResults<KRIM_PERM_T> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				KRIM_PERM_T.class, criteria.build());
		return qr.getResults();
	}

	@Override
	public void addPermissions(KRIM_PERM_T krim_perm_t) {
		// TODO Auto-generated method stub
		KradDataServiceLocator.getDataObjectService().save(krim_perm_t);
	}
}
